
import com.alibaba.druid.pool.DruidDataSource;
import com.hxh.config.SpringMybatisConfig;
import com.hxh.entity.AppVersion;
import com.hxh.entity.Examine;
import com.hxh.entity.Qualification;
import com.hxh.entity.QualificationCondition;
import com.hxh.mapper.AppVersionMapper;
import com.hxh.mapper.ExamineMapper;
import com.hxh.mapper.QualificationMapper;
import com.hxh.service.AppVersionService;
import com.hxh.service.ExamineService;
import com.hxh.service.QualificationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringMybatisConfig.class)//加载dao层配置类
public class TestSSM {

    @Autowired
    DruidDataSource dataSource;
    @Autowired
    AppVersionMapper mapper;

    @Autowired
    AppVersionService service;
    @Autowired
    QualificationMapper qualificationMapper;
    @Autowired
    QualificationService qualificationService;
    @Autowired
    ExamineMapper examineMapper;
    @Autowired
    ExamineService examineService;

    @Test
    public void test() throws Exception {
        System.out.println(dataSource.getConnection());
    }

    @Test
    public void testService() {
        for (AppVersion appVersion : service.selectAll()) {
            System.out.println(appVersion);
        }
    }

    @Test
    public void testService1() {
       mapper.deleteByPrimaryKey(1);
        }

    @Test
    public void testService2() {
        AppVersion appVersion= mapper.selectByPrimaryKey(2);
        appVersion.setId(null);
       service.insertSelective(appVersion);
    }

    @Test
    public void testService3() {
        List<Qualification>qualification1 = qualificationMapper.selectAll();
        for(Qualification q:qualification1){
            System.out.println(q);
        }
    }

    @Test
    public void  page(){
        QualificationCondition qualificationCondition = new QualificationCondition();
        qualificationCondition.setType("3");
        qualificationCondition.setCheck("2");
        qualificationCondition.setStartDate("2019-01-01");
        qualificationCondition.setEndDate("2019-12-31");
        List<Qualification> qualifications = qualificationMapper.selectPage(qualificationCondition );
        for (Qualification qualification : qualifications) {
            System.out.println(qualification);
        }
    }

    @Test
    public void testService4() {
        List<Examine>qualification1 = examineMapper.selectAll();
        for(Examine q:qualification1){
            System.out.println(q);
        }
    }
    @Test
    public void  page1(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("type","1");
        map.put("officeId",56);
        map.put("name","人员");

        List<Examine> examines = examineMapper.selectPage(map);
        for (Examine examine : examines) {
            System.out.println(examine);
        }
    }
}
