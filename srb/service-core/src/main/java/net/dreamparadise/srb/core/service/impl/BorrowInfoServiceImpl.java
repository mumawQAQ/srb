package net.dreamparadise.srb.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.dreamparadise.srb.core.mapper.BorrowInfoMapper;
import net.dreamparadise.srb.core.pojo.entity.BorrowInfo;
import net.dreamparadise.srb.core.service.BorrowInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 借款信息表 服务实现类
 * </p>
 *
 * @author Muma
 * @since 2021-07-21
 */
@Service
public class BorrowInfoServiceImpl
  extends ServiceImpl<BorrowInfoMapper, BorrowInfo>
  implements BorrowInfoService {}
