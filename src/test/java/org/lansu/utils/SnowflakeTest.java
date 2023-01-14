package org.lansu.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SnowflakeTest {

    @Test
    void nextId() {
        long snowBit53 = Snowflake.getInstance().nextId();
        long snowParamBit53 = Snowflake.getInstance(10).nextId();
        assertTrue(String.valueOf(snowBit53).length() < 17);
        assertTrue(String.valueOf(snowParamBit53).length() < 17);
        assertThrows(IllegalArgumentException.class, () -> Snowflake.getInstance(1024).nextId());
    }

    @Test
    void nextId64() {
        long snowBit64 = Snowflake.getInstance64().nextId64();
        long snowParamBit64 = Snowflake.getInstance64(200).nextId64();
        assertTrue(String.valueOf(snowBit64).length() < 20);
        assertTrue(String.valueOf(snowParamBit64).length() < 20);
        assertThrows(IllegalArgumentException.class, () -> Snowflake.getInstance64(999999).nextId());
    }
}