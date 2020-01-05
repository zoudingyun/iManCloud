package per.zdy.iManCloud.domain.dao;

import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.annotatoin.SqlResource;
import org.springframework.stereotype.Repository;
import per.zdy.iManCloud.domain.pojo.ServerConfInitialize;
/**
 * @author zdy
 * 系统参数配置
 * */
@SqlResource("system")
@Repository
public interface SystemDao {

    /**增加用户*/
    public void addUser(@Param("userName") String userName,@Param("password") String password,@Param("nickname") String nickname);

    /**配置系统配置信息*/
    public void addSystemConf(@Param("confName") String confName,@Param("confValue") String confValue);

    /**配置系统角色*/
    public void addRoleList(@Param("roleName") String roleName);

    /**配置用户角色*/
    public void addRole(@Param("roleName") String roleName,@Param("userName") String userName);

    /**配置角色权限*/
    public void addRolePermissions(@Param("roleName") String roleName,@Param("permissionsName") String permissionsName);

    /**配置权限列表*/
    public void addPermissions(@Param("permissionsName") String permissionsName);

    /**查询系统配置参数*/
    public ServerConfInitialize querySystemConf();
}
