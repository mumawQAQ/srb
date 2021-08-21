package net.dreamparadise.srb.core.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import net.dreamparadise.common.result.R;
import net.dreamparadise.srb.core.pojo.entity.Dict;
import net.dreamparadise.srb.core.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("数据字典")
@RestController
@RequestMapping("/api/core/dict")
public class ApiDictController {

  @Autowired
  private DictService dictService;

  @ApiOperation("根据dictCode获取下级节点")
  @GetMapping("/findByDictCode/{dictCode}")
  public R findByDictCode(@PathVariable String dictCode) {
    List<Dict> list = dictService.findByDictCode(dictCode);

    return R.ok().data("dictList", list);
  }
}
