
import com.hxh.config.SpringMybatisConfig;
import com.hxh.entity.SysOffice;
import com.hxh.mapper.SysOfficeMapper;
import com.hxh.service.SysOfficeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringMybatisConfig.class)//加载dao层配置类
public class TestRedis {

    @Autowired
    RedisConnectionFactory connectionFactory;

    @Autowired
    RedisTemplate<Object,Object> redisTemplate;

    @Autowired
    SysOfficeMapper mapper;


    @Autowired
    SysOfficeService service;


    @Test
    public void testConnection(){
        RedisConnection connection = connectionFactory.getConnection();
        Jedis jedis = (Jedis) connection.getNativeConnection();//jedis
//        System.out.println(jedis.get("name1"));
       Set<String>set= jedis.smembers("set");
        for (String s : set) {
            System.out.println(s);
        }
        System.out.println(jedis);
    }



    @Test
    public void testRedisTemplate(){
        /*根据不同的value的类型调用不同的api获取不同的值操作对象*/
        ValueOperations<Object, Object> ops = redisTemplate.opsForValue();
        //redisTemplate的api会有自己的序列化和反序列化的处理对象，key经过序列化处理后，并不是"name1"
        //注意旧的key：value不能使用
//        System.out.println(ops.get("name1"));
        ops.set("redisTemplate1","hello redisTemplate1");
        System.out.println(ops.get("redisTemplate1"));
    }


    @Test
    public void testCache(){
        SysOffice sysOffice = new SysOffice();
        sysOffice.setDelFlag("0");
        List<SysOffice> offices = mapper.select(sysOffice);
        ValueOperations<Object, Object> ops = redisTemplate.opsForValue();
        ops.set("offices",offices);

        List<SysOffice> offices2 = (List<SysOffice>) ops.get("offices");
        for (SysOffice office : offices2) {
            System.out.println(office);
        }


    }
    /*测试redis作为第三方缓存处理*/
    @Test
    public void testService(){
        SysOffice sysOffice = new SysOffice();
        sysOffice.setDelFlag("0");
        List<SysOffice> offices = service.select(sysOffice);
        System.out.println(offices);
        System.out.println("-------------------------------------");
        offices = service.select(sysOffice);
        System.out.println(offices);

        sysOffice.setAreaId(2L);
        List<SysOffice> offices2 = service.select(sysOffice);
        System.out.println(offices2);
    }

    @Test
    public void  test(){
        SysOffice sysOffice = new SysOffice();
        sysOffice.setDelFlag("0");
        service.select(sysOffice);
//        service.selectByPrimaryKey(sysOffice);

    }
    @Test
    public void testPut(){
        SysOffice sysOffice ;
        sysOffice=service.selectByPrimaryKey(2);
        sysOffice.setPhone("13800138001");
        SysOffice sysOffice1 = service.updateByPrimaryKeySelectiveCache(sysOffice);
    }

    @Test
    public void testEvict(){
        SysOffice sysOffice = service.selectByPrimaryKey(2);
        sysOffice = service.selectByPrimaryKey(2);
        sysOffice.setPhone("13800138002");
        service.updateByPrimaryKeySelective(sysOffice);
    }
}


