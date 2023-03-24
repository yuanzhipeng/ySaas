package cc.sybx.saas.util;

import cc.sybx.saas.common.exception.SaasRuntimeException;
import cc.sybx.saas.common.util.CommonErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class RedisService {

    @Resource
    private RedisTemplate<String, ?> redisTemplate;

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
     * 从redis 中查询字符串对象
     */
    public String getString(final String key) {
        try {
            return redisTemplate.execute((RedisCallback<String>) connection -> {
                byte[] bytes = connection.get(redisTemplate.getStringSerializer().serialize(key));
                return null != bytes ? redisTemplate.getStringSerializer().deserialize(bytes) : null;
            });
        } catch (Exception e) {
            throw new SaasRuntimeException(CommonErrorCode.FAILED);
        }
    }

    /**
     * 根据key删除缓存
     *
     * @param key
     * @return
     */
    public void delete(final String key) {
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            log.error("Delete cache fail and key : {}", key);
        }
    }

    public Map<String, String> hgetall(final String key) {
        try {
            return redisTemplate.execute((RedisCallback<Map<String, String>>) con -> {
                Map<byte[], byte[]> result = con.hGetAll(key.getBytes());
                if (CollectionUtils.isEmpty(result)) {
                    return new HashMap<>(0);
                }
                Map<String, String> ans = new HashMap<>(result.size());
                for (Map.Entry<byte[], byte[]> entry : result.entrySet()) {
                    ans.put(new String(entry.getKey()), new String(entry.getValue()));
                }
                return ans;
            });
        } catch (Exception e) {
            log.error("hget value from redis fail...", e);
        }
        return null;
    }

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
}
