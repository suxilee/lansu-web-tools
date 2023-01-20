# 兰溯web工具类

## 1.响应结果封装R.class
```java
//数据结构：
{
    "success": true,
    "code": 200,
    "msg": "success",
    "data": {}
}
//返回成功
R.ok();
//自定成功消息
R res = R.ok().message("响应测试");
//更多用法参考Wiki...
```
## 2.雪花ID生成Sonwflake.class
提供两种雪花id生成方式，64bit与53bit;  
53bit完美适配前端number类型，解决丢失精度问题;
53bit生成id时间采用31bit时间戳秒级处理，64bit生成id采用42bit时间戳毫秒级处理，均可使用至2100年。  

```java
//生成ID 53bit
SnowflakeUtils.getInstance().nextId();
//生成ID 64bit
SnowflakeUtils.getInstance64().nextId64();
```
## 3.自定义异常WebException.class
```java
//抛出异常
thorw new WebException("自定义异常");
//支持ResultCode封装的枚举类
thorw new WebException(ResultCodeEnum.PARAM_ERROR);
```