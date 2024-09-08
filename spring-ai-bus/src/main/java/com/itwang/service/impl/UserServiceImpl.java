package com.itwang.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itwang.common.constants.AiConstants;
import com.itwang.converter.LoginUserConverter;
import com.itwang.dao.entity.User;
import com.itwang.dao.mapper.UserMapper;
import com.itwang.request.UserLoginRequest;
import com.itwang.request.UserRegisterRequest;
import com.itwang.response.client.ClientLoginRes;
import com.itwang.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lmz
 * @since 2024-09-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private LoginUserConverter loginUserConverter;

    @Override
    public ClientLoginRes userLogin(UserLoginRequest userLoginRequest) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getNickname, userLoginRequest.getNickname()));
        String dataBasePassword = user.getPassword();
        boolean matches = passwordEncoder.matches(userLoginRequest.getPassword(), dataBasePassword);
        if(!matches){
            throw new RuntimeException("用户账号或者密码错误");
        }
        StpUtil.login(user.getId());
        String tokenValue = StpUtil.getTokenValue();
        return new ClientLoginRes(user.getId(), user.getNickname(), tokenValue, user.getAvatar());
    }


    @Override
    public Boolean createUser(UserRegisterRequest userRegisterRequest) {
        // 设置用户为普通用户
        userRegisterRequest.setType(AiConstants.UserType.NORMAL.getCode());
        User user = loginUserConverter.converterToUser(userRegisterRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return this.save(user);
    }

    @Override
    public void logout() {
        StpUtil.logout();
    }
}
