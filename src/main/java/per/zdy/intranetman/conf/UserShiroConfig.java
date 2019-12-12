package per.zdy.intranetman.conf;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import per.zdy.intranetman.domain.pojo.User;
import per.zdy.intranetman.service.UserService;

public class UserShiroConfig extends AuthorizingRealm {

    private UserService userService;

    /**
     * 执行授权逻辑
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 执行认证逻辑
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行认证逻辑");
        UsernamePasswordToken token =(UsernamePasswordToken)authenticationToken;
        User user= userService.findByName(token.getUsername());
        if (user == null){
            return null;
        }
        return new SimpleAuthenticationInfo("",user.getPassword(),"");
    }

}
