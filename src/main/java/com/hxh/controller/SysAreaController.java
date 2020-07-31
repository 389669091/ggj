package com.hxh.controller;

import com.hxh.entity.Result;
import com.hxh.entity.SysArea;
import com.hxh.service.SysAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/manager/area")  //由于tomcat默认会部署一个叫 manager的应用
public class SysAreaController {

    @Autowired
    SysAreaService service;

    /**
     * 设置返回值类型ModelAndView一定走视图解析器
     *
     * @return
     */
    @RequestMapping()
    public ModelAndView toIndex() {
        return new ModelAndView("/area/index");//"/WEB-INF/html"+"/index"+".html"
    }

    @RequestMapping("toUpdate")
    public ModelAndView toUpdate() {
        return new ModelAndView("/area/update");//"/WEB-INF/html"+"/index"+".html"
    }

    @RequestMapping("toModule")
    public ModelAndView toModule() {
        return new ModelAndView("/modules/font-awesome");//"/WEB-INF/html"+"/index"+".html"
    }

    @RequestMapping("toSelect")
    public ModelAndView toSelect() {
        return new ModelAndView("/area/select");//"/WEB-INF/html"+"/index"+".html"
    }

    @RequestMapping("select")
    public Result select() {
        SysArea sysArea = new SysArea();
        sysArea.setDelFlag("0");
        return new Result(true, "查询成功", service.select(sysArea));
    }


    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    public Result selectPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize, String name) {
        //obj:PageInfo对象
        return new Result(true, "查询成功", service.select(pageNum, pageSize, name));
    }

    @RequestMapping("selectByPid/{pageNum}/{pageSize}")
    public Result selectByPid(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize, long id) {
        //obj:PageInfo对象
        return new Result(true, "查询成功", service.selectByPid(pageNum, pageSize, id));
    }

    @RequestMapping(value = "doUpdate", method = RequestMethod.PUT)
    public Result doUpdate(@RequestBody SysArea sysArea) {
        sysArea.setUpdateDate(new Date());
        return new Result(true, "更新成功", service.updateByPrimaryKeySelective(sysArea));
    }

    @RequestMapping("download")
    public void download(HttpServletResponse response) throws IOException {
        //1.设置响应头信息  指定
        //new String("区域.xls".getBytes(),"iso-8859-1")  解决客户端显示中文文件名乱码问题
        response.setHeader("Content-Disposition","attachment;filename="+new String("区域.xls".getBytes(),"iso-8859-1"));
        service.download(response.getOutputStream());
    }

    //必须实现文件上传解析器配置
    @RequestMapping("upload")
    public Result upload(MultipartFile file) throws IOException {
        service.upload(file.getInputStream());
        return new Result(true,"批量导入数据成功",file.getOriginalFilename());
    }

    @RequestMapping("doDelete")
    public Result doDelete(@RequestBody SysArea sysArea){
//        sysArea.setUpdateDate(new Date());
//        service.updateByPrimaryKeySelective(sysArea);
        return new Result(true,"修改成功",service.doUpdate(sysArea));
    }
}
