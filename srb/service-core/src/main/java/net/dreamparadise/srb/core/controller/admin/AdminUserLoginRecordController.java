package net.dreamparadise.srb.core.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import net.dreamparadise.common.result.R;
import net.dreamparadise.srb.core.pojo.entity.UserLoginRecord;
import net.dreamparadise.srb.core.service.UserLoginRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户登录记录表 前端控制器
 * </p>
 *
 * @author Muma
 * @since 2021-07-21
 */
@Api(tags = "会员登录日志接口")
@RestController
@RequestMapping("/admin/core/userLoginRecord")
@Slf4j
public class AdminUserLoginRecordController {

  @Autowired
  private UserLoginRecordService userLoginRecordService;

  @ApiOperation("获取会员登录日志列表")
  @GetMapping("/listTop50/{userId}")
  public R listTop50(@PathVariable Long userId) {
    List<UserLoginRecord> userLoginRecordList = userLoginRecordService.listTop50(
      userId
    );
    return R.ok().data("list", userLoginRecordList);
  }
}
