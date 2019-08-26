package com.cn.redisQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by MOZi on 2019/3/22.
 */
public class EventController {

    private static final Logger logger = LoggerFactory.getLogger(EventController.class);
    //主业务
    private static String source = "D:/HEXO.zip";
    private static String target = "D:/copy/1.zip";
    private static String target2 = "D:/copy/2.zip";
    //非主业务
    private static String sourceGame = "D:/SQL.zip";
    private static String targetGame = "D:/copy/SQL1.zip";

    @Autowired
    EventProducer eventProducer;

    @RequestMapping(path = {"/event/async"}, method = {RequestMethod.POST})
    @ResponseBody
    public String eventAsync() {

        try {
            long start = System.currentTimeMillis();

            /**
             * 主业务1，2是复制一个较小的文件；
             *
             * 非主业务是复制一个大小为2G的文件，所以复制时间会比较长。
             */

            //模拟主业务1 ： 复制文件HEXO.zip到指定文件夹target中
//            long time1 = FileCopyUtil.copyFile(source, target);
//            System.out.println("业务1运行时间为： " + time1);

            /**
             * 同步
             */
            //模拟非主业务: 这个业务可能不是那么很紧急要立刻实现的
//            System.out.println("非业务程序开始运行...");
//            long t = FileCopyUtil.copyFile(sourceGame, targetGame);
//            System.out.println("非业务程序运行结束，运行时间为：" + t);


            /**
             * 异步
             */
            //模拟非主业务: 这个业务可能不是那么很紧急要立刻实现的
            System.out.println("非主业务程序开始运行...");
            eventProducer.fireEvent(new EventModel(EventType.COPYFILE));
            System.out.println("非主业务程序运行结束");


            //模拟主业务2 ： 复制文件HEXO.zip到指定文件夹target2中
            //long time2 = FileCopyUtil.copyFile(source, target);
            //System.out.println("业务2运行时间为： " + time2);

            long end = System.currentTimeMillis();
            System.out.println("当前运行总时间为：" + (end - start));

            return null;
            //return JSONUtil.getJSONString(0);
        } catch (Exception e) {
            logger.error("eventAsync 异常:" + e.getMessage());
            //return JSONUtil.getJSONString(1, "failed");
        }

        return null;
    }
}
