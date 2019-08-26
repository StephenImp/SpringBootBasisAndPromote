package com.cn.scheduled.case1;

import com.cn.scheduled.entity.CommonData;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//@Component注解用于对那些比较中立的类进行注释；
//相对与在持久层、业务层和控制层分别采用 @Repository、@Service 和 @Controller 对分层中的类进行注释
@Component
@EnableScheduling   // 1.开启定时任务
@EnableAsync        // 2.开启多线程
public class MultithreadScheduleTask {

    FileWriter fileWriter = null;

    /**
     * 单渠道
     * @throws InterruptedException
     */
    @Async
    @Scheduled(fixedDelay = 10000)  //间隔1秒
    public void singleChannelSchedule() throws InterruptedException {
        System.out.println("第一个定时任务开始 : " + LocalDateTime.now().toLocalTime() + "\r\n线程 : " + Thread.currentThread().getName());

        CommonData<String> data = new CommonData<>();

        data.setList(new ArrayList<>());

        if (ObjectUtils.isEmpty(data.getList())){
            return;
        }
        //将数据写入文本
        try {
            writeForTXT(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 平台
     */
    @Async
    @Scheduled(fixedDelay = 20000)
    public void platform() {
        System.out.println("第二个定时任务开始 : " + LocalDateTime.now().toLocalTime() + "\r\n线程 : " + Thread.currentThread().getName());
        System.out.println();
    }


    private void writeForTXT(CommonData data) throws IOException {

        List list = data.getList();
        try {
            fileWriter = new FileWriter("D:/testWrite/c.txt");//创建文本文件

            for (int i = 0; i <list.size() ; i++) {
                //写入
                // windows下的文本文件换行符:\r\n
                //inux/unix下的文本文件换行符:\r
                // Mac下的文本文件换行符:\n
                fileWriter.write(list.get(i)+"\r\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            fileWriter.flush();
            fileWriter.close();
        }

    }
}
