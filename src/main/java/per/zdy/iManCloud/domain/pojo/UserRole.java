package per.zdy.iManCloud.domain.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class UserRole {

    @Id
    private int id;

    private String userName;
    private String roleName;

}
