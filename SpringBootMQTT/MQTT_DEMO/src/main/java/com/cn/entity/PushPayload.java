package com.cn.entity;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.util.StringUtils;

/**
 * Created by MOZi on 2018/10/30.
 *
 * mqtt消息推送实体
 */
@Slf4j
public class PushPayload {

    //唯一标识
    private String sn;

    //推送类型
    private String type;
    //推送对象
    private String mobile;
    //标题
    private String title;
    //内容
    private String content;
    //数量
    private Integer badge = 1;
    //铃声
    private String sound = "default";


    public PushPayload(String sn,String type, String mobile, String title, String content, Integer badge , String sound){
        this.sn = sn;
        this.type = type;
        this.mobile = mobile;
        this.title = title;
        this.content = content;
        this.badge = badge;
        this.sound = sound;
    }

    public static class Builder{

        private String sn;

        //推送类型
        private String type;
        //推送对象
        private String mobile;
        //标题
        private String title;
        //内容
        private String content;
        //数量
        private Integer badge = 1;
        //铃声
        private String sound = "default";

        public Builder setSn(String sn) {
            this.sn = sn;
            return this;
        }

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public Builder setMobile(String mobile) {
            this.mobile = mobile;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setBadge(Integer badge) {
            this.badge = badge;
            return this;
        }

        public Builder setSound(String sound) {
            this.sound = sound;
            return this;
        }

        public PushPayload bulid(){
            return new PushPayload(sn,type,mobile,title,content,badge,sound);
        }
    }


    public static Builder getPushPayloadBuider(){
        return new Builder();
    }


//    @Override
//    public String toString() {
//        return JSON.toJSONString(this, SerializerFeature.DisableCircularReferenceDetect);
//    }


    public static Logger getLog() {
        return log;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getBadge() {
        return badge;
    }

    public void setBadge(Integer badge) {
        this.badge = badge;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    @Override
    public String toString() {
        return "PushPayload{" +
                "sn='" + sn + '\'' +
                ", type='" + type + '\'' +
                ", mobile='" + mobile + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", badge=" + badge +
                ", sound='" + sound + '\'' +
                '}';
    }

    public byte[] toBytes(){

        ByteBuf byteBuf = Unpooled.buffer();

        if(StringUtils.isEmpty(this.sn)) {
            this.sn = "testMqtt";
        }

        byte[] snBytes = this.sn.getBytes();
        byteBuf.writeByte((byte)snBytes.length);
        byteBuf.writeBytes(snBytes);

        byte[] mobileBytes = this.mobile.getBytes();
        byteBuf.writeByte((byte)mobileBytes.length);
        byteBuf.writeBytes(mobileBytes);


        byte[] bytes = new byte[byteBuf.writerIndex()];

        byteBuf.readBytes(bytes);
        byteBuf.release();
        return bytes;
    }
}


