package org.lansu.result;

/**
 * 响应信息接口
 *
 * @author lansu
 * @date 2023/01/14
 */
public interface ResultCode {
    /**
     * 响应是否成功
     */
    Boolean success =true;

    /**
     * 响应状态码
     */
    Integer code=200;

    /**
     * 响应自定义信息
     */
    String message="成功";

    /**
     * 获得响应结果
     *
     * @return {@link Boolean}
     */
    Boolean getSuccess();

    /**
     * 获得响应状态码
     *
     * @return {@link Integer}
     */
    Integer getCode();

    /**
     * 获得响应自定义信息
     *
     * @return {@link String}
     */
    String getMessage();


}
