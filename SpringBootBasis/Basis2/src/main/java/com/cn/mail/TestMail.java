package com.cn.mail;


import com.cn.mail.core.MailSender;
import com.cn.mail.enums.MailContentTypeEnum;

import java.util.ArrayList;

/**
 * ========================
 * Date：2017/4/8
 * Time：20:49
 * ========================
 */
public class TestMail {
    public static void main(String[] args) throws Exception
    {
        new MailSender()
                .title("测试SpringBoot发送邮件")
                .content("简单文本内容发送")
                .contentType(MailContentTypeEnum.TEXT)
                .targets(new ArrayList<String>(){{
                    add("390168303@qq.com");
                }})
                .send();
    }
}
