package net.dreamparadise.srb.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.dreamparadise.srb.core.mapper.UserIntegralMapper;
import net.dreamparadise.srb.core.pojo.entity.UserIntegral;
import net.dreamparadise.srb.core.service.UserIntegralService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户积分记录表 服务实现类
 * </p>
 *
 * @author Muma
 * @since 2021-07-21
 */
@Service
public class UserIntegralServiceImpl
  extends ServiceImpl<UserIntegralMapper, UserIntegral>
  implements UserIntegralService {}
