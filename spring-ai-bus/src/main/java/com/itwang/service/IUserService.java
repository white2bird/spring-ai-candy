package com.itwang.service;

import com.itwang.dao.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itwang.request.UserLoginRequest;
import com.itwang.request.UserRegisterRequest;
import com.itwang.response.client.ClientLoginRes;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lmz
 * @since 2024-09-08
 */
public interface IUserService extends IService<User> {


    ClientLoginRes userLogin(UserLoginRequest userLoginRequest);


    Boolean createUser(UserRegisterRequest userRegisterRequest);

    void logout();

}
