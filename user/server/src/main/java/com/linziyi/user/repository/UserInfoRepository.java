package com.linziyi.user.repository;

import com.linziyi.user.dataobject.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserInfoRepository extends JpaRepository<UserInfo,String> {
    UserInfo findByOpenid(String openid);
}
