package org.lansu.util.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * 分页数据转换工具
 * R：源数据 T:目标对象
 *
 * @author lansu
 * @version 1.0.0
 * @since 2023/01/19
 */
public class PageConvertUtils<R, T> {
    /**
     * 分页数据转换
     *
     * @param page    分页数据
     * @param convert 转换器
     * @return 转换后的分页数据
     */
    public static <R, T> Page<T> convert(IPage<R> page, Convert<R, T> convert) {
        List<R> records = page.getRecords();
        List<T> tList = records.stream().map(convert::convert).toList();
        Page<T> tPage = new Page<>();
        tPage.setSize(page.getSize());
        tPage.setCurrent(page.getCurrent());
        tPage.setPages(page.getPages());
        tPage.setTotal(page.getTotal());
        tPage.setRecords(tList);
        return tPage;
    }
}
