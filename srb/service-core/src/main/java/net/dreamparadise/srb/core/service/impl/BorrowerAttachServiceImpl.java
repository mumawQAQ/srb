package net.dreamparadise.srb.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.dreamparadise.srb.core.mapper.BorrowerAttachMapper;
import net.dreamparadise.srb.core.pojo.entity.BorrowerAttach;
import net.dreamparadise.srb.core.service.BorrowerAttachService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 借款人上传资源表 服务实现类
 * </p>
 *
 * @author Muma
 * @since 2021-07-21
 */
@Service
public class BorrowerAttachServiceImpl
  extends ServiceImpl<BorrowerAttachMapper, BorrowerAttach>
  implements BorrowerAttachService {}
