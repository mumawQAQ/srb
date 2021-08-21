package net.dreamparadise.srb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import net.dreamparadise.srb.core.mapper.UserLoginRecordMapper;
import net.dreamparadise.srb.core.pojo.entity.UserLoginRecord;
import net.dreamparadise.srb.core.service.UserLoginRecordService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户登录记录表 服务实现类
 * </p>
 *
 * @author Muma
 * @since 2021-07-21
 */
@Service
public class UserLoginRecordServiceImpl
  extends ServiceImpl<UserLoginRecordMapper, UserLoginRecord>
  implements UserLoginRecordService {

  @Override
  public List<UserLoginRecord> listTop50(Long userId) {
    QueryWrapper<UserLoginRecord> objectQueryWrapper = new QueryWrapper<>();
    objectQueryWrapper
      .eq("user_id", userId)
      .orderByDesc("id")
      .last(" limit 50");
    List<UserLoginRecord> userLoginRecords = baseMapper.selectList(
      objectQueryWrapper
    );
    return userLoginRecords;
  }
}
