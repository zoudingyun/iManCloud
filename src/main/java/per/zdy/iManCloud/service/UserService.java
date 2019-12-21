package per.zdy.iManCloud.service;

import per.zdy.iManCloud.domain.pojo.User;

import java.util.List;

public interface UserService {

    public User findByName(String name);
    public List<User> getUserList();
}
