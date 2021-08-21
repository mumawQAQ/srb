package net.dreamparadise.srb.core.controller.api;

import io.swagger.annotations.Api;
import javax.servlet.http.HttpServletRequest;
import net.dreamparadise.common.result.R;
import net.dreamparadise.srb.base.util.JwtUtils;
import net.dreamparadise.srb.core.pojo.vo.BorrowerVo;
import net.dreamparadise.srb.core.service.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("借款人")
@RestController
@RequestMapping("/api/core/borrower")
public class ApiBorrowerController {

  @Autowired
  private BorrowerService borrowerService;

  @PostMapping("/auth/save")
  public R save(
    @RequestBody BorrowerVo borrowerVo,
    HttpServletRequest request
  ) {
    String token = request.getHeader("token");
    Long userId = JwtUtils.getUserId(token);
    borrowerService.saveBorrowerVoByUserId(borrowerVo, userId);
    return R.ok().message("信息提交成功");
  }
}
