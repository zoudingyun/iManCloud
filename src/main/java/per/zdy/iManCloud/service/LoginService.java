package per.zdy.iManCloud.service;

import per.zdy.iManCloud.domain.pojo.User;

public interface LoginService {

    public User getUserByName(String userName);

    public Boolean isNewService();

}
