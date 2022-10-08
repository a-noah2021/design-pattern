package com.noah2021.ratelimiter.error;

/**
 * @program: design-pattern
 * @description:
 * @author: noah2021
 * @date: 2022-10-08 00:23
 **/
public enum EmRateLimiterError implements CommonError {

    RPC_TIMEOUT(10001,"调用外部接口超时"),
    FILE_LOAD_FAIL(10002, "限流规则读取失败"),
    API_THREAD_FULL(10003,"调用接口线程已满"),
    SERVER_HIGH_LOAD(10004,"服务器负载过高")
    ;

    EmRateLimiterError(int errCode,String errMsg){
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
    public CommonError setErrMsg(String errMsg) {
        this.errMsg = errMsg;
        return this;
    }
}