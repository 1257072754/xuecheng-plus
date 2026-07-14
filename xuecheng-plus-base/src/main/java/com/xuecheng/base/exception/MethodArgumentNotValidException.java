package com.xuecheng.base.exception;

/**
 * @author Mr.M
 * @version 1.0
 * @description 本项目自定义异常类型
 * @date 2023/2/12 16:56
 */
public class MethodArgumentNotValidException extends RuntimeException {

    private String errMessage;

    public MethodArgumentNotValidException() {
    }

    public MethodArgumentNotValidException(String message) {
        super(message);
        this.errMessage = message;

    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public static void cast(String message){
        throw new MethodArgumentNotValidException(message);
    }
    public static void cast(CommonError error){
        throw new MethodArgumentNotValidException(error.getErrMessage());
    }

}
