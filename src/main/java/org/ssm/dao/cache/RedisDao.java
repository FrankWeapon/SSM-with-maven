package org.ssm.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ssm.entity.Seckill;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * File created by FrankWeapon on 8/9/16 for ssm.
 * Email: helldarkfire@gmail.com
 */
public class RedisDao {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);

    private JedisPool jedisPool;

    public RedisDao(String ip, int port) {
        this.jedisPool = new JedisPool(ip, port);
    }

    /**
     * get->byte[]->反序列化->Object(Seckill)
     * @param seckillId
     * @return
     */
    public Seckill getSecill(long seckillId) {
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill:" + seckillId;
                byte[] bytes = jedis.get(key.getBytes());
                if (bytes != null) {
                    Seckill seckill = schema.newMessage();
                    ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
                    return seckill;
                }
            } finally {
                jedis.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Object(Seckill)->序列化->byte[]
     * @param seckill
     * @return
     */
    public String putSeckill(Seckill seckill) {
        try {
            Jedis jedis = jedisPool.getResource();
            try {
                String key = "seckill:" + seckill.getSeckillId();
                byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
                int timeout = 60 * 60;
                return jedis.setex(key.getBytes(), timeout, bytes);
            }finally {
                jedis.close();
            }
        } catch (Exception e){
            logger.error(e.getMessage(), e);
        }
        return null;
    }
}
