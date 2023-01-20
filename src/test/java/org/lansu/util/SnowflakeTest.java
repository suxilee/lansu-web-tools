package org.lansu.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SnowflakeTest {

    @Test
    void nextId() {
        long snowBit53 = SnowflakeUtils.getInstance().nextId();
        long snowParamBit53 = SnowflakeUtils.getInstance(10).nextId();
        assertTrue(String.valueOf(snowBit53).length() < 17);
        assertTrue(String.valueOf(snowParamBit53).length() < 17);
        assertThrows(IllegalArgumentException.class, () -> SnowflakeUtils.getInstance(1024).nextId());
    }

    @Test
    void nextId64() {
        long snowBit64 = SnowflakeUtils.getInstance64().nextId64();
        long snowParamBit64 = SnowflakeUtils.getInstance64(200).nextId64();
        assertTrue(String.valueOf(snowBit64).length() < 20);
        assertTrue(String.valueOf(snowParamBit64).length() < 20);
        assertThrows(IllegalArgumentException.class, () -> SnowflakeUtils.getInstance64(999999).nextId());
    }
}