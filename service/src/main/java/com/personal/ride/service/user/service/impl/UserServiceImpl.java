package com.personal.ride.service.user.service.impl;

import com.personal.ride.service.user.entity.User;
import com.personal.ride.service.user.mapper.UserMapper;
import com.personal.ride.service.user.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author sunpeikai
 * @since 2020-05-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
