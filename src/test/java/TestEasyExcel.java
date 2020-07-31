
import com.alibaba.excel.EasyExcel;
import com.hxh.config.SpringMybatisConfig;
import com.hxh.entity.SysArea;
//import com.hxh.listener.SysAreaListener;
import com.hxh.listener.SysAreaListener;
import com.hxh.mapper.SysAreaMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringMybatisConfig.class)//加载dao层配置类
public class TestEasyExcel {

    @Autowired
    SysAreaMapper mapper;

    /*excel写操作
    * 1.获取java集合数据
    * 2.写入excel
    *   a.获取ExcelWriter对象
    *   b.获取sheet对象
    *   c.写出集合到excel中
    * @ExcelProperty("区域名称")指定生产的表头的字段名
    * @ExcelIgnore  忽略生成到表头
    * @DateTimeFormat("yyyy-MM-dd")  :格式化日期
    * @NumberFormat("0.00")   :  设置显示数字格式
    * */
    @Test
    public void write(){
//        List<SysArea> sysAreas = mapper.selectAll();
//        /*
//         * 自动根据指定类型的属性名生成表头字段名，通过注解自定义生成的excel字段名
//         * 自动关闭流
//         * */
//        EasyExcel.write("D:\\area1.xls",SysArea.class)
//                .sheet("区域")
//                .doWrite(sysAreas);
        List<SysArea>list=mapper.selectAll();
        EasyExcel.write("D:\\2.xls",SysArea.class).sheet("区域").doWrite(list);

    }

    /*
    * 读操作：
    * 1.获取excel读对象，指定读取的excel文件和表头对应类型,读监听器
    * 2.获取sheet处理对象
    * 3.读取excel，调用监听器指定逻辑处理java对象
    * */
    @Test
    public void read(){
        EasyExcel.read(
                "D:\\area.xls",
                SysArea.class,new SysAreaListener(mapper)).sheet().doRead();
    }



}
