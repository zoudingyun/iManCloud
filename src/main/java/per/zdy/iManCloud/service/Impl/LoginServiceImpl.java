package per.zdy.iManCloud.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import per.zdy.iManCloud.bean.Permissions;
import per.zdy.iManCloud.bean.Role;
import per.zdy.iManCloud.domain.dao.UserDao;
import per.zdy.iManCloud.domain.pojo.User;
import per.zdy.iManCloud.domain.pojo.UserInfo;
import per.zdy.iManCloud.service.LoginService;

import java.util.*;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    UserDao userDao;

    @Override
    public User getUserByName(String getMapByName) {
        //模拟数据库查询，正常情况此处是从数据库或者缓存查询。
        return getMapByName(getMapByName);
    }

    @Override
    public Boolean isNewService(){
        if (userDao.queryUserCount()>0){
            return false;
        }else {
            return true;
        }
    }

    /**
     * 模拟数据库查询
     * @param userName
     * @return
     */
    private User getMapByName(String userName){

        User user = new User();
        List<UserInfo> userInfos = userDao.queryUserInfoByUserName(userName);
        if (userInfos.size()<=0){
            return null;
        }
        List<Role> roles = new ArrayList<>();
        Set<Role> roleSet = new HashSet<>();
        for (UserInfo userInfo:userInfos){
            Role role = new Role();
            role.setRoleName(userInfo.getRoleName());
            roles.add(role);
        }
        for (int i =0;i<roles.size();i++){
            Set<Permissions> permissionsSet = new HashSet<>();
            for (UserInfo userInfo:userInfos){
                if (roles.get(i).getRoleName().equals(userInfo.getRoleName())){
                    Permissions permissions = new Permissions();
                    permissions.setPermissionsName(userInfo.getPermissionsName());
                    permissionsSet.add(permissions);
                }
            }
            roles.get(i).setPermissions(permissionsSet);
            roleSet.add(roles.get(i));
        }

        user.setUserName(userName);
        user.setNickname(userInfos.get(0).getNickname());
        user.setPassword(userInfos.get(0).getPassword());
        user.setRoles(roleSet);

        return user;
    }
}
