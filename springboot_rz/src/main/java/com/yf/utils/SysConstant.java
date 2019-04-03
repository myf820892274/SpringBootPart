package com.yf.utils;

/**
 * 项目常量类
 */
public class SysConstant {
    public static final String CAPTCHA_KEY="code";
    //public static final byte NOMAL=0;
    //public static final byte PAUSE=1;

    public enum ScheduleStatus{
        NOMAL((byte)0),
        PAUSE((byte)1);

        private byte value;
        ScheduleStatus(byte value){
            this.value=value;
        }

        public byte getValue() {
            return value;
        }
    }

}
