package net.dreamparadise.srb.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.dreamparadise.srb.core.mapper.IntegralGradeMapper;
import net.dreamparadise.srb.core.pojo.entity.IntegralGrade;
import net.dreamparadise.srb.core.service.IntegralGradeService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 积分等级表 服务实现类
 * </p>
 *
 * @author Muma
 * @since 2021-07-21
 */
@Service
public class IntegralGradeServiceImpl
  extends ServiceImpl<IntegralGradeMapper, IntegralGrade>
  implements IntegralGradeService {}
