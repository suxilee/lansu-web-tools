package org.lansu.result;


import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;


/**
 * 统一数据响应
 *
 * @author lansu
 * @date 2023/01/14
 */
public class R<T> implements ResultCode {
    private Boolean success;

    private Integer code;

    private String message;

    private T data;


    private R() {
    }

    /**
     * 通用返回成功
     *
     * @return {@link R}
     */
    public static <T> R<T> ok() {
        R<T> r = new R<T>();
        r.success = ResultCodeEnum.SUCCESS.getSuccess();
        r.code = ResultCodeEnum.SUCCESS.getCode();
        r.message = ResultCodeEnum.SUCCESS.getMessage();
        return r;
    }


    /**
     * 失败
     *
     * @return {@link R}
     */
    public static <T> R<T> failed() {
        R<T> r = new R<T>();
        r.success = ResultCodeEnum.FAILED.getSuccess();
        r.code = ResultCodeEnum.FAILED.getCode();
        r.message = ResultCodeEnum.FAILED.getMessage();
        return r;
    }

    /**
     * 错误
     * 未知错误
     *
     * @return {@link R}
     */
    public static <T> R<T> error() {
        R<T> r = new R<T>();
        r.success = ResultCodeEnum.ERROR.getSuccess();
        r.code = ResultCodeEnum.ERROR.getCode();
        r.message = ResultCodeEnum.ERROR.getMessage();
        return r;
    }


    /**
     * 结果
     * 自定义响应码返回
     *
     * @param resultCode 结果代码
     * @return {@link R}
     */
    public static <T> R<T> result(ResultCode resultCode) {
        R<T> r = new R<T>();
        r.success = resultCode.getSuccess();
        r.code = resultCode.getCode();
        r.message = resultCode.getMessage();
        return r;
    }


    /**
     * 消息
     * 自定义消息
     *
     * @param message 消息
     * @return {@link R}
     */
    public R<T> message(String message) {
        this.message = message;
        return this;
    }

    /**
     * 数据
     * 返回自定义数据
     *
     * @param data 数据
     * @return {@link R}
     */
    public R<T> data(T data) {
        this.data = data;
        return this;
    }

    @Override
    public Boolean getSuccess() {
        return success;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
