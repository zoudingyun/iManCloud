package per.zdy.iManCloud.service;

import per.zdy.iManCloud.domain.pojo.ServerConfInitialize;
import per.zdy.iManCloud.domain.pojo.User;

public interface LoginService {

    /**
     * 查询用户信息
     * @param userName:用户账号
     * */
    public User getUserByName(String userName);

    /**
     * 检查是否是新服务器
     * @return true：是 false：否
     * */
    public Boolean isNewService();

    /**
     * 插入系统基础配置参数
     * @param serverConfInitialize:需要上传的系统配置
     * */
    public void systemConf(ServerConfInitialize serverConfInitialize);

    /**
     * 刷新系统基础配置缓存
     * */
    public void renovateSystemConf();

}
