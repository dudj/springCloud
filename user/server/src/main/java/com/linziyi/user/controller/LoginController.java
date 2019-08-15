package com.linziyi.user.controller;

import com.linziyi.user.constant.CookieConstant;
import com.linziyi.user.constant.RedisConstant;
import com.linziyi.user.dataobject.UserInfo;
import com.linziyi.user.enums.ResultEnum;
import com.linziyi.user.enums.RoleEnum;
import com.linziyi.user.service.UserService;
import com.linziyi.user.util.CookieUtil;
import com.linziyi.user.util.ResultVOUtil;
import com.linziyi.user.vo.ResultVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    /**
     * 买家登录
     * @param openid
     * @param response
     * @return
     */
    @GetMapping("/buyer")
    public ResultVO buyer(@RequestParam("openid") String openid, HttpServletResponse response){
        //1.openid和数据库的数据进行对比
        UserInfo userInfo = userService.findByOpenid(openid);
        if(null == userInfo){
            return ResultVOUtil.error(ResultEnum.LOGIN_FAIL);
        }
        //2.判断角色
        if(userInfo.getRole() != RoleEnum.BUYER.getCode()){
            return ResultVOUtil.error(ResultEnum.ROLE_ERROR);
        }
        //3.cookie里设置openid=abc
        CookieUtil.set(response,CookieConstant.OPENID, openid,CookieConstant.expire);
        return ResultVOUtil.success();
    }
    @GetMapping("/seller")
    public ResultVO seller(@RequestParam("openid") String openid, HttpServletRequest request, HttpServletResponse response){
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if(cookie != null &&
                !StringUtils.isEmpty(stringRedisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_TEMPLATE,cookie.getValue())))){
            return ResultVOUtil.success();
        }
        //1.openid和数据库的数据进行对比
        UserInfo userInfo = userService.findByOpenid(openid);
        if(null == userInfo){
            return ResultVOUtil.error(ResultEnum.LOGIN_FAIL);
        }
        //2.判断角色
        if(userInfo.getRole() != RoleEnum.SELLER.getCode()){
            return ResultVOUtil.error(ResultEnum.ROLE_ERROR);
        }
        //3.redis设置key=UUID，value=xyz
        String token = UUID.randomUUID().toString();
        stringRedisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_TEMPLATE,token),
                openid,
                RedisConstant.expire,
                TimeUnit.SECONDS
        );
        //4.cookie里设置openid=xyz
        CookieUtil.set(response,CookieConstant.TOKEN, token,CookieConstant.expire);
        return ResultVOUtil.success();
    }
}
