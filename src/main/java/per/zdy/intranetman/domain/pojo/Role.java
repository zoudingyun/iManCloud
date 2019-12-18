package per.zdy.intranetman.domain.pojo;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Role {

    @Id //主键
    private Integer id;
    private Integer age;
    private String name;
    private String address;
    private String city;

}
