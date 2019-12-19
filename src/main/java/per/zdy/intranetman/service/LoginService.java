package per.zdy.intranetman.service;

import per.zdy.intranetman.domain.pojo.User;

public interface LoginService {

    public User getUserByName(String userName);

    public Boolean isNewService();

}
