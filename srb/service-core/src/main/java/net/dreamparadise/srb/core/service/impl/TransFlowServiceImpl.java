package net.dreamparadise.srb.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.dreamparadise.srb.core.mapper.TransFlowMapper;
import net.dreamparadise.srb.core.pojo.entity.TransFlow;
import net.dreamparadise.srb.core.service.TransFlowService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 交易流水表 服务实现类
 * </p>
 *
 * @author Muma
 * @since 2021-07-21
 */
@Service
public class TransFlowServiceImpl
  extends ServiceImpl<TransFlowMapper, TransFlow>
  implements TransFlowService {}
