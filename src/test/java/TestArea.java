
import com.hxh.config.SpringMybatisConfig;
import com.hxh.entity.SysArea;
import com.hxh.mapper.SysAreaMapper;
import com.hxh.service.SysAreaService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringMybatisConfig.class)//加载dao层配置类
public class TestArea {


    @Autowired
    SysAreaMapper mapper;

    @Autowired
    SysAreaService service;


   @Test
    public void test1(){
       List<SysArea> sysAreas = mapper.selectByName("区");
       System.out.println(sysAreas);
   }

//   @Test
//    public void testInsetBatch(){
//       List<SysArea> sysAreas = mapper.selectAll();
//       int i = mapper.insertBatch(sysAreas);
//   }

}
