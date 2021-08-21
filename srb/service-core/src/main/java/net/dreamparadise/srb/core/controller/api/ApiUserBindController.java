package net.dreamparadise.srb.core.controller.api;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import net.dreamparadise.common.result.R;
import net.dreamparadise.srb.base.util.JwtUtils;
import net.dreamparadise.srb.core.hfb.RequestHelper;
import net.dreamparadise.srb.core.pojo.vo.UserBindVo;
import net.dreamparadise.srb.core.service.UserBindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户绑定表 前端控制器
 * </p>
 *
 * @author Muma
 * @since 2021-07-21
 */
@Api("会员账号绑定")
@RestController
@RequestMapping("/api/core/userBind")
@Slf4j
public class ApiUserBindController {

  @Autowired
  private UserBindService userBindService;

  @ApiOperation("账户绑定提交数据")
  @PostMapping("/auth/bind")
  public R bind(
    @RequestBody UserBindVo userBindVo,
    HttpServletRequest request
  ) {
    String token = request.getHeader("token");
    Long userId = JwtUtils.getUserId(token);

    String formStr = userBindService.commitBindUser(userBindVo, userId);
    return R.ok().data("formStr", formStr);
  }

  @ApiOperation("账户绑定异步回调")
  @PostMapping("/notify")
  public String notify(HttpServletRequest request) {
    Map<String, Object> stringObjectMap = RequestHelper.switchMap(
      request.getParameterMap()
    );
    log.info("账户绑定参数" + JSON.toJSONString(stringObjectMap));
    if (!RequestHelper.isSignEquals(stringObjectMap)) {
      log.error(
        "用户账户绑定异步回调签名错误" + JSON.toJSONString(stringObjectMap)
      );
      return "fail";
    }
    userBindService.notify(stringObjectMap);
    return "success";
  }
}
