package org.ssm.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.ssm.entity.SuccessKilled;

import javax.annotation.Resource;

/**
 * File created by FrankWeapon on 7/30/16 for ssm.
 * Email: helldarkfire@gmail.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {

    @Resource
    private SuccessKilledDao successKilledDao;

    @Test
    public void insertSuccessKilled() throws Exception {
        int insertCount = successKilledDao.insertSuccessKilled(1000L,123123123L);
        System.out.println(insertCount);
    }

    @Test
    public void queryByIdWithSeckill() throws Exception {
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(1000L,123123123L);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
    }

}