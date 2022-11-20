package com.noah2021.ratelimiter.exception;

/**
 * @program: design-pattern
 * @description:
 * @author: noah2021
 * @date: 2022-10-08 00:23
 **/
public enum EmRateLimiterException implements CommonException {

    GENERIC_ERR(10000,"接口通用异常"),
    RPC_TIMEOUT(10001,"调用外部接口超时"),
    FILE_LOAD_FAIL(10002, "限流规则读取失败"),
    INVALID_URL(10003, "无效URL"),
    INTERNAL_ERR(10003, "内部错误"),
    CONFIGURATION_RESOLVE_ERR(10004, "配置解析异常"),
    API_THREAD_FULL(10005,"调用接口线程已满"),
    SERVER_HIGH_LOAD(10006,"服务器负载过高")
    ;

    EmRateLimiterException(int errCode,String errMsg){
        this.errCode = errCode;
        this.errMsg = errMsg;
    }


    private int errCode;
    private String errMsg;


    @Override
    public int getErrCode() {
        return this.errCode;
    }

    @Override
    public String getErrMsg() {
        return this.errMsg;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    @Override
    public CommonException setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}
