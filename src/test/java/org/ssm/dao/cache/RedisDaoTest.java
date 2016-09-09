package org.ssm.dao.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.ssm.dao.SeckillDao;
import org.ssm.entity.Seckill;

/**
 * File created by FrankWeapon on 8/10/16 for ssm.
 * Email: helldarkfire@gmail.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class RedisDaoTest {

    private long id = 1001;

    @Autowired
    RedisDao redisDao;
    @Autowired
    SeckillDao seckillDao;

    @Test
    public void testSeckill() throws Exception {
        Seckill seckill = redisDao.getSecill(id);
        if(seckill == null){
            seckill = seckillDao.queryById(id);
            if(seckill != null) {
                String result = redisDao.putSeckill(seckill);
                System.out.println(result);
                seckill = redisDao.getSecill(id);
                System.out.println(seckill);
            }
        }
    }

}