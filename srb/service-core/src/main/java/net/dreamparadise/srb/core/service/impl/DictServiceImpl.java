package net.dreamparadise.srb.core.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import net.dreamparadise.srb.core.listener.ExcelDictDTOListener;
import net.dreamparadise.srb.core.mapper.DictMapper;
import net.dreamparadise.srb.core.pojo.dto.ExcelDictDTO;
import net.dreamparadise.srb.core.pojo.entity.Dict;
import net.dreamparadise.srb.core.service.DictService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 数据字典 服务实现类
 * </p>
 *
 * @author Muma
 * @since 2021-07-21
 */
@Service
public class DictServiceImpl
  extends ServiceImpl<DictMapper, Dict>
  implements DictService {

  @Autowired
  private RedisTemplate redisTemplate;

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void importData(InputStream inputStream) {
    EasyExcel
      .read(
        inputStream,
        ExcelDictDTO.class,
        new ExcelDictDTOListener(baseMapper)
      )
      .sheet()
      .doRead();
  }

  @Override
  public List<ExcelDictDTO> listDictData() {
    List<Dict> dicts = baseMapper.selectList(null);
    ArrayList<ExcelDictDTO> excelDictDTOS = new ArrayList<>(dicts.size());
    dicts.forEach(
      dict -> {
        ExcelDictDTO excelDictDTO = new ExcelDictDTO();
        BeanUtils.copyProperties(dict, excelDictDTO);
        excelDictDTOS.add(excelDictDTO);
      }
    );
    return excelDictDTOS;
  }

  @Override
  public List<Dict> listByParentId(Long parentId) {
    try {
      List<Dict> dictList = (List<Dict>) redisTemplate
        .opsForValue()
        .get("srb:core:dictList" + parentId);
      if (dictList != null) {
        return dictList;
      }
    } catch (Exception e) {
      log.error("redis服务器异常" + ExceptionUtils.getStackTrace(e));
    }

    QueryWrapper<Dict> objectQueryWrapper = new QueryWrapper<>();
    objectQueryWrapper.eq("parent_id", parentId);
    List<Dict> dicts = baseMapper.selectList(objectQueryWrapper);
    dicts.forEach(
      dict -> {
        Boolean aBoolean = this.hasChildren(dict.getId());
        dict.setHasChildren(aBoolean);
      }
    );

    try {
      redisTemplate
        .opsForValue()
        .set("srb:core:dictList" + parentId, dicts, 5, TimeUnit.MINUTES);
    } catch (Exception e) {
      log.error("redis服务器异常" + ExceptionUtils.getStackTrace(e));
    }
    return dicts;
  }

  @Override
  public List<Dict> findByDictCode(String dictCode) {
    QueryWrapper<Dict> dictQueryWrapper = new QueryWrapper<>();
    dictQueryWrapper.eq("dict_code", dictCode);
    Dict dict = baseMapper.selectOne(dictQueryWrapper);
    return this.listByParentId(dict.getId());
  }

  private Boolean hasChildren(Long id) {
    QueryWrapper<Dict> objectQueryWrapper = new QueryWrapper<>();
    objectQueryWrapper.eq("parent_id", id);
    Integer integer = baseMapper.selectCount(objectQueryWrapper);
    return integer > 0;
  }
}
