package net.dreamparadise.srb.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.dreamparadise.srb.core.mapper.LendItemMapper;
import net.dreamparadise.srb.core.pojo.entity.LendItem;
import net.dreamparadise.srb.core.service.LendItemService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 标的出借记录表 服务实现类
 * </p>
 *
 * @author Muma
 * @since 2021-07-21
 */
@Service
public class LendItemServiceImpl
  extends ServiceImpl<LendItemMapper, LendItem>
  implements LendItemService {}
