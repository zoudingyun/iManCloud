package per.zdy.iManCloud;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import per.zdy.iManCloud.service.FileService;

@SpringBootTest
class IManCloudApplicationTests {

    @Autowired
    FileService fileService;

    @Test
    void contextLoads() {
        Boolean v = fileService.pathIsExist("D:\\英雄时刻");

        fileService.queryChildPaths("C:/app");
    }

}
