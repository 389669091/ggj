package com.hxh.controller;



import com.hxh.entity.AppVersion;
import com.hxh.entity.Result;
import com.hxh.service.DemandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@RestController
@RequestMapping("/manager/demand")  //由于tomcat默认会部署一个叫 manager的应用
public class DemandController {

    @Autowired
    DemandService service;


    /*@RequestMapping("selectAll")
    public List<AppVersion> selectAll(){
        return service.selectAll();
    }*/

    /*
    * 统一结果响应 ，统一后台接口返回值类型
    * */
    @RequestMapping("selectAll")
    public Result selectAll(){
        return new Result(true,"查询成功",service.selectAll());
    }

    @RequestMapping("toUpdate")
    public ModelAndView toUpdate(){
        return new ModelAndView("/app/update");//"/WEB-INF/html"+"/index"+".html"
    }

    @RequestMapping("index")
    public ModelAndView userList(){
        return new ModelAndView("/demand/index");//"/WEB-INF/html"+"/index"+".html"
    }

//    @RequestMapping("delete")
//    public Result delete(Integer id){
//       return new Result(true,"删除成功",service.deleteByPrimaryKey(id));
//    }

    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    public Result selectPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize){
        //obj:PageInfo对象
        return new Result(true,"查询成功",service.selectPage(pageNum,pageSize));
    }

//    @RequestMapping(value = "doUpdate",method = RequestMethod.PUT)
//    public Result doUpdate(@RequestBody AppVersion app){
//        app.setUpdateDate(new Date());
//        return new Result(true,"更新成功",service.updateByPrimaryKeySelective(app));
//    }
//
//@RequestMapping(value = "Insert",method = RequestMethod.POST)
//    public Result Insert(@RequestBody AppVersion app){
//        return new Result(true,"添加成功",service.insertSelective(app));
//}

}
