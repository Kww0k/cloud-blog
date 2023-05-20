package com.cxcacm.user.enums;

public enum AppHttpCodeEnum {

    SUCCESS(200, "操作成功"),
    SYSTEM_ERROR(500, "系统错误"),
    NEED_LOGIN(403, "没有登录"),
    AUTH_EXPIRE(401, "认证过期"),
    CXC_AUTH(415, "没有我ACM金牌陈轩丞的认可还想访问?"),
    HAVE_BEEN_REGISTER(409, "该邮箱已经被注册了"),
    MISSING_PARAM(409, "缺少必要参数"),
    PASSWORD_DIFFERENT(401, "两次输入的密码不同"),
    CODE_ERROR(403, "验证码错误"),
    HAVA_CODE(403, "在5分钟内不能发送第二次验证码"),
    EMAIL_FALSE(403, "邮箱格式错误"),
    PASSWORD_ERROR(403, "密码不匹配"),
    URL_NULL(404, "空的头像地址"),
    USER_NULL(404, "用户不存在");



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
