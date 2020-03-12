package com.ixiangliu.modules.sys.controller;

import com.ixiangliu.common.utils.Result;
import com.ixiangliu.modules.sys.shiro.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class LoginController {

    @ResponseBody
    @PostMapping("/sys/login")
    public Result loginPost(String username, String password){
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            Result.error("请输入用户名和密码");
        }
        try{
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            subject.login(token);
        }catch (UnknownAccountException e) {
            return Result.error(e.getMessage());
        }catch (IncorrectCredentialsException e) {
            return Result.error("账号或密码不正确");
        }catch (LockedAccountException e) {
            return Result.error("账号已被锁定,请联系管理员");
        }catch (AuthenticationException e) {
            return Result.error("账户验证失败");
        }
        return Result.ok();
    }

    /**
     * 退出
     */
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout() {
        ShiroUtils.logout();
        return "redirect:login.html";
    }
}
