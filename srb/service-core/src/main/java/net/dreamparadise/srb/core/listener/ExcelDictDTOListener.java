package net.dreamparadise.srb.core.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import net.dreamparadise.srb.core.mapper.DictMapper;
import net.dreamparadise.srb.core.pojo.dto.ExcelDictDTO;
import org.springframework.stereotype.Component;

@Slf4j
public class ExcelDictDTOListener extends AnalysisEventListener<ExcelDictDTO> {

  private DictMapper dictMapper;

  List<ExcelDictDTO> list = new ArrayList<>();

  static final int BATCH_COUNT = 5;

  @Override
  public void invoke(
    ExcelDictDTO excelDictDTO,
    AnalysisContext analysisContext
  ) {
    list.add(excelDictDTO);
    if (list.size() >= BATCH_COUNT) {
      saveData();
      list.clear();
    }
  }

  private void saveData() {
    dictMapper.insertBatch(list);
    log.info("{}条数据被存储到数据库", list.size());
  }

  @Override
  public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    saveData();
  }

  public ExcelDictDTOListener() {}

  public ExcelDictDTOListener(DictMapper dictMapper) {
    this.dictMapper = dictMapper;
  }
}
