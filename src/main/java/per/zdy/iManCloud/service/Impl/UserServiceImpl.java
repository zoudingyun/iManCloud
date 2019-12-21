package per.zdy.iManCloud.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import per.zdy.iManCloud.domain.dao.UserDao;
import per.zdy.iManCloud.domain.pojo.User;
import per.zdy.iManCloud.service.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userMapper;

    public User findByName(String name){
        return  null;
    }

    public List<User> getUserList(){
        return null;
    }

}
