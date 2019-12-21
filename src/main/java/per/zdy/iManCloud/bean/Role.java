package per.zdy.iManCloud.bean;

import lombok.Data;
import per.zdy.iManCloud.domain.pojo.RoleList;

import java.util.Set;

@Data
public class Role {

    private String id;
    private String roleName;
    /**
     * 角色对应权限集合
     */
    private Set<Permissions> permissions;

    public Role(RoleList roleList){
        this.id = roleList.getId();
        this.roleName = roleList.getRoleName();
    }
    public Role(){
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<Permissions> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permissions> permissions) {
        this.permissions = permissions;
    }
}
