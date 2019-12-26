package per.zdy.iManCloud.conf;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 初始化配置
 * */
@Component
@Order(value = 1)
public class ServerConfig implements ApplicationRunner {

    /**网盘文件存放主目录*/
    public static String filePath = "";

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("》》》》》》》》》》》");
    }

}
