package per.zdy.iManCloud.domain.pojo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 用户角色对应关系
 * @author zdy
 * */
@Entity
@Data
public class UserPojo {

    @Id //主键
    private String userName;
    private String password;
    private String nickname;
}
