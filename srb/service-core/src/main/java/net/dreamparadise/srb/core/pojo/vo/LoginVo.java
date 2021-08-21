package net.dreamparadise.srb.core.pojo.vo;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
@ApiModel("登录对象")
public class LoginVo {

  private Integer userType;

  @NotEmpty(message = "手机号不能为空")
  @NotNull(message = "手机号不能为空")
  private String mobile;

  @NotEmpty(message = "密码不能为空")
  @NotNull(message = "密码不能为空")
  private String password;
}
