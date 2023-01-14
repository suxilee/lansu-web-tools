package org.lansu.result;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 结果代码
 *
 * @author lansu
 * @date 2023/01/14
 */

@AllArgsConstructor
@Getter
public enum ResultCodeEnum implements ResultCode {

    /**
     * 成功
     */
    SUCCESS(true,200,"成功"),
    /**
     * 失败
     */
    FAILED(false,400,"失败"),
    /**
     * 错误
     */
    ERROR(false,500,"系统错误"),
    /**
     * 未认证
     */
    UNAUTHENTICATED(false,401,"未认证"),
    /**
     * 未授权
     */
    UNAUTHORIZED(false,403,"未授权"),

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
