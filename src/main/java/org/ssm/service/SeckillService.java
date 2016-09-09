package org.ssm.service;

import org.ssm.dto.Exposer;
import org.ssm.dto.SeckillExecution;
import org.ssm.entity.Seckill;
import org.ssm.exception.RepeatKillException;
import org.ssm.exception.SeckillCloseException;
import org.ssm.exception.SeckillException;

import java.util.List;

/**
 * File created by FrankWeapon on 7/31/16 for ssm.
 * Email: helldarkfire@gmail.com
 */
public interface SeckillService {

    /**
     * 查询所有秒杀记录
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 查询单个秒杀记录
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 秒杀开启输出地址, 否则输出时间
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws
            SeckillException, RepeatKillException, SeckillCloseException;

    /**
     * 通过存储过程执行秒杀操作
     */
    SeckillExecution executeSeckillProcedure(long seckillI, long userphone, String md5);
}
