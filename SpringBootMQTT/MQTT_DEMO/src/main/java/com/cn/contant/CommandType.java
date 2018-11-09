package com.cn.contant;

public enum CommandType {
    SERVICE_PING((short)0xF000,"心跳指令"),
    SERVICE_LOGIN((short)0xF001,"登录指令"),
    DEVICE_ONLINE_STATUS((short)0xF002,"设备在线/离线状态汇报指令"),
    DEVICE_USER_BINDING((short)0xA001,"用户/设备绑定指令"),
    STRIP_ALL_SWITCH_STATUS_REPORT((short)0xA002,"智能排插全部状态汇报指令"),
    STRIP_ELECTRICITY_REPORT((short)0xA003,"智能排插电量汇报指令"),
    STRIP_TASK_RESULT_REPORT((short)0xA004,"智能排插任务结果汇报指令"),
    DEVICE_VERSION_REPORT((short)0xA005,"设备版本汇报指令"),
    HEALTH_CHECK_RESULT_REPORT((short)0xA006,"监控检查结果汇报"),
    STRIP_TOTAL_SWITCH_CONTROLLER((short)0xB001,"智能排插总开关控制指令"),
    DEVICE_INFO_RESET_CONTROLLER((short)0xB002,"设备信息重置控制指令"),
    STRIP_JACK_SWITCH_CONTROLLER((short)0xB003,"智能排插插孔开关控制指令"),
    STRIP_JACK_SWITCH_TIME_TASK_SETTING((short)0xB004,"智能排插定时任务指令"),
    STRIP_JACK_SWITCH_COUNTDOWN_SETTING((short)0xB005,"智能排插倒计时任务指令"),
    DEVICE_UPGRADE_FIRMWARE((short)0xB006,"设备固件升级指令"),
    HEALTH_CHECK((short)0xB007,"监控检查指令");



    private short value;
    private String desc;

    CommandType(short value, String desc) {
        this.value = value;
        this.desc = desc;
    }


    public short value() {
        return value;
    }

    public String desc() {
        return desc;
    }

}
