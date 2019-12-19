package per.zdy.intranetman.domain.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 角色权限对应关系
 * @author zdy
 * */
@Entity
@Data
public class RolePermissions {

    @Id
    private int id;

    private String roleName;
    private String permissionsName;

}
