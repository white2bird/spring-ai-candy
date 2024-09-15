package com.itwang.converter;


import com.itwang.dao.entity.User;
import com.itwang.request.UserRegisterRequest;
import com.itwang.response.client.ClientUserInfoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginUserConverter {

    User converterToUser(UserRegisterRequest userRegisterRequest);

    ClientUserInfoResponse converterToClientUserInfoResponse(User user);
}
