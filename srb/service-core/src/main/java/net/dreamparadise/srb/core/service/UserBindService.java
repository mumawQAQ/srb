package net.dreamparadise.srb.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import java.util.Map;
import net.dreamparadise.srb.core.pojo.entity.UserBind;
import net.dreamparadise.srb.core.pojo.vo.UserBindVo;

/**
 * <p>
 * 用户绑定表 服务类
 * </p>
 *
 * @author Muma
 * @since 2021-07-21
 */
public interface UserBindService extends IService<UserBind> {
  String commitBindUser(UserBindVo userBindVo, Long userId);

  void notify(Map<String, Object> stringObjectMap);
}
