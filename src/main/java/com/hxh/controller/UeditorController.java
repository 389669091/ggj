package com.hxh.controller;

import com.baidu.ueditor.ActionEnter;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @auth hxh
 * @date 2020/7/24 18:00
 * @Description
 */
@RestController
public class UeditorController {
    @Value("${realPath}")
    private String realPath;
    @Value("${path}")
    private String path;

    @RequestMapping(value = "doUeditor", produces = "text/html;charset=utf-8")
    public String doUeditor(String action, HttpServletRequest request, MultipartFile upfile) {
        String result = null;
        if ("config".equals(action)){
            /*当前端的富文本编辑器对象创建后，会自动发送一个请求后台json配置字符串的ajax请求
             * serverUrl+'?action="config"'
             * 读取config.json文件，转换成json字符串，响应回页面
             * */
            System.out.println("前端请求获取后台配置json对象啦......");

            result = new ActionEnter( request, request.getContextPath() ).exec();
        }else if("uploadimage".equals(action)){
            String originalFilename=upfile.getOriginalFilename();
            String type=originalFilename.substring(originalFilename.lastIndexOf("."));
            String fileName= UUID.randomUUID()+type;
            try {
                upfile.transferTo(new File(realPath,fileName));
                //分析ueditor.all.js 23705 行，可以看出响应json格式包含key规则
                Map<String, Object> resultMap = resultMap("SUCCESS", originalFilename, upfile.getSize(), fileName, type, path + fileName);
                result = new JSONObject(resultMap).toString();
            } catch (IOException e) {
                result = new JSONObject(resultMap("FAIL",null,0,null,null,null)).toString();
                e.printStackTrace();
            }
        }else if("uploadfile".equals(action)){
            System.out.println("前端请求ajax文件上传处理啦......");
        }
        return result;
    }

    //{"state": "SUCCESS","original": "111.jpg","size": "124147","title": "1535961757878095151.jpg","type": ".jpg","url": "/1535961757878095151.jpg"}
    private Map<String,Object> resultMap(String state, String original, long size, String title,String type,  String url){
        Map<String ,Object> result = new HashMap<>();
        result.put("state",state);
        result.put("original",original);
        result.put("size",size);
        result.put("title",title);
        result.put("type",type);
        result.put("url", url);
        return result;
    }

}
