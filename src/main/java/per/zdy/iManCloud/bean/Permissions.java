package per.zdy.iManCloud.bean;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Permissions {

    @Id //主键
    private String id;
    private String permissionsName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPermissionsName() {
        return permissionsName;
    }

    public void setPermissionsName(String permissionsName) {
        this.permissionsName = permissionsName;
    }

/*public Permissions(String id,String permissionsName){
        this.id = id;
        this.permissionsName = permissionsName;
    }*/

}
