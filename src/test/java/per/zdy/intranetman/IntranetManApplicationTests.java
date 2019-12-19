package per.zdy.intranetman;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import per.zdy.intranetman.domain.dao.UserDao;
import per.zdy.intranetman.domain.pojo.User;

import java.util.List;

@SpringBootTest
class IntranetManApplicationTests {

    @Autowired
    UserDao userDao;

    @Test
    void contextLoads() {
    }

}
