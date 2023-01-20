package org.lansu.result;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RTest {

    @Test
    void ok() {
        R<Object> r = R.ok();
        assertTrue(r.getSuccess());
        assertEquals(200, r.getCode());
        assertEquals("成功", r.getMessage());
    }

    @Test
    void failed() {
        R<Object> r = R.failed();
        assertFalse(r.getSuccess());
        assertEquals(400, r.getCode());
        assertEquals("失败", r.getMessage());
    }

    @Test
    void error() {
        R<Object> r = R.error();
        assertFalse(r.getSuccess());
        assertEquals(500, r.getCode());
        assertEquals("系统错误", r.getMessage());
    }

    @Test
    void result() {
        R<Object> r = R.result(ResultCodeEnum.SUCCESS);
        assertTrue(r.getSuccess());
        assertEquals(200, r.getCode());
        assertEquals("成功", r.getMessage());
    }

    @Test
    void message() {
        R<Object> r = R.ok().message("自定义消息");
        assertTrue(r.getSuccess());
        assertEquals(200, r.getCode());
        assertEquals("自定义消息", r.getMessage());
    }

    @Test
    void data() {
        R<Object> r = R.ok().data("自定义数据");
        assertTrue(r.getSuccess());
        assertEquals(200, r.getCode());
        assertEquals("成功", r.getMessage());
        assertEquals("自定义数据", r.getData());
    }
}