package per.zdy.iManCloud.service;

import per.zdy.iManCloud.domain.pojo.ServerConfInitialize;
import per.zdy.iManCloud.domain.pojo.User;

public interface LoginService {

    public User getUserByName(String userName);

    /**
     * 检查是否是新服务器
     * @return true：是 false：否
     * */
    public Boolean isNewService();

    public void systemConf(ServerConfInitialize serverConfInitialize);

}
