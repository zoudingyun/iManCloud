package per.zdy.iManCloud;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import per.zdy.iManCloud.domain.dao.SystemDao;
import per.zdy.iManCloud.service.FileService;

@SpringBootTest
class IManCloudApplicationTests {

    @Autowired
    FileService fileService;

    @Autowired
    SystemDao systemDao;

    @Test
    void contextLoads() {
        Boolean v = fileService.pathIsExist("D:\\英雄时刻");

        fileService.queryChildPaths("C:/app");
    }

    @Test
    void testSystemConf(){
        Object o = systemDao.querySystemConf();
        return;
    }

}
