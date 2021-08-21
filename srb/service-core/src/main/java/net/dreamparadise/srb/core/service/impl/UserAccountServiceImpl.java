package net.dreamparadise.srb.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.dreamparadise.srb.core.mapper.UserAccountMapper;
import net.dreamparadise.srb.core.pojo.entity.UserAccount;
import net.dreamparadise.srb.core.service.UserAccountService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户账户 服务实现类
 * </p>
 *
 * @author Muma
 * @since 2021-07-21
 */
@Service
public class UserAccountServiceImpl
  extends ServiceImpl<UserAccountMapper, UserAccount>
  implements UserAccountService {}
