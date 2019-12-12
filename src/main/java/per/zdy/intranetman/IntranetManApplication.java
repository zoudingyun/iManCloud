package per.zdy.intranetman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import per.zdy.intranetman.domain.task.BeetlDemo;

@SpringBootApplication
public class IntranetManApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntranetManApplication.class, args);
        BeetlDemo beetlDemo =new BeetlDemo();
        beetlDemo.run();
    }

}
