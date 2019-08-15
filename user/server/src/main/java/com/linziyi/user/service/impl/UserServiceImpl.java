package com.linziyi.user.service.impl;

import com.linziyi.user.dataobject.UserInfo;
import com.linziyi.user.repository.UserInfoRepository;
import com.linziyi.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserInfoRepository userInfoRepository;

    /**
     * 通过openid来查询用户信息
     * @param openid
     * @return
     */
    @Override
    public UserInfo findByOpenid(String openid) {
        return this.userInfoRepository.findByOpenid(openid);
    }
}
