package net.dreamparadise.srb.core;

import java.util.concurrent.TimeUnit;
import net.dreamparadise.srb.core.mapper.DictMapper;
import net.dreamparadise.srb.core.pojo.entity.Dict;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisTemplateTests {

  @Autowired
  private RedisTemplate redisTemplate;

  @Autowired
  private DictMapper dictMapper;

  @Test
  public void saveDict() {
    Dict dict = dictMapper.selectById(1);
    redisTemplate.opsForValue().set("dict", dict, 5, TimeUnit.MINUTES);
  }
}
