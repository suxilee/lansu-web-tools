package org.lansu.result;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RTest {

    @Test
    void ok() {
        R r = R.ok();
        assertTrue(r.getSuccess());
        assertEquals(200, r.getCode());
        assertEquals("成功", r.getMessage());
    }

    @Test
    void failed() {
        R r = R.failed();
        assertFalse(r.getSuccess());
        assertEquals(400, r.getCode());
        assertEquals("失败", r.getMessage());
    }

    @Test
    void error() {
        R r = R.error();
        assertFalse(r.getSuccess());
        assertEquals(500, r.getCode());
        assertEquals("系统错误", r.getMessage());
    }

    @Test
    void result() {
        R r = R.result(ResultCodeEnum.SUCCESS);
        assertTrue(r.getSuccess());
        assertEquals(200, r.getCode());
        assertEquals("成功", r.getMessage());
    }

    @Test
    void message() {
        R r = R.ok().message("自定义消息");
        assertTrue(r.getSuccess());
        assertEquals(200, r.getCode());
        assertEquals("自定义消息", r.getMessage());
    }

    @Test
    void data() {
        R r = R.ok().data("自定义数据");
        assertTrue(r.getSuccess());
        assertEquals(200, r.getCode());
        assertEquals("成功", r.getMessage());
        assertEquals("自定义数据", r.getData());
    }
}