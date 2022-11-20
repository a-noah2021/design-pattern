package com.noah2021.ratelimiter.exception;

/**
 * @program: design-pattern
 * @description:
 * @author: noah2021
 * @date: 2022-10-08 00:20
 **/
public class RateLimiterException extends Exception implements CommonException {

    private CommonException commonException;

    //直接接收EmRateLimiterException的传参用于构造业务异常
    public RateLimiterException(CommonException commonException){
        super();
        this.commonException = commonException;
    }

    //接收自定义errMsg的方式构造业务异常
    public RateLimiterException(CommonException commonException,String errMsg){
        super();
        this.commonException = commonException;
        this.commonException.setErrMsg(errMsg);
    }

    @Override
    public int getErrCode() {
        return this.commonException.getErrCode();
    }

    @Override
    public String getErrMsg() {
        return this.commonException.getErrMsg();
    }

    @Override
    public CommonException setErrMsg(String errMsg) {
        this.commonException.setErrMsg(errMsg);
        return this;
    }

    public CommonException getCommonException() {
        return commonException;
    }
}