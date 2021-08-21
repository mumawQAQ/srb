package net.dreamparadise.srb.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;
import net.dreamparadise.srb.core.pojo.dto.ExcelDictDTO;
import net.dreamparadise.srb.core.pojo.entity.Dict;

/**
 * <p>
 * 数据字典 Mapper 接口
 * </p>
 *
 * @author Muma
 * @since 2021-07-21
 */
public interface DictMapper extends BaseMapper<Dict> {
  void insertBatch(List<ExcelDictDTO> list);
}
