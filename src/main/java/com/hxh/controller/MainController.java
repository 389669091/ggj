package com.hxh.controller;

import com.hxh.entity.Result;
import com.hxh.entity.SysUser;
import com.hxh.mapper.SysUserMapper;
import com.hxh.service.SysUserService;
import com.hxh.utils.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * @auth hxh
 * @date 2020/7/17 11:51
 * @Description
 */
@Controller
@RequestMapping("main")
public class MainController {
    @Autowired
    SysUserService userService;
    @RequestMapping("navbar")
    public String navbar(){
        return "/common/navbar";
    }

    @RequestMapping("sidebar")
    public String sidebar(){
        return "/common/sidebar";
    }

    @RequestMapping("toIndex")
    public ModelAndView toIndex(){
        return new ModelAndView("/index");
    }

    @RequestMapping("login")
    @ResponseBody
    public Result login(String username, String password, String code, @SessionAttribute("checkCode") String checkCode, HttpSession session){
        if (checkCode.equals(code)){
        SysUser sysUser=new SysUser();
        sysUser.setUsername(username);
        sysUser.setPassword(EncryptUtils.MD5_HEX(EncryptUtils.MD5_HEX(password)+username));
        SysUser loginUser=userService.selectOne(sysUser);

        if (loginUser!=null){
            session.setAttribute("loginUser",loginUser);
            loginUser.setPassword(null);
            return new Result(true,"登录成功",loginUser);
        }
    }
        return new Result(false,"登录失败",null);
    }

    @RequestMapping("logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/login.html";
    }
}
