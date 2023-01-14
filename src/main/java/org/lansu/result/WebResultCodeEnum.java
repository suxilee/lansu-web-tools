package org.lansu.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * web结果代码枚举
 *
 * @author lansu
 * @date 2023/01/14
 */
@Getter
@AllArgsConstructor
public enum WebResultCodeEnum implements ResultCode {

    /**
     * 参数错误
     */
    PARAM_ERROR(false, 418, "参数错误"),
    ;

    /**
     * 响应是否成功
     */
    private final Boolean success;

    /**
     * 响应状态码
     */
    private final Integer code;

    /**
     * 响应自定义信息
     */
    private final String message;



}
