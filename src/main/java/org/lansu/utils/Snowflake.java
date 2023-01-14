package org.lansu.utils;

/**
 * 雪花id生成器
 * <p>
 * 提供53bit的id，完美支持js的number类型
 *
 * @author lansu & yangzc
 * @link https://juejin.cn/post/6844903981886472206#heading-0 文章参考
 * @date 2023/01/14
 */
public class Snowflake {

    ////////////////////////////////  53bit常量参数  ////////////////////////////////
    /**
     * 初始偏移时间戳
     */
    private static final long OFFSET = 1546300800L;

    /**
     * 机器id (0~15 保留 16~31作为备份机器)
     */
    private static long WORKER_ID;
    /**
     * 机器id所占位数 (7bit, 支持最大机器数 2^7 = 128)
     */
    private static final long WORKER_ID_BITS = 7L;
    /**
     * 自增序列所占位数 (14bit, 支持最大每秒生成 2^14 = 16384)
     */
    private static final long SEQUENCE_ID_BITS = 14L;
    /**
     * 机器id偏移位数
     */
    private static final long WORKER_SHIFT_BITS = SEQUENCE_ID_BITS;
    /**
     * 自增序列偏移位数
     */
    private static final long OFFSET_SHIFT_BITS = SEQUENCE_ID_BITS + WORKER_ID_BITS;
    /**
     * 机器标识最大值 (2^7 / 2 - 1 = 128)
     */
    private static final long WORKER_ID_MAX = ((1 << WORKER_ID_BITS) - 1) >> 1;
    /**
     * 备份机器ID开始位置 (2^7 / 2 = 64)
     */
    private static final long BACK_WORKER_ID_BEGIN = (1 << WORKER_ID_BITS) >> 1;
    /**
     * 自增序列最大值 (2^14 - 1 = 16384)
     */
    private static final long SEQUENCE_MAX = (1 << SEQUENCE_ID_BITS) - 1;
    /**
     * 发生时间回拨时容忍的最大回拨时间 (秒)
     */
    private static final long BACK_TIME_MAX = 1L;

    /**
     * 上次生成ID的时间戳 (秒)
     */
    private static long lastTimestamp = 0L;
    /**
     * 当前秒内序列 (2^16)
     */
    private static long sequence = 0L;
    /**
     * 备份机器上次生成ID的时间戳 (秒)
     */
    private static long lastTimestampBak = 0L;
    /**
     * 备份机器当前秒内序列 (2^16)
     */
    private static long sequenceBak = 0L;

    private static final Snowflake SNOWFLAKE = null;


    //////////////////////////////// 64bit常量参数 ////////////////////////////////

    /**
     * 开始时间：2020-01-01 00:00:00
     * 64bit开始时间偏移量
     */
    private final long OFFSET_64 = 1577808000000L;

    /**
     * 机器id所占位数 (9bit, 支持最大机器数 2^9 = 512)
     */
    private static final long WORKER_ID_BITS_64 = 9L;

    /**
     * 自增序列所占位数 (12bit, 支持最大每秒生成 2^12 = 4096)
     */
    private static final long SEQUENCE_ID_BITS_64 = 12L;

    /**
     * 机器id偏移位数
     */
    private static final long WORKER_SHIFT_BITS_64 = SEQUENCE_ID_BITS_64;

    /**
     * 自增序列偏移位数
     */
    private static final long OFFSET_SHIFT_BITS_64 = SEQUENCE_ID_BITS_64 + WORKER_ID_BITS_64;

    /**
     * 机器标识最大值 (2^9 -1= 512)
     */
    private static final long WORKER_ID_MAX_64 = ((1 << WORKER_ID_BITS_64) - 1);

    /**
     * 自增序列最大值 (2^12 - 1 = 4096)
     */
    private static final long SEQUENCE_MAX_64 = (1 << SEQUENCE_ID_BITS_64) - 1;

    /**
     * 毫秒内序列，12位，2^12 = 4096个数字
     */
    private static long sequence_64 = 0L;

    /**
     * 上次生成ID的时间戳 (毫秒)
     */
    private static long lastTimestamp_64 = 0L;

    ////////////////////////////////  功能实现  ////////////////////////////////

    /**
     * 私有化构造
     */
    private Snowflake() {

    }

    /**
     * 对外提供实例获取
     *
     * @return {@link Snowflake} 返回实例对象
     */
    public static Snowflake getInstance() {
        return getInstance(0);
    }

    /**
     * get实例
     *
     * @param workerId 机器id
     * @return {@link Snowflake}
     */
    public static Snowflake getInstance(long workerId) {
        return getInstance(workerId, WORKER_ID_MAX);
    }

    public static Snowflake getInstance64() {
        return getInstance64(0);
    }

    public static Snowflake getInstance64(long workerId) {
        //64bit 机器id校验
        return getInstance(workerId, WORKER_ID_MAX_64);
    }

    /**
     * get实例
     *
     * @param workerId 机器id
     * @param maxId    最大id
     * @return {@link Snowflake}
     */
    private static Snowflake getInstance(long workerId, long maxId) {
        // 机器id校验
        if (workerId > maxId || workerId < 0) {
            throw new IllegalArgumentException("workerId must be between 0 and " + WORKER_ID_MAX);
        }
        WORKER_ID = workerId;
        //双重校验锁
        if (SNOWFLAKE == null) {
            synchronized (Snowflake.class) {
                if (SNOWFLAKE == null) {
                    return new Snowflake();
                }
            }
        }
        return SNOWFLAKE;
    }

    /**
     * 获取53bit id
     *
     * @return long
     */
    public long nextId() {
        long millis = System.currentTimeMillis();
        long timestamp = millis / 1000;
        // 时间回拨校验
        if (timestamp < lastTimestamp) {
            // 时间回拨超过容忍范围
            if (lastTimestamp - timestamp > BACK_TIME_MAX) {
                throw new RuntimeException(String.format("时钟回拨: now: [%d] last: [%d]", timestamp, lastTimestampBak));
            }
            // 时间回拨在容忍范围内
            timestamp = lastTimestamp;
        }
        //开始下一秒
        if (lastTimestamp != timestamp) {
            lastTimestamp = timestamp;
            sequence = 0L;
        }
        if (0L == (++sequence & SEQUENCE_MAX)) {
            // 秒内序列用尽
            sequence--;
            return nextIdBackup(Math.max(timestamp, lastTimestampBak));
        }
        return ((timestamp - OFFSET) << OFFSET_SHIFT_BITS) | (WORKER_ID << WORKER_SHIFT_BITS) | sequence;
    }

    /**
     * 下一个id备份
     *
     * @param timestamp 时间戳
     * @return long
     */
    private static long nextIdBackup(long timestamp) {
        if (timestamp < lastTimestampBak) {
            if (lastTimestampBak - (System.currentTimeMillis() / 1000) <= BACK_TIME_MAX) {
                timestamp = lastTimestampBak;
            } else {
                throw new RuntimeException(String.format("时钟回拨: now: [%d] last: [%d]", timestamp, lastTimestampBak));
            }
        }

        if (timestamp != lastTimestampBak) {
            lastTimestampBak = timestamp;
            sequenceBak = 0L;
        }

        if (0L == (++sequenceBak & SEQUENCE_MAX)) {
            // 秒内序列用尽
            return nextIdBackup(timestamp + 1);
        }

        return ((timestamp - OFFSET) << OFFSET_SHIFT_BITS) | ((WORKER_ID ^ BACK_WORKER_ID_BEGIN) << WORKER_SHIFT_BITS) | sequenceBak;
    }

    ////////////////////////////////  64bit 功能实现  ////////////////////////////////

    /**
     * 获取64位id
     *
     * @return long
     */
    public synchronized long nextId64() {
        long millis = System.currentTimeMillis();
        if (millis < lastTimestamp_64) {
            throw new RuntimeException(String.format("时钟回拨: now: [%d] last: [%d]", millis, lastTimestamp_64));
        }
        //同一时间内计算序列号
        if (millis == lastTimestamp_64) {
            if (++sequence_64 > SEQUENCE_MAX_64) {
                //毫秒秒内序列号耗尽，等待下一秒
                millis = tilNextMillis(lastTimestamp_64);
                sequence = 0L;
            }
        } else {
            //时间戳改变重置序列号
            sequence_64 = 0L;
        }
        lastTimestamp_64 = millis;
        return ((millis - OFFSET_64) << OFFSET_SHIFT_BITS_64) | (WORKER_ID << WORKER_SHIFT_BITS_64) | sequence_64;
    }

    /**
     * 阻塞至下一秒
     *
     * @param lastTimestamp64 上次stamp64
     * @return long
     */
    private long tilNextMillis(long lastTimestamp64) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp64) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

}
