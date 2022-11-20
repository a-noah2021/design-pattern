package com.noah2021.ratelimiter.exception;

/**
 * @program: design-pattern
 * @description:
 * @author: noah2021
 * @date: 2022-10-08 00:19
 **/
public interface CommonException {

    int getErrCode();

    String getErrMsg();

    CommonException setErrMsg(String errMsg);

}
