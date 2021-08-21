package net.dreamparadise.srb.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.HashMap;
import java.util.Map;
import net.dreamparadise.common.exception.Assert;
import net.dreamparadise.common.result.ResponseEnum;
import net.dreamparadise.srb.core.enums.UserBindEnum;
import net.dreamparadise.srb.core.hfb.FormHelper;
import net.dreamparadise.srb.core.hfb.HfbConst;
import net.dreamparadise.srb.core.hfb.RequestHelper;
import net.dreamparadise.srb.core.mapper.UserBindMapper;
import net.dreamparadise.srb.core.mapper.UserInfoMapper;
import net.dreamparadise.srb.core.pojo.entity.UserBind;
import net.dreamparadise.srb.core.pojo.entity.UserInfo;
import net.dreamparadise.srb.core.pojo.vo.UserBindVo;
import net.dreamparadise.srb.core.service.UserBindService;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户绑定表 服务实现类
 * </p>
 *
 * @author Muma
 * @since 2021-07-21
 */
@Service
public class UserBindServiceImpl
  extends ServiceImpl<UserBindMapper, UserBind>
  implements UserBindService {

  @Autowired
  private UserInfoMapper userInfoMapper;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public String commitBindUser(UserBindVo userBindVo, Long userId) {
    QueryWrapper<UserBind> userBindQueryWrapper = new QueryWrapper<>();
    userBindQueryWrapper
      .eq("id_card", userBindVo.getIdCard())
      .ne("user_id", userId);
    UserBind userBind = baseMapper.selectOne(userBindQueryWrapper);
    Assert.isNull(userBind, ResponseEnum.USER_BIND_IDCARD_EXIST_ERROR);

    userBindQueryWrapper = new QueryWrapper<>();
    userBindQueryWrapper.eq("user_id", userId);
    userBind = baseMapper.selectOne(userBindQueryWrapper);
    if (userBind == null) {
      userBind = new UserBind();
      BeanUtils.copyProperties(userBindVo, userBind);
      userBind.setUserId(userId);
      userBind.setStatus(UserBindEnum.NO_BIND.getStatus());
      baseMapper.insert(userBind);
    } else {
      BeanUtils.copyProperties(userBindVo, userBind);
      baseMapper.updateById(userBind);
    }

    HashMap<String, Object> paramMap = new HashMap<>();
    paramMap.put("agentId", HfbConst.AGENT_ID);
    paramMap.put("agentUserId", userId);
    paramMap.put("idCard", userBindVo.getIdCard());
    paramMap.put("personalName", userBindVo.getName());
    paramMap.put("bankType", userBindVo.getBankType());
    paramMap.put("bankNo", userBindVo.getBankNo());
    paramMap.put("mobile", userBindVo.getMobile());
    paramMap.put("returnUrl", HfbConst.USERBIND_RETURN_URL);
    paramMap.put("notifyUrl", HfbConst.USERBIND_NOTIFY_URL);
    paramMap.put("timestamp", RequestHelper.getTimestamp());
    paramMap.put("sign", RequestHelper.getSign(paramMap));
    String s = FormHelper.buildForm(HfbConst.USERBIND_URL, paramMap);
    return s;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public void notify(Map<String, Object> stringObjectMap) {
    String bindCode = (String) stringObjectMap.get("bindCode");
    String agentUserId = (String) stringObjectMap.get("agentUserId");

    QueryWrapper<UserBind> queryWrapper = new QueryWrapper<>();
    queryWrapper.eq("user_id", agentUserId);
    UserBind userBind = baseMapper.selectOne(queryWrapper);
    userBind.setBindCode(bindCode);
    userBind.setStatus(UserBindEnum.BIND_OK.getStatus());
    baseMapper.updateById(userBind);

    UserInfo userInfo = userInfoMapper.selectById(agentUserId);
    userInfo.setBindCode(bindCode);
    userInfo.setName(userBind.getName());
    userInfo.setIdCard(userBind.getIdCard());
    userInfo.setBindStatus(UserBindEnum.BIND_OK.getStatus());
    userInfoMapper.updateById(userInfo);
  }
}
