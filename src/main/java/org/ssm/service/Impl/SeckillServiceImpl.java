package org.ssm.service.Impl;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.ssm.dao.SeckillDao;
import org.ssm.dao.SuccessKilledDao;
import org.ssm.dao.cache.RedisDao;
import org.ssm.dto.Exposer;
import org.ssm.dto.SeckillExecution;
import org.ssm.entity.Seckill;
import org.ssm.entity.SuccessKilled;
import org.ssm.enums.SeckillStatEnum;
import org.ssm.exception.RepeatKillException;
import org.ssm.exception.SeckillCloseException;
import org.ssm.exception.SeckillException;
import org.ssm.service.SeckillService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * File created by FrankWeapon on 7/31/16 for ssm.
 * Email: helldarkfire@gmail.com
 */
@Service
public class SeckillServiceImpl implements SeckillService{

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Autowired
    private RedisDao redisDao;

    private final String slat = "stringUsedForMD5";

    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0,4);
    }

    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {

        Seckill seckill = redisDao.getSecill(seckillId);
        if(seckill == null){
            seckill = seckillDao.queryById(seckillId);
            if (seckill == null){
                return new Exposer(false, seckillId);
            } else {
                redisDao.putSeckill(seckill);
            }
        }

        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date nowTime = new Date();
        if (nowTime.getTime() < startTime.getTime() || endTime.getTime() > endTime.getTime()){
            return new Exposer(false, seckillId, startTime.getTime(), nowTime.getTime(), endTime.getTime());
        }
        String md5 = getMD5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws SeckillException {
        if(md5 == null || !md5.equals(getMD5(seckillId))){
            throw new SeckillException("seckill data rewrite");
        }
        Date nowTime = new Date();

        try{
        int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
        if (updateCount <= 0){
            throw new SeckillCloseException("seckill is closed");
        } else {
            int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
            if (insertCount <= 0){
                throw new RepeatKillException("seckill repeated");
            } else {
                SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
            }
        }
        } catch (SeckillCloseException e1){
            throw e1;
        } catch (SeckillException e2){
            throw e2;
        } catch (Exception e){
            logger.error(e.getMessage(), e);
            throw new SeckillException("seckill inner error" + e.getMessage());
        }
    }

    public SeckillExecution executeSeckillProcedure(long seckillId, long userphone, String md5) {
        if(md5 == null || !md5.equals(getMD5(seckillId))){
            return  new SeckillExecution(seckillId, SeckillStatEnum.DATA_REWRITE);
        }
        Date killTime = new Date();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("seckilId", seckillId);
        map.put("phone", userphone);
        map.put("killTime", killTime);
        map.put("result", null);
        try {
            seckillDao.killByProducure(map);
            int result = MapUtils.getInteger(map, "result", -2);
            if(result == 1){
                SuccessKilled sk = successKilledDao.queryByIdWithSeckill(seckillId, userphone);
                return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, sk);
            } else {
                return new SeckillExecution(seckillId, SeckillStatEnum.stateOf(result));
            }
        } catch (Exception e){
            logger.error(e.getMessage(), e);
            return  new SeckillExecution(seckillId, SeckillStatEnum.INNER_ERROR);
        }

    }

    private String getMD5(long seckillId){
        String base = seckillId + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }
}
