package com.yf.text;

public enum MySecondEnum {
    NOMAL((byte)0),
    PAUSE((byte)1);
    private byte value;

    MySecondEnum(byte value){
        this.value=value;
    }

    public byte getValue() {
        return value;
    }
}
