package net.dreamparadise.srb.core.controller.admin;

import com.alibaba.excel.EasyExcel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import net.dreamparadise.common.exception.BusinessException;
import net.dreamparadise.common.result.R;
import net.dreamparadise.common.result.ResponseEnum;
import net.dreamparadise.srb.core.pojo.dto.ExcelDictDTO;
import net.dreamparadise.srb.core.pojo.entity.Dict;
import net.dreamparadise.srb.core.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 数据字典 前端控制器
 * </p>
 *
 * @author Muma
 * @since 2021-07-21
 */
@Api("数据字典管理")
@RestController
@RequestMapping("/admin/core/dict")
public class AdminDictController {

  @Autowired
  private DictService dictService;

  @ApiOperation("Excel数据的批量导入")
  @PostMapping("/import")
  public R batchImport(@RequestParam("file") MultipartFile file) {
    try {
      InputStream inputStream = file.getInputStream();
      dictService.importData(inputStream);
      return R.ok().message("数据批量导入成功");
    } catch (IOException e) {
      throw new BusinessException(ResponseEnum.UPLOAD_ERROR, e);
    }
  }

  @ApiOperation("Excel数据的导出")
  @GetMapping("/export")
  public void export(HttpServletResponse response) throws IOException {
    response.setContentType("application/vnd.ms-excel");
    response.setCharacterEncoding("utf-8");
    String fileName = URLEncoder
      .encode("mydict", "UTF-8")
      .replaceAll("\\+", "%20");
    response.setHeader(
      "Content-disposition",
      "attachment;filename*=utf-8''" + fileName + ".xlsx"
    );
    EasyExcel
      .write(response.getOutputStream(), ExcelDictDTO.class)
      .sheet("数据字典")
      .doWrite(dictService.listDictData());
  }

  @ApiOperation("根据上级id获取子节点数据列表")
  @GetMapping("/listByParentId/{parentId}")
  public R listByParentId(@PathVariable Long parentId) {
    List<Dict> dictList = dictService.listByParentId(parentId);
    return R.ok().data("list", dictList);
  }
}
