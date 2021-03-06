package com.cn.quartz.timers;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

//Job对象多实例禁止并发执行
//就是当这个调度作业还没执行完成的时候，下一次的调度又到了，
// 如果注解了表示不会再申请一个线程让两个Job并发执行，需要等上一次作业执行完成才串行的执行。
@DisallowConcurrentExecution
public class GoodAddTimer extends QuartzJobBean {
    /**
     * logback
     */
    static Logger logger = LoggerFactory.getLogger(GoodAddTimer.class);

    /**
     * 定时任务逻辑实现方法
     * 每当触发器触发时会执行该方法逻辑
     *
     * @param jobExecutionContext 任务执行上下文
     * @throws JobExecutionException
     */
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("分布式节点quartz-cluster-node-first，商品添加完成后执行任务，任务时间：{}", new Date());
    }
}
