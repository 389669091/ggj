package com.hxh.controller;

import com.hxh.entity.Qualification;
import com.hxh.entity.QualificationCondition;
import com.hxh.entity.Result;
import com.hxh.service.Impl.QualificationServiceImpl;
import com.hxh.service.QualificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/manager/qualification")  //由于tomcat默认会部署一个叫 manager的应用
public class QualificationController {

    @Autowired
    QualificationService service;

    /**
     * 设置返回值类型ModelAndView一定走视图解析器
     * @return
     */
    @RequestMapping("index")
    public ModelAndView toIndex(){
        return new ModelAndView("/qualification/index");//"/WEB-INF/html"+"/index"+".html"
    }

    @RequestMapping("toUpdate")
    public ModelAndView toUpdate(){
        return new ModelAndView("/qualification/update");//"/WEB-INF/html"+"/index"+".html"
    }

//    @RequestMapping("selectAll")
//    public List<Qualification> selectAll(){
//        return service.selectAll();
//    }


    @RequestMapping("selectPage/{pageNum}/{pageSize}")
    public Result selectPage(@PathVariable("pageNum") int pageNum, @PathVariable("pageSize") int pageSize, @RequestBody QualificationCondition condition){
        //obj:PageInfo对象
        return new Result(true,"查询成功",service.selectPage(pageNum,pageSize,condition));
    }

    @RequestMapping(value = "doUpdate",method = RequestMethod.PUT)
    public Result doUpdate(@RequestBody Qualification qualification){
        qualification.setUpdateDate(new Date());
        return new Result(true,"更新成功",service.updateByPrimaryKeySelective(qualification));
    }

    /**
     * 根据用户id获取其用户公司上传文件夹路径
     * @param uid
     * @return
     */
    @RequestMapping("getPath/{uid}")
    public Result getPath(@PathVariable("uid")long uid){
        return new Result(true,"查询成功",service.getPath(uid));
    }

}
