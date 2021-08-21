package net.dreamparadise.srb.core.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.dreamparadise.common.result.R;
import net.dreamparadise.srb.core.pojo.entity.UserInfo;
import net.dreamparadise.srb.core.pojo.query.UserInfoQuery;
import net.dreamparadise.srb.core.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户基本信息 前端控制器
 * </p>
 *
 * @author Muma
 * @since 2021-07-21
 */
@Api("会员管理")
@RestController
@RequestMapping("/admin/core/userInfo")
@Slf4j
public class AdminUserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @ApiOperation("获取会员分页列表")
    @GetMapping("/list/{page}/{limit}")
    public R listPage(
            @PathVariable Long page,
            @PathVariable Long limit,
            UserInfoQuery userInfoQuery
    ) {
        Page<UserInfo> userInfoPage = new Page<>(page, limit);
        IPage<UserInfo> pageModel = userInfoService.listPage(
                userInfoPage,
                userInfoQuery
        );

        return R.ok().data("pageModel", pageModel);
    }

    @ApiOperation("锁定和解锁")
    @PutMapping("/lock/{id}/{status}")
    public R lock(
            @PathVariable("id") Long id,
            @PathVariable("status") Integer status
    ) {
        userInfoService.lock(id, status);

        return R.ok().message(status == 1 ? "解锁成功" : "锁定成功");
    }
}
