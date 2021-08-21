package net.dreamparadise.srb.core.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import net.dreamparadise.srb.core.pojo.entity.UserInfo;
import net.dreamparadise.srb.core.pojo.query.UserInfoQuery;
import net.dreamparadise.srb.core.pojo.vo.LoginVo;
import net.dreamparadise.srb.core.pojo.vo.RegisterVo;
import net.dreamparadise.srb.core.pojo.vo.UserInfoVo;

/**
 * <p>
 * 用户基本信息 服务类
 * </p>
 *
 * @author Muma
 * @since 2021-07-21
 */
public interface UserInfoService extends IService<UserInfo> {
  UserInfoVo login(LoginVo loginVo, String ip);

  void register(RegisterVo registerVo);

  IPage<UserInfo> listPage(
    Page<UserInfo> userInfoPage,
    UserInfoQuery userInfoQuery
  );

  void lock(Long id, Integer status);

  boolean checkMobile(String mobile);
}
