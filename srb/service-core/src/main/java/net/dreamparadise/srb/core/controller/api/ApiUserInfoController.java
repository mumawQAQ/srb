package net.dreamparadise.srb.core.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import net.dreamparadise.common.exception.Assert;
import net.dreamparadise.common.result.R;
import net.dreamparadise.common.result.ResponseEnum;
import net.dreamparadise.srb.base.util.JwtUtils;
import net.dreamparadise.srb.core.pojo.vo.LoginVo;
import net.dreamparadise.srb.core.pojo.vo.RegisterVo;
import net.dreamparadise.srb.core.pojo.vo.UserInfoVo;
import net.dreamparadise.srb.core.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户基本信息 前端控制器
 * </p>
 *
 * @author Muma
 * @since 2021-07-21
 */
@Api("会员接口")
@RestController
@RequestMapping("api/core/userInfo")
@Slf4j
public class ApiUserInfoController {

  @Autowired
  private RedisTemplate redisTemplate;

  @Autowired
  private UserInfoService userInfoService;

  @ApiOperation("会员注册")
  @PostMapping("/register")
  public R register(@Valid @RequestBody RegisterVo registerVo) {
    String mobile = registerVo.getMobile();
    String code = registerVo.getCode();
    String codeGen = (String) redisTemplate
      .opsForValue()
      .get("srb:sms:code" + mobile);
    Assert.equals(code, codeGen, ResponseEnum.CODE_ERROR);

    userInfoService.register(registerVo);
    return R.ok().message("注册成功");
  }

  @ApiOperation("会员登录")
  @PostMapping("/login")
  public R login(
    @Valid @RequestBody LoginVo loginVo,
    HttpServletRequest httpServletRequest
  ) {
    String ip = httpServletRequest.getRemoteAddr();
    UserInfoVo userInfoVo = userInfoService.login(loginVo, ip);
    return R.ok().data("userinfo", userInfoVo);
  }

  @ApiOperation("校验令牌")
  @GetMapping("/checkToken")
  public R checkToken(HttpServletRequest request) {
    String token = request.getHeader("token");
    boolean b = JwtUtils.checkToken(token);
    if (b) {
      return R.ok();
    } else {
      return R.setResult(ResponseEnum.LOGIN_AUTH_ERROR);
    }
  }

  @ApiOperation("校验手机号是否注册")
  @GetMapping("/checkMobile/{mobile}")
  public R checkMobile(@PathVariable String mobile) {
    boolean result = userInfoService.checkMobile(mobile);
    return R.ok().data("isExist", result);
  }
}
