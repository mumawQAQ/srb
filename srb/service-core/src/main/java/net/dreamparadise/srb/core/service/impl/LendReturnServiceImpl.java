package net.dreamparadise.srb.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.dreamparadise.srb.core.mapper.LendReturnMapper;
import net.dreamparadise.srb.core.pojo.entity.LendReturn;
import net.dreamparadise.srb.core.service.LendReturnService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 还款记录表 服务实现类
 * </p>
 *
 * @author Muma
 * @since 2021-07-21
 */
@Service
public class LendReturnServiceImpl
  extends ServiceImpl<LendReturnMapper, LendReturn>
  implements LendReturnService {}
