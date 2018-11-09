package com.cn.entity;

import com.cn.contant.StatusCode;
import com.cn.utils.HexUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Data;

import java.io.Serializable;

@Data
public class NettyMessage implements Serializable {

    /**
     * 版本号
     */
    private short version;

    /**
     * 操作码
     */
    private short commandType;

    /**
     * 时间戳
     */
    private int timestamp;

    /**
     * 流水号
     */
    private short requestId;

    /**
     * 状态码
     */
    private byte status;

    /**
     * 设备SN号(16位)
     */
    private String sn;

    /**
     * 包体长度
     */
    private short dataLength;
    public NettyMessage(){
        this.timestamp= (int) (System.currentTimeMillis()/1000L);
    }
    //包体，默认为空
    private byte[] data=new byte[]{};

    public void setStatus(byte status) {
        this.status = status;
    }

    public void setStatus(StatusCode statusCode) {
        this.status = statusCode.value();
    }

    public String commandTypeHex(){
        return HexUtil.short2HexString(this.commandType);
    }

    public int statusInt(){
        return this.status;
    }

    public void cleanData(){
        this.dataLength=0;
        this.data=new byte[]{};
    }

    public static NettyMessage parseFromNettyMessage(byte[] bytes) {
        ByteBuf byteBuf = Unpooled.buffer();
        NettyMessage nettyMessage = null;

        try {
            byteBuf.writeBytes(bytes);
            nettyMessage = new NettyMessage();
            nettyMessage.setVersion(byteBuf.readShort());
            nettyMessage.setCommandType(byteBuf.readShort());
            nettyMessage.setTimestamp(byteBuf.readInt());
            nettyMessage.setRequestId(byteBuf.readShort());
            nettyMessage.setStatus(byteBuf.readByte());
            ByteBuf snBytes = byteBuf.readBytes(16);
            nettyMessage.setSn(new String(snBytes.array()));
            nettyMessage.setDataLength(byteBuf.readShort());
            byte[] data = new byte[nettyMessage.getDataLength()];
            byteBuf.readBytes(data);
            nettyMessage.setData(data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            byteBuf.release();
        }

        return nettyMessage;
    }

    public byte[] toBytes() {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeShort(this.version);
        byteBuf.writeShort(this.commandType);
        byteBuf.writeInt(this.timestamp);
        byteBuf.writeShort(this.requestId);
        byteBuf.writeByte(this.status);
        byteBuf.writeBytes(this.sn.getBytes());
        byteBuf.writeShort(this.data.length);
        byteBuf.writeBytes(this.data);
        byte[] bytes = new byte[byteBuf.writerIndex()];
        byteBuf.readBytes(bytes);
        byteBuf.release();
        return bytes;
    }

}
