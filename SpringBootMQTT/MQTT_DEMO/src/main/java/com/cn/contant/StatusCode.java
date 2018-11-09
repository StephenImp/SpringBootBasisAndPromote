package com.cn.contant;

public enum StatusCode {
    MESSAGE_ERR((byte)101,"解析报文错误"),
    COMMOND_NOT_EXIST((byte)102,"功能号不存在"),
    BAD_REQUEST((byte)103,"参数不正确"),
    SERVER_ERROR((byte)104,"系统内部错误"),
    UNAUTHORIZED((byte)105,"未登录");

    private byte value;
    private String desc;

    StatusCode(byte value, String desc) {
        this.value = value;
        this.desc = desc;
    }


    public byte value() {
        return value;
    }

    public String desc() {
        return desc;
    }

}
