package com.cxcacm.commons.enums;

public enum AppHttpCodeEnum {

    SUCCESS(200, "操作成功"),SYSTEM_ERROR(500, "系统错误");


    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage) {
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
