package com.itwang.dao.service.impl;

import com.itwang.dao.entity.User;
import com.itwang.dao.mapper.UserMapper;
import com.itwang.dao.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

}
