package net.dreamparadise.srb.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import java.io.InputStream;
import java.util.List;
import net.dreamparadise.srb.core.pojo.dto.ExcelDictDTO;
import net.dreamparadise.srb.core.pojo.entity.Dict;

/**
 * <p>
 * 数据字典 服务类
 * </p>
 *
 * @author Muma
 * @since 2021-07-21
 */
public interface DictService extends IService<Dict> {
  void importData(InputStream inputStream);

  List<ExcelDictDTO> listDictData();

  List<Dict> listByParentId(Long parentId);

  List<Dict> findByDictCode(String dictCode);
}
