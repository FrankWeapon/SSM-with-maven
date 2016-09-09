package org.ssm.dao;

import org.apache.ibatis.annotations.Param;
import org.ssm.entity.SuccessKilled;

/**
 * File created by FrankWeapon on 7/29/16 for ssm.
 * Email: helldarkfire@gmail.com
 */
public interface SuccessKilledDao {
    /**
     * 插入购买明细
     * @param seckillId
     * @param userphone
     * @return 返回值>0 表示插入成功
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userphone);

    /**
     * 根据id查询successkilled并携带产品对象实体
     * @param seckillId
     * @return
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);

}
