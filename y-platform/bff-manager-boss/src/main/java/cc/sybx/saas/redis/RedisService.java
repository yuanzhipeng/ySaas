package cc.sybx.saas.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

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

    public boolean hsetPipeline(final String key, final List<RedisHsetBean> fieldValues) {
        try {
            return redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    redisConnection.openPipeline();
                    for (RedisHsetBean bean : fieldValues) {
                        redisConnection.hSet(redisTemplate.getStringSerializer().serialize(key),
                                redisTemplate.getStringSerializer().serialize(bean.getField()),
                                redisTemplate.getStringSerializer().serialize(bean.getValue()));
                    }
                    List<Object> objects = redisConnection.closePipeline();
                    return !CollectionUtils.isEmpty(objects);
                }
            });
        } catch (Exception e) {
            log.error("hsetPipeline value to redis fail...", e);
        }
        return false;
    }
}
