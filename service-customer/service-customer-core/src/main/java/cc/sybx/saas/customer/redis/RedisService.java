package cc.sybx.saas.customer.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedisService {
    @Resource
    private RedisTemplate<String, ?> redisTemplate;

    public String hget(final String key, final String field) {
        try {
            return redisTemplate.execute((RedisCallback<String>) redisConnection -> {
                byte[] bytes = redisConnection.hGet(redisTemplate.getStringSerializer().serialize(key),
                        redisTemplate.getStringSerializer().serialize(field));
                return null != bytes ? redisTemplate.getStringSerializer().deserialize(bytes) : null;
            });
        } catch (Exception e) {
            log.error("hget value from redis fail...", e);
        }

        return null;
    }

    public boolean hset(final String key, final String field, final String value) {
        try {
            return redisTemplate.execute((RedisCallback<Boolean>) redisConnection ->
                    redisConnection.hSet(redisTemplate.getStringSerializer().serialize(key),
                            redisTemplate.getStringSerializer().serialize(field),
                            redisTemplate.getStringSerializer().serialize(value)));
        } catch (Exception e) {
            log.error("hset value to redis fail...", e);
        }
        return false;
    }

    /**
     * 保存字符串到redis中
     */
    public boolean setString(final String key, final String value) {
        try {
            return redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
                redisConnection.set(redisTemplate.getStringSerializer().serialize(key),
                        redisTemplate.getStringSerializer().serialize(value));
                return Boolean.TRUE;
            });
        } catch (Exception e) {
            log.error("putString value to redis fail...", e);
        }
        return false;
    }

    /**
     * 以分钟为单位设置key的超时时间
     *
     * @param key        key
     * @param expireTime 超时时间
     * @return boolean
     */
    public boolean expireByMinutes(String key, Long expireTime) {
        return redisTemplate.expire(key, expireTime, TimeUnit.MINUTES);
    }

    /**
     * 保存字符串到redis中,失效时间单位秒
     */
    public boolean setString(final String key, final String value,final long seconds) {
        try {
            return redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
                redisConnection.setEx(redisTemplate.getStringSerializer().serialize(key),
                        seconds,redisTemplate.getStringSerializer().serialize(value));
                return Boolean.TRUE;
            });
        } catch (Exception e) {
            log.error("putString value to redis fail...", e);
        }
        return false;
    }
}
