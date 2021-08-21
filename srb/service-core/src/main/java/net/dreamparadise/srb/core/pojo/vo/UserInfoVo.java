package net.dreamparadise.srb.core.pojo.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("用户信息对象")
public class UserInfoVo {

  private String name;
  private String nickName;
  private String headImg;
  private String mobile;
  private Integer userType;
  private String token;
}
