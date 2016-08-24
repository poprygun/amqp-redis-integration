package hello;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PreDestroy;
import java.util.List;

@RestController
public class MonitorController {

    @Autowired
    private StringRedisTemplate template;

    private static final String key = "myCollection";

    @RequestMapping(method = RequestMethod.GET, value = "/redis")
    public List<String> messagesInRedis() {
        Long size = template.opsForList().size(key);
        System.out.println("Collection size " + size);
        return template.opsForList().range(key, 0, size - 1);
    }

    @PreDestroy
    public void clearRabbit() {
        template.delete(key);
    }
}
