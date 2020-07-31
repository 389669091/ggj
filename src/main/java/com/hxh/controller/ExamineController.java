package com.hxh.controller;


import com.hxh.entity.Result;
import com.hxh.service.ExamineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RestController
@RequestMapping("/manager/examine")  //由于tomcat默认会部署一个叫 manager的应用
public class ExamineController {

    @Autowired
    ExamineService service;

    /**
     * 设置返回值类型ModelAndView一定走视图解析器
     * @return
     */
    @RequestMapping("index")
    public ModelAndView toIndex(){
        return new ModelAndView("/examine/index");//"/WEB-INF/html"+"/index"+".html"
    }


    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    public Result selectPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize, @RequestBody Map<String,Object> params){
        //obj:PageInfo对象
        return new Result(true,"查询成功",service.selectPage(pageNum,pageSize,params));
    }

//    @RequestMapping("selectAll/{pageNum}/{pageSize}")
//    public Result selectAll(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize){
//        //obj:PageInfo对象
//        return new Result(true,"查询成功",service.selectPage(pageNum,pageSize));
//    }

}
