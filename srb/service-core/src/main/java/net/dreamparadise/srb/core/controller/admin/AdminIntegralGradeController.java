package net.dreamparadise.srb.core.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import net.dreamparadise.common.exception.Assert;
import net.dreamparadise.common.result.R;
import net.dreamparadise.common.result.ResponseEnum;
import net.dreamparadise.srb.core.pojo.entity.IntegralGrade;
import net.dreamparadise.srb.core.service.IntegralGradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 积分等级表 前端控制器
 * </p>
 *
 * @author Muma
 * @since 2021-07-21
 */

@Api("积分等级管理")
@RestController
@RequestMapping("/admin/core/integralGrade")
@Slf4j
public class AdminIntegralGradeController {

  @Autowired
  private IntegralGradeService integralGradeService;

  @ApiOperation("积分等级列表")
  @GetMapping("/list")
  public R listAll() {
    List<IntegralGrade> list = integralGradeService.list();
    return R.ok().data("list", list).message("获取列表成功");
  }

  @ApiOperation(value = "根据id删除数据记录", notes = "逻辑删除数据记录")
  @DeleteMapping("/remove/{id}")
  public R removeById(
    @ApiParam(value = "数据id", example = "100") @PathVariable Long id
  ) {
    boolean b = integralGradeService.removeById(id);
    if (b) {
      return R.ok().message("删除成功");
    } else {
      return R.error().message("删除失败");
    }
  }

  @ApiOperation("新增积分等级")
  @PostMapping("/save")
  public R save(@RequestBody IntegralGrade integralGrade) {
    Assert.notNull(
      integralGrade.getBorrowAmount(),
      ResponseEnum.BORROW_AMOUNT_NULL_ERROR
    );
    boolean save = integralGradeService.save(integralGrade);
    if (save) {
      return R.ok().message("保存成功");
    } else {
      return R.error().message("保存失败");
    }
  }

  @ApiOperation("根据id获取积分等级")
  @GetMapping("/get/{id}")
  public R getByID(@PathVariable Long id) {
    IntegralGrade byId = integralGradeService.getById(id);
    if (byId != null) {
      return R.ok().data("record", byId);
    } else {
      return R.error().message("数据获取成功");
    }
  }

  @ApiOperation("更新积分等级")
  @PutMapping("/update")
  public R updateById(@RequestBody IntegralGrade integralGrade) {
    boolean save = integralGradeService.updateById(integralGrade);
    if (save) {
      return R.ok().message("更新成功");
    } else {
      return R.error().message("更新失败");
    }
  }
}
