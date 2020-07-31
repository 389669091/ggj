package com.hxh.controller;

import com.hxh.entity.AppVersion;
import com.hxh.entity.Result;
import com.hxh.entity.Statute;
import com.hxh.service.StatuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@RestController
@RequestMapping("/manager/statute")  //由于tomcat默认会部署一个叫 manager的应用
public class StatuteController {

    @Autowired
    StatuteService service;

    /**
     * 设置返回值类型ModelAndView一定走视图解析器
     * @return
     */
    @RequestMapping("index")
    public ModelAndView toIndex(){
        return new ModelAndView("/statute/index");//"/WEB-INF/html"+"/index"+".html"
    }

    @RequestMapping("toUpdate")
    public ModelAndView toUpdate(){
        return new ModelAndView("/statute/update");//"/WEB-INF/html"+"/index"+".html"
    }


    /*@RequestMapping("selectAll")
    public List<Statute> selectAll(){
        return service.selectAll();
    }*/


   @RequestMapping("selectPage/{pageNum}/{pageSize}")
    public Result selectPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize, Integer type){
        //obj:PageInfo对象
       Statute statute = new Statute();
       statute.setDelFlag("0");//查询未删除
       statute.setType(type);
       return new Result(true,"查询成功",service.select(pageNum,pageSize,statute));
    }

     @RequestMapping(value = "doUpdate",method = RequestMethod.PUT)
    public Result doUpdate(@RequestBody Statute statute){
        statute.setUpdateDate(new Date());
        return new Result(true,"更新成功",service.updateByPrimaryKeySelective(statute));
    }

    @RequestMapping(value = "Insert",method = RequestMethod.POST)
    public Result Insert(@RequestBody Statute statute){
        return new Result(true,"添加成功",service.insertSelective(statute));
    }

}
