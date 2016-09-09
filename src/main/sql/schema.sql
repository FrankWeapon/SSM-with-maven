
CREATE TABLE seckill(
  `seckill_id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '商品库存ID',
  `name` varchar(20) NOT NULL COMMENT '商品名称',
  `number` INT NOT NULL COMMENT '库存数量',
  `start_time` TIMESTAMP NOT NULL COMMENT '开始时间',
  `end_time` TIMESTAMP NOT NULL COMMENT '开始时间',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (seckill_id),
  key idx_start_time(start_time),
  key idx_end_time(end_time),
  key idx_create_time(create_time)
)ENGINE = InnoDB AUTO_INCREMENT=1000 DEFAULT CHAR SET utf8 COMMENT = '秒杀库存表';

INSERT INTO seckill(name, number, start_time, end_time)
    VALUES
      ('1000元秒杀iphone',200,'2016-11-11 00:00:00', '2016-11-12 00:00:00'),
      ('800秒杀魅族',400,'2016-11-11 00:00:00', '2016-11-12 00:00:00'),
      ('500秒杀小米',500,'2016-11-11 00:00:00', '2016-11-12 00:00:00'),
      ('300秒杀酷派',800,'2016-11-11 00:00:00', '2016-11-12 00:00:00'),
      ('100元秒杀昂达',1000,'2016-11-11 00:00:00', '2016-11-12 00:00:00');


CREATE TABLE success_killed(
  `seckill_id` BIGINT NOT NULL COMMENT '秒杀商品ID',
  `user_phone` BIGINT NOT NULL COMMENT '用户手机号',
  `state` TINYINT NOT NULL DEFAULT -1 COMMENT '状态表示 -1:无效, 0:成功, 1:已付款',
  `create_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (seckill_id,user_phone),
  key idx_create_time(create_time)
)ENGINE = InnoDB DEFAULT CHAR SET = utf8 COMMENT '秒杀成功明细表';
