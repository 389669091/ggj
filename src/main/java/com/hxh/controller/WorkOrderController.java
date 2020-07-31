package com.hxh.controller;

import com.hxh.entity.Result;
import com.hxh.service.WorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RestController
@RequestMapping("/manager")  //由于tomcat默认会部署一个叫 manager的应用
public class WorkOrderController {

    @Autowired
    WorkOrderService service;

    /**
     * 设置返回值类型ModelAndView一定走视图解析器
     * @return
     */
    @RequestMapping("/admin/work")
    public ModelAndView toIndex(){
        return new ModelAndView("/work/admin/index");//"/WEB-INF/html"+"/index"+".html"
    }

    @RequestMapping("/admin/detail")
    public ModelAndView detail(){
        return new ModelAndView("/work/work-detail");//"/WEB-INF/html"+"/index"+".html"
    }


    @RequestMapping("/admin/work/selectPage/{pageNum}/{pageSize}")
    public Result selectPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize,@RequestBody Map<String,Object> params){
        //obj:PageInfo对象
        return new Result(true,"查询成功",service.selectPage(pageNum,pageSize,params));
    }


    @RequestMapping("/admin/selectDetail")
    public Result selectDetail(long id){
        return new Result(true,"查询成功",service.selectDetail(id));
    }



}
