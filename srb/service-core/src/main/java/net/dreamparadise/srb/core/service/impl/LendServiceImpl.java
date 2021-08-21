package net.dreamparadise.srb.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.dreamparadise.srb.core.mapper.LendMapper;
import net.dreamparadise.srb.core.pojo.entity.Lend;
import net.dreamparadise.srb.core.service.LendService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 标的准备表 服务实现类
 * </p>
 *
 * @author Muma
 * @since 2021-07-21
 */
@Service
public class LendServiceImpl
  extends ServiceImpl<LendMapper, Lend>
  implements LendService {}
