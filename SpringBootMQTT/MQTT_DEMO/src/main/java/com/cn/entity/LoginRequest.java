package com.cn.entity;

import com.cn.contant.CommandType;
import com.cn.utils.AttributeCopyer;
import com.cn.utils.HexUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.ReferenceCountUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@Slf4j
@Data
public class LoginRequest extends NettyMessage {
    /**
     * 登陆的账号
     */
    private String userName;
    /**
     * 登陆的密码
     */
    private String password;
    /**
     * 设备的MAC地址
     */
    private String macAddress;

    /**
     * 单元测试使用
     * 构建包体
     * @return
     * @throws Exception
     */
    public byte[] toBytes(){
        ByteBuf byteBuf = Unpooled.buffer();
        if(StringUtils.isEmpty(userName)){
            userName="";
        }
        byte[] userNameBytes = userName.getBytes();
        byteBuf.writeByte((byte) userNameBytes.length);
        byteBuf.writeBytes(userNameBytes);
        if(StringUtils.isEmpty(password)){
            password="";
        }
        byte[] passwordBytes = password.getBytes();
        byteBuf.writeByte((byte) passwordBytes.length);
        byteBuf.writeBytes(passwordBytes);
        if(StringUtils.isEmpty(macAddress)){
            macAddress="";
        }
        String macArr[] = macAddress.split("-");
        for (String mac : macArr) {
            Integer a = Integer.parseInt(mac, 16);
            byteBuf.writeByte(a);
        }
        byte[] bytes = new byte[byteBuf.writerIndex()];
        byteBuf.readBytes(bytes);
        byteBuf.release();
        return bytes;
    }
    /**
     * 解析包体
     * @param message
     * @return
     */
    public static LoginRequest parseFromNettyMessage(NettyMessage message){
        ByteBuf byteBuf =null;
        try {
            byte[] bytes = message.getData();
            byteBuf = Unpooled.copiedBuffer(bytes);
            LoginRequest loginRequest = AttributeCopyer.clone(message, LoginRequest.class);

            byte[] userNameBytes = new byte[byteBuf.readByte()];
            byteBuf.readBytes(userNameBytes);
            loginRequest.setUserName(new String(userNameBytes));
            byte[] passwordBytes = new byte[byteBuf.readByte()];
            byteBuf.readBytes(passwordBytes);
            loginRequest.setPassword(new String(passwordBytes));
            StringBuffer buffer=new StringBuffer();
            buffer.append(HexUtil.byte2HexString(byteBuf.readByte()));
            buffer.append("-").append(HexUtil.byte2HexString(byteBuf.readByte()));
            buffer.append("-").append(HexUtil.byte2HexString(byteBuf.readByte()));
            buffer.append("-").append(HexUtil.byte2HexString(byteBuf.readByte()));
            buffer.append("-").append(HexUtil.byte2HexString(byteBuf.readByte()));
            buffer.append("-").append(HexUtil.byte2HexString(byteBuf.readByte()));
            loginRequest.setMacAddress(buffer.toString());
            return loginRequest;
        }catch (Exception e){
            log.error("解析消息[{}][{}][{}][{}][{}],{},内容[{}]", CommandType.SERVICE_LOGIN.desc(),message.getSn(),HexUtil.short2HexString(message.getCommandType()),message.getRequestId(),message.getTimestamp(),"解析失败",HexUtil.bytes2HexString(message.getData()),e);
        }finally {
            //释放资源
            ReferenceCountUtil.release(byteBuf);
        }
        return null;
    }
}
