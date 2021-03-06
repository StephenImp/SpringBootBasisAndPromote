package com.cn.quartz.service;


import com.cn.quartz.entity.GoodInfoEntity;
import com.cn.quartz.jpa.GoodInfoRepository;
import com.cn.quartz.timers.GoodAddTimer;
import com.cn.quartz.timers.GoodSecKillRemindTimer;
import com.cn.quartz.timers.GoodStockCheckTimer;
import org.quartz.*;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;



/**
 * 商品业务逻辑
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GoodInfoService
{
    /**
     * 注入任务调度器
     */
    @Autowired
    @Qualifier("schedulerFactoryBean")
    private Scheduler scheduler;
    /**
     * 商品数据接口
     */
    @Autowired
    private GoodInfoRepository goodInfoRepository;

    /**
     * 保存商品基本信息
     * @param good 商品实例
     * @return
     */
    public Long saveGood(GoodInfoEntity good) throws Exception
    {
        goodInfoRepository.save(good);
        //构建创建商品定时任务
        buildCreateGoodTimer();
        //构建商品库存定时任务
        buildGoodStockTimer();
        //构建商品描述提醒定时任务
        buildGoodSecKillRemindTimer(good.getId());
        //容灾测试
        buildMisfireTestTimer();

        return good.getId();
    }

    /**
     * 容灾测试
     */
    private void buildMisfireTestTimer() {

        //设置开始时间为1分钟后
        long startAtTime = System.currentTimeMillis() + 1000 * 60;
        //任务名称
        String jobName = UUID.randomUUID().toString();
        //任务所属分组
        String groupName = GoodAddTimer.class.getName();

        /**
         * 把这个设置好的trigger加到scheduler里面就行了吧，哎，先这样吧啊
         */
        SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder.newTrigger()
                .withIdentity(jobName, groupName)
                //如果你没有调用 *startAt(..)*方法，那么当前时间（立刻激活）将被赋值。
                .startAt(new Date(startAtTime))
                .build();
        ((SimpleTriggerImpl)trigger).setMisfireInstruction(SimpleTrigger.MISFIRE_INSTRUCTION_RESCHEDULE_NOW_WITH_EXISTING_REPEAT_COUNT);
    }

    /**
     * 构建创建商品定时任务
     * GoodAddTimer实例只运行一次，在商品添加完成后延迟1分钟进行调用任务主体逻辑。
     */
    public void buildCreateGoodTimer() throws Exception
    {
        //设置开始时间为1分钟后
        long startAtTime = System.currentTimeMillis() + 1000 * 60;
        //任务名称
        String jobName = UUID.randomUUID().toString();
        //任务所属分组
        String groupName = GoodAddTimer.class.getName();
        //创建任务
        JobDetail jobDetail = JobBuilder.newJob(GoodAddTimer.class).withIdentity(jobName,groupName).build();
        //创建任务触发器
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName,groupName).startAt(new Date(startAtTime)).build();
        //将触发器与任务绑定到调度器内
        scheduler.scheduleJob(jobDetail, trigger);
    }

    /**
     * 构建商品库存定时任务
     * @throws Exception
     */
    public void buildGoodStockTimer() throws Exception
    {
        //任务名称
        String jobName = UUID.randomUUID().toString();
        //任务所属分组
        String groupName = GoodStockCheckTimer.class.getName();

        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/30 * * * * ?");
        //创建任务
        JobDetail jobDetail = JobBuilder.newJob(GoodStockCheckTimer.class).withIdentity(jobName,groupName).build();
        //创建任务触发器
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName,groupName).withSchedule(scheduleBuilder).build();
        //将触发器与任务绑定到调度器内
        scheduler.scheduleJob(jobDetail, trigger);
    }

    /**
     * 构建商品秒杀提醒定时任务
     * 我们模拟秒杀提醒时间是添加商品后的5分钟，
     * @throws Exception
     */
    public void buildGoodSecKillRemindTimer(Long goodId) throws Exception
    {
        //任务名称
        String jobName = UUID.randomUUID().toString();
        //任务所属分组
        String groupName = GoodSecKillRemindTimer.class.getName();
        //秒杀开始时间
        long startTime = System.currentTimeMillis() + 1000 * 5 * 60;
        JobDetail jobDetail = JobBuilder
                .newJob(GoodSecKillRemindTimer.class)
                .withIdentity(jobName,groupName)
                .build();

        //设置任务传递商品编号参数
        jobDetail.getJobDataMap().put("goodId",goodId);

        //创建任务触发器
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName,groupName).startAt(new Date(startTime)).build();
        //将触发器与任务绑定到调度器内
        scheduler.scheduleJob(jobDetail,trigger);
    }
}
