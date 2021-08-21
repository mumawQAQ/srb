package net.dreamparadise.srb.core.pojo.query;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
public class UserInfoQuery {

  private String mobile;
  private Integer status;
  private Integer userType;
}
