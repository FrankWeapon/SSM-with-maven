-- 秒杀执行存储过程
-- in 输入参数; out 输出参数
-- row_count(): 返回上一条修改类型的sql所影响的行数
DELIMITER $$ --  console ; 转换为 $$
CREATE PROCEDURE `seckill`.`execute_seckill`
  (in v_seckill_id bigint, in v_phone BIGINT,
    in v_kill_time TIMESTAMP, out r_result int)
  BEGIN
    DECLARE insert_count  int DEFAULT 0;
    START TRANSACTION;
    insert ignore into success_killed
    (seckill_id, user_phone, create_time)
    values (v_seckill_id, v_phone, v_kill_time);
    select row_count() into insert_count;
    IF (insert_count = 0) THEN
      ROLLBACK;
      set r_result = -1;
    ELSEIF (insert_count < 0) THEN
      ROLLBACK;
      SET r_result = -2;
    ELSE
      UPDATE seckill SET number = number -1 where seckill_id = v_seckill_id and end_time > v_kill_time and start_time < v_kill_time and number > 0;
      select row_count() INTO insert_count;
      IF (insert_count = 0) THEN
        ROLLBACK ;
        SET r_result = 0;
      ELSEIF (insert_count < 0 ) THEN
        ROLLBACK ;
        SET r_result = -2;
      ELSE
        COMMIT ;
        SET r_result = 1;
      END IF;
    END IF;
  END;
$$

-- 执行存储过程
DELIMITER ;
SET @r_result = -3;
call execute_seckill(1003, 13421593832, now(), @r_result);

select @r_result;