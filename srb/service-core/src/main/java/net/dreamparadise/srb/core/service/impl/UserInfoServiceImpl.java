package net.dreamparadise.srb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.dreamparadise.common.exception.Assert;
import net.dreamparadise.common.result.ResponseEnum;
import net.dreamparadise.common.util.MD5;
import net.dreamparadise.srb.base.util.JwtUtils;
import net.dreamparadise.srb.core.mapper.UserAccountMapper;
import net.dreamparadise.srb.core.mapper.UserInfoMapper;
import net.dreamparadise.srb.core.mapper.UserLoginRecordMapper;
import net.dreamparadise.srb.core.pojo.entity.UserAccount;
import net.dreamparadise.srb.core.pojo.entity.UserInfo;
import net.dreamparadise.srb.core.pojo.entity.UserLoginRecord;
import net.dreamparadise.srb.core.pojo.query.UserInfoQuery;
import net.dreamparadise.srb.core.pojo.vo.LoginVo;
import net.dreamparadise.srb.core.pojo.vo.RegisterVo;
import net.dreamparadise.srb.core.pojo.vo.UserInfoVo;
import net.dreamparadise.srb.core.service.UserInfoService;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户基本信息 服务实现类
 * </p>
 *
 * @author Muma
 * @since 2021-07-21
 */
@Service
public class UserInfoServiceImpl
  extends ServiceImpl<UserInfoMapper, UserInfo>
  implements UserInfoService {

  @Autowired
  private UserAccountMapper userAccountMapper;

  @Autowired
  private UserLoginRecordMapper userLoginRecordMapper;

  @Transactional(rollbackFor = Exception.class)
  @Override
  public UserInfoVo login(LoginVo loginVo, String ip) {
    String mobile = loginVo.getMobile();
    String password = loginVo.getPassword();
    Integer userType = loginVo.getUserType();

    QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
    userInfoQueryWrapper.eq("mobile", mobile).eq("user_type", userType);
    UserInfo userInfo = baseMapper.selectOne(userInfoQueryWrapper);
    Assert.notNull(userInfo, ResponseEnum.LOGIN_MOBILE_ERROR);
    Assert.equals(
      MD5.encrypt(password),
      userInfo.getPassword(),
      ResponseEnum.LOGIN_PASSWORD_ERROR
    );
    Assert.equals(
      userInfo.getStatus(),
      UserInfo.STATUS_NORMAL,
      ResponseEnum.LOGIN_LOKED_ERROR
    );

    UserLoginRecord userLoginRecord = new UserLoginRecord();
    userLoginRecord.setUserId(userInfo.getId());
    userLoginRecord.setIp(ip);
    userLoginRecordMapper.insert(userLoginRecord);

    String token = JwtUtils.createToken(userInfo.getId(), userInfo.getName());
    UserInfoVo userInfoVo = new UserInfoVo();
    BeanUtils.copyProperties(userInfo, userInfoVo);
    userInfoVo.setToken(token);

    return userInfoVo;
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void register(RegisterVo registerVo) {
    QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("mobile", registerVo.getMobile());
    Integer count = baseMapper.selectCount(queryWrapper);
    Assert.isTrue(count == 0, ResponseEnum.MOBILE_EXIST_ERROR);
    UserInfo userInfo = new UserInfo();
    copy2UserInfo(userInfo, registerVo);
    baseMapper.insert(userInfo);
    UserAccount userAccount = new UserAccount();
    userAccount.setUserId(userInfo.getId());
    userAccountMapper.insert(userAccount);
  }

  @Override
  public IPage<UserInfo> listPage(
    Page<UserInfo> userInfoPage,
    UserInfoQuery userInfoQuery
  ) {
    if (userInfoQuery == null) {
      return baseMapper.selectPage(userInfoPage, null);
    }
    String mobile = userInfoQuery.getMobile();
    Integer status = userInfoQuery.getStatus();
    Integer userType = userInfoQuery.getUserType();
    QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
    userInfoQueryWrapper
      .eq(StringUtils.isNotBlank(mobile), "mobile", mobile)
      .eq(status != null, "status", status)
      .eq(userType != null, "user_type", userType);
    return baseMapper.selectPage(userInfoPage, userInfoQueryWrapper);
  }

  @Override
  public void lock(Long id, Integer status) {
    UserInfo userInfo = new UserInfo();
    userInfo.setId(id);
    userInfo.setStatus(status);
    baseMapper.updateById(userInfo);
  }

  @Override
  public boolean checkMobile(String mobile) {
    QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
    userInfoQueryWrapper.eq("mobile", mobile);
    Integer count = baseMapper.selectCount(userInfoQueryWrapper);
    return count > 0;
  }

  private void copy2UserInfo(UserInfo userInfo, RegisterVo registerVo) {
    userInfo.setUserType(registerVo.getUserType());
    userInfo.setNickName(registerVo.getMobile());
    userInfo.setName(registerVo.getMobile());
    userInfo.setMobile(registerVo.getMobile());
    userInfo.setPassword(MD5.encrypt(registerVo.getPassword()));
    userInfo.setStatus(UserInfo.STATUS_NORMAL);
    userInfo.setHeadImg(UserInfo.USER_AVATAR);
  }
}
