package net.dreamparadise.srb.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.dreamparadise.srb.core.mapper.LendItemReturnMapper;
import net.dreamparadise.srb.core.pojo.entity.LendItemReturn;
import net.dreamparadise.srb.core.service.LendItemReturnService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 标的出借回款记录表 服务实现类
 * </p>
 *
 * @author Muma
 * @since 2021-07-21
 */
@Service
public class LendItemReturnServiceImpl
  extends ServiceImpl<LendItemReturnMapper, LendItemReturn>
  implements LendItemReturnService {}
