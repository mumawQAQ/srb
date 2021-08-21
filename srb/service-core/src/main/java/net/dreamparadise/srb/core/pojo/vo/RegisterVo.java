package net.dreamparadise.srb.core.pojo.vo;

import io.swagger.annotations.ApiModel;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Data;

@Data
@ApiModel("注册对象")
public class RegisterVo {

  @NotNull(message = "userType can' be empty")
  private Integer userType;

  @Pattern(regexp = "^\\+\\d{10,}$")
  private String mobile;

  @Pattern(regexp = "\\d{4}")
  private String code;

  @Pattern(regexp = ".{6,24}")
  private String password;
}
