package com.hxh.controller;
import com.hxh.entity.Result;
import com.hxh.entity.SysOffice;
import com.hxh.service.SysOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manager/office")  //由于tomcat默认会部署一个叫 manager的应用
public class SysOfficeController {

    @Autowired
    SysOfficeService service;

   @RequestMapping("select")
    public Result select(){
       SysOffice sysOffice = new SysOffice();
       sysOffice.setDelFlag("0");
       return new Result(true,"查询成功",service.select(sysOffice));
   }




}
