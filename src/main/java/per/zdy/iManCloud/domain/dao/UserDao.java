package per.zdy.iManCloud.domain.dao;

import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.annotatoin.SqlResource;
import org.beetl.sql.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import per.zdy.iManCloud.bean.Permissions;
import per.zdy.iManCloud.domain.pojo.RoleList;
import per.zdy.iManCloud.domain.pojo.User;
import per.zdy.iManCloud.domain.pojo.UserInfo;

import java.util.List;

/**
 * @Auther: xf
 * @Date: 2018/12/2 21:45
 * @Description:
 * BaseMapper包含了内置的常用 CRUD
 */
// 通过@SqlResource 注解来指定Mapper对应的sql文件 user.md 文件
// @SqlResource("ron.user")  多级目录
@SqlResource("user")
@Repository
public interface UserDao extends BaseMapper<User> {
    // JDK 1.8 可不加@Param  但java编译的时候开启-parameters选项 @Param("name") String name

    /**查询所有的权限列表*/
    public List<Permissions> queryAllPermission();

    /**通过角色名查询权限列表*/
    public List<Permissions> queryPermissionsByRoleName(@Param("roleName") String roleName);

    /**通过用户名查询角色列表*/
    public List<RoleList> queryRolesByUserName(@Param("userName") String userName);

    /**通过用户名查询用户信息*/
    public List<UserInfo> queryUserInfoByUserName(@Param("userName") String userName);

    /**查询用户个数*/
    public int queryUserCount();


}
