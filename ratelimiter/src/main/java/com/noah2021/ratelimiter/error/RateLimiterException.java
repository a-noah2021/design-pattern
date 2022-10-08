package com.noah2021.ratelimiter.error;

/**
 * @program: design-pattern
 * @description:
 * @author: noah2021
 * @date: 2022-10-08 00:20
 **/
public class RateLimiterException extends Exception implements CommonError {

    private CommonError commonError;

    //直接接收EmRateLimiterError的传参用于构造业务异常
    public RateLimiterException(CommonError commonError){
        super();
        this.commonError = commonError;
    }

    //接收自定义errMsg的方式构造业务异常
    public RateLimiterException(CommonError commonError,String errMsg){
        super();
        this.commonError = commonError;
        this.commonError.setErrMsg(errMsg);
    }

    @Override
    public int getErrCode() {
        return this.commonError.getErrCode();
    }

    @Override
    public String getErrMsg() {
        return this.commonError.getErrMsg();
    }

    @Override
    public CommonError setErrMsg(String errMsg) {
        this.commonError.setErrMsg(errMsg);
        return this;
    }

    public CommonError getCommonError() {
        return commonError;
    }
}