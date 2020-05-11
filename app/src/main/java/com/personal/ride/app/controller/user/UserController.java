package com.personal.ride.app.controller.user;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import java.util.function.Function;
import com.personal.ride.service.user.service.IUserService;
import com.personal.ride.service.user.entity.User;
import com.personal.ride.service.user.request.UserRequest;
import com.personal.ride.service.user.vo.UserVO;
import org.springframework.web.bind.annotation.RestController;
import com.personal.core.common.utils.CommonUtils;
import com.personal.ride.app.response.R;
import com.personal.ride.app.base.BaseController;


/**
 *
 * 用户信息表 控制器
 *
 * @author sunpeikai
 * @since 2020-05-11
 */
@RestController
@Api(value = "用户信息表", tags = "用户信息表接口")
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    /**
     * 查询用户信息表列表
     */
    @PostMapping("/list")
    @ApiOperation(value = "列表", notes = "列表")
    public R<IPage<UserVO>> list(@RequestBody UserRequest userRequest) {
        User user = CommonUtils.convertBean(userRequest,User.class);
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>().setEntity(user);
        IPage<User> iPage = userService.page(new Page<>(userRequest.getCurrPage(),userRequest.getPageSize()),queryWrapper);
        IPage<UserVO> result = iPage.convert(new Function<User, UserVO>() {
            @Override
            public UserVO apply(User user) {
                return CommonUtils.convertBean(user,UserVO.class);
            }
        });
        return R.success(result);
    }

    /**
     * 查询用户信息表详情
     */
    @PostMapping("/info")
    @ApiOperation(value = "详情", notes = "详情")
    public R<UserVO> info(@RequestBody UserRequest userRequest) {
        if (userRequest.getId() == null) {
            return R.fail("缺少主键id!");
        }
        User user = CommonUtils.convertBean(userRequest,User.class);
        User data =  userService.getOne(new QueryWrapper<User>().setEntity(user));
        return R.success(CommonUtils.convertBean(data,UserVO.class));
    }


    /**
     * 新增用户信息表
     */
    @PostMapping("/add")
    @ApiOperation(value = "新增", notes = "新增")
    public R save(@RequestBody UserRequest userRequest) {
        User user = CommonUtils.convertBean(userRequest,User.class);
        userService.save(user);
        return R.success();
    }


    /**
     * 更新用户信息表数据
     */
    @PostMapping("/update")
    @ApiOperation(value = "更新", notes = "更新")
    public R update(@RequestBody UserRequest userRequest) {
        if (userRequest.getId() == null) {
            return R.fail("缺少主键id!");
        }
        User user = CommonUtils.convertBean(userRequest,User.class);
        userService.updateById(user);
        return R.success();
    }

    /**
     * 删除用户信息表数据
     */
    @PostMapping("/delete")
    @ApiOperation(value = "删除", notes = "删除")
    public R delete(@RequestBody UserRequest userRequest) {
        if (userRequest.getId() == null) {
            return R.fail("缺少主键id!");
        }
        User user = CommonUtils.convertBean(userRequest,User.class);
        userService.remove(new QueryWrapper<User>().setEntity(user));
        return R.success();
    }


}
