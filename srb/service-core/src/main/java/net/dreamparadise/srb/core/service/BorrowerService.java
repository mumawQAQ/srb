package net.dreamparadise.srb.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.dreamparadise.srb.core.pojo.entity.Borrower;
import net.dreamparadise.srb.core.pojo.vo.BorrowerVo;

/**
 * <p>
 * 借款人 服务类
 * </p>
 *
 * @author Muma
 * @since 2021-07-21
 */
public interface BorrowerService extends IService<Borrower> {
  void saveBorrowerVoByUserId(BorrowerVo borrowerVo, Long userId);
}
