package org.lansu.util.controller;

/**
 * 实体转换
 *
 * @author lansu
 * @version 1.0.0
 * @since 2023/01/19
 */
public interface Convert <R,T> {
    /**
     * 转换
     *
     * @param r 源数据
     * @return {@link T} 目标数据
     */
    T convert(R r);


}
