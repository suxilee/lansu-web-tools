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
@Data
public class R implements ResultCode {
    private Boolean success;

    private Integer code;

    private String message;

    private Object data;


    private R() {
    }

    /**
     * 通用返回成功
     *
     * @return {@link R}
     */
    public static R ok() {
        R r = new R();
        r.setSuccess(ResultCodeEnum.SUCCESS.getSuccess());
        r.setCode(ResultCodeEnum.SUCCESS.getCode());
        r.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return r;
    }


    /**
     * 失败
     *
     * @return {@link R}
     */
    public static R failed() {
        R r = new R();
        r.setSuccess(ResultCodeEnum.FAILED.getSuccess());
        r.setCode(ResultCodeEnum.FAILED.getCode());
        r.setMessage(ResultCodeEnum.FAILED.getMessage());
        return r;
    }

    /**
     * 错误
     * 未知错误
     *
     * @return {@link R}
     */
    public static R error() {
        R r = new R();
        r.setSuccess(ResultCodeEnum.ERROR.getSuccess());
        r.setCode(ResultCodeEnum.ERROR.getCode());
        r.setMessage(ResultCodeEnum.ERROR.getMessage());
        return r;
    }


    /**
     * 结果
     * 自定义响应码返回
     *
     * @param resultCode 结果代码
     * @return {@link R}
     */
    public static R result(ResultCode resultCode) {
        R r = new R();
        r.setSuccess(resultCode.getSuccess());
        r.setCode(resultCode.getCode());
        r.setMessage(resultCode.getMessage());
        return r;
    }


    /**
     * 消息
     * 自定义消息
     *
     * @param message 消息
     * @return {@link R}
     */
    public R message(String message) {
        this.setMessage(message);
        return this;
    }

    /**
     * 数据
     * 返回自定义数据
     *
     * @param data 数据
     * @return {@link R}
     */
    public R data(Object data) {
        this.setData(data);
        return this;
    }

    /**
     * 数据
     * 多数据处理
     *
     * @param data 数据
     * @return {@link R}
     */
    public R data(Object... data) {
        List<Object> collect = Arrays.stream(data).collect(Collectors.toList());
        this.setData(collect);
        return this;
    }

    /**
     * 数据
     * 键值对格式
     *
     * @param key   钥匙
     * @param value 值
     * @return {@link R}
     */
    public R data(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        this.setData(map);
        return this;
    }

    /**
     * 数据
     * 键值对格式
     *
     * @param map 地图
     * @return {@link R}
     */
    public R data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }
}
