package net.dreamparadise.srb.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import net.dreamparadise.srb.core.pojo.entity.UserLoginRecord;

/**
 * <p>
 * 用户登录记录表 服务类
 * </p>
 *
 * @author Muma
 * @since 2021-07-21
 */
public interface UserLoginRecordService extends IService<UserLoginRecord> {
  List<UserLoginRecord> listTop50(Long userId);
}
