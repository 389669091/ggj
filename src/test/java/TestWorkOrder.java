
import com.hxh.config.SpringMybatisConfig;
import com.hxh.entity.WorkOrder;
import com.hxh.mapper.WorkOrderMapper;
import com.hxh.service.WorkOrderService;
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
public class TestWorkOrder {


    @Autowired
    WorkOrderMapper mapper;

    @Autowired
    WorkOrderService service;


    @Test
    public void  page(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("status","2");
        map.put("officeId",13);
        map.put("startDate","2016-09-01");

        List<WorkOrder> workOrders = mapper.selectPage(map);
        for (WorkOrder workOrder : workOrders) {
            System.out.println(workOrder);
        }
    }

    //map封装 默认会对没有值的字段忽略封装key
    @Test
    public void testSelectById(){
        Map<String, Object> map = mapper.selectById(1);
        for (String s : map.keySet()) {
            System.out.println(s+"-"+map.get(s));
        }
    }

    @Test
    public void testServiceSelectById(){
        Map<String, Object> map = service.selectDetail(1);
        for (String s : map.keySet()) {
            System.out.println(s+"-"+map.get(s));
        }
    }


}
