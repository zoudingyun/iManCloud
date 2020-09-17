package per.zdy.iManCloud.conf;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.hibernate.collection.internal.PersistentSet;
import org.springframework.beans.factory.annotation.Autowired;
import per.zdy.iManCloud.bean.Permissions;
import per.zdy.iManCloud.bean.Role;
import per.zdy.iManCloud.domain.pojo.User;
import per.zdy.iManCloud.service.FileService;
import per.zdy.iManCloud.service.LoginService;

import java.util.HashSet;
import java.util.Set;

/**
 * 自定义shiro验证方式
 * @author zdy
 * */
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private LoginService loginService;
    @Autowired
    private FileService fileService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登录用户名
        String name = (String) principalCollection.getPrimaryPrincipal();
        //根据用户名去数据库查询用户信息
        User user = loginService.getUserByName(name);
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (Role role : user.getRoles()) {
            //添加角色
            simpleAuthorizationInfo.addRole(role.getRoleName());
            //添加权限
            for (Permissions permissions : role.getPermissions()) {
                simpleAuthorizationInfo.addStringPermission(permissions.getPermissionsName());
            }
        }
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
        //获取用户信息
        String name = authenticationToken.getPrincipal().toString();
        String[] nameStr = name.split("\\$");
        if (nameStr.length!=2){
            return null;
        }
        name = nameStr[0];
        User user = new User();
        if ("checkFileCode".equals(nameStr[1])){
            /*验证提取码*/
            user = fileService.sharedFileUser(name);
        }else {
            /*验证用户登录*/
            user = loginService.getUserByName(name);
        }
        if (user == null) {
            //这里返回后会报出对应异常
            return null;
        } else {
            //这里验证authenticationToken和simpleAuthenticationInfo的信息
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name, user.getPassword().toString(), getName());
            return simpleAuthenticationInfo;
        }
    }
}
