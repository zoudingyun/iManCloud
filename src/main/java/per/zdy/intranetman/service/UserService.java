package per.zdy.intranetman.service;

import per.zdy.intranetman.domain.pojo.User;

import java.util.List;

public interface UserService {

    public User findByName(String name);
    public List<User> getUserList();
}
