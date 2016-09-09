package org.ssm.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.ssm.dto.Exposer;
import org.ssm.dto.SeckillExecution;
import org.ssm.entity.Seckill;
import org.ssm.exception.RepeatKillException;
import org.ssm.exception.SeckillCloseException;

import java.util.List;

/**
 * File created by FrankWeapon on 8/7/16 for ssm.
 * Email: helldarkfire@gmail.com
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() throws Exception {
        List<Seckill> list = seckillService.getSeckillList();
        logger.info("list={}", list);
    }

    @Test
    public void getById() throws Exception {
        Seckill seckill = seckillService.getById(1000);
        logger.info("seckill={}", seckill);
    }

    @Test
    public void exportSeckillUrl() throws Exception {
        long id = 1000;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        logger.info("exposer={}", exposer);
    }

    @Test
    public void executeSeckill() throws Exception {
        long id = 1000L;
        long phone = 123453256L;
        String md5 = "99d81b86281da0cda731c3d4c2b82779";
//        try{
//            SeckillExecution seckillExecution = seckillService.executeSeckill(id, phone, md5);
//            logger.info("result={}",seckillExecution);
//        } catch (RepeatKillException e){
//            logger.error(e.getMessage());
//        } catch (SeckillCloseException e){
//            logger.error(e.getMessage());
//        }
    }

    @Test
    public void testSeckillLogic() throws Exception {
        long id = 1000;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        logger.info("exposer={}", exposer);
        long phone = 123453256L;
        if (exposer.isExposed()) {

            String md5 = exposer.getMd5();
            try {
                SeckillExecution seckillExecution = seckillService.executeSeckill(id, phone, md5);
                logger.info("result={}", seckillExecution);
            } catch (RepeatKillException e) {
                logger.error(e.getMessage());
            } catch (SeckillCloseException e) {
                logger.error(e.getMessage());
            }
        } else {
            logger.warn("exposer={}", exposer);
        }
    }

    @Test
    private void executeSeckillProcedure(){
        long seckillId = 1001;
        long phone = 12314125;
        String md5 = null;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if (exposer.isExposed()){
            md5 = exposer.getMd5();
            SeckillExecution seckillExecution = seckillService.executeSeckillProcedure(seckillId,phone,md5);
            logger.info(seckillExecution.getStateInfo());
        }
    }

}