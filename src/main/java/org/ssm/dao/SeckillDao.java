package org.ssm.dao;

import org.apache.ibatis.annotations.Param;
import org.ssm.entity.Seckill;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * File created by FrankWeapon on 7/29/16 for ssm.
 * Email: helldarkfire@gmail.com
 */
public interface SeckillDao {
    /**
     * 减库存
     * @param seckillId
     * @param killTime
     * @return 返回值>0 表示插入成功
     */
    int reduceNumber (@Param("seckillId") long seckillId, @Param("killTime") Date killTime);

    /**
     * 根据id查询商品信息
     * @param seckillId
     * @return
     */
    Seckill queryById(@Param("seckillId") long seckillId);

    /**
     * 根据偏移量查询商品列表
     * @param offset
     * @param limit
     * @return
     */
    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);

    void killByProducure(Map<String, Object> paramMap);
}
