package net.dreamparadise.srb.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.dreamparadise.srb.core.enums.BorrowerStatusEnum;
import net.dreamparadise.srb.core.mapper.BorrowerAttachMapper;
import net.dreamparadise.srb.core.mapper.BorrowerMapper;
import net.dreamparadise.srb.core.mapper.UserInfoMapper;
import net.dreamparadise.srb.core.pojo.entity.Borrower;
import net.dreamparadise.srb.core.pojo.entity.BorrowerAttach;
import net.dreamparadise.srb.core.pojo.entity.UserInfo;
import net.dreamparadise.srb.core.pojo.vo.BorrowerVo;
import net.dreamparadise.srb.core.service.BorrowerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 借款人 服务实现类
 * </p>
 *
 * @author Muma
 * @since 2021-07-21
 */
@Service
public class BorrowerServiceImpl
        extends ServiceImpl<BorrowerMapper, Borrower>
        implements BorrowerService {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private BorrowerAttachMapper borrowerAttachMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBorrowerVoByUserId(BorrowerVo borrowerVo, Long userId) {
        UserInfo userInfo = userInfoMapper.selectById(userId);
        Borrower borrower = new Borrower();
        BeanUtils.copyProperties(borrowerVo, borrower);
        borrower.setUserId(userId);
        borrower.setName(userInfo.getName());
        borrower.setIdCard(userInfo.getIdCard());
        borrower.setMobile(userInfo.getMobile());
        borrower.setStatus(BorrowerStatusEnum.AUTH_RUN.getStatus());
        baseMapper.insert(borrower);

        List<BorrowerAttach> borrowerAttachList = borrowerVo.getBorrowerAttachList();
        borrowerAttachList.forEach(borrowerAttach -> {
            borrowerAttach.setBorrowerId(borrower.getId());
            borrowerAttachMapper.insert(borrowerAttach);
        });
        userInfo.setBorrowAuthStatus(BorrowerStatusEnum.AUTH_RUN.getStatus());
        userInfoMapper.updateById(userInfo);
    }
}
