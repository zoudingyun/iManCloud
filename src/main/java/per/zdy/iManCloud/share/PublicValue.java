package per.zdy.iManCloud.share;

import per.zdy.iManCloud.domain.pojo.ServerConfInitialize;

import java.util.HashMap;
import java.util.Map;

public class PublicValue {

    public static String loginMessageReloginCN = "登录信息过期，请重新登录！";
    public static String loginMessageFailedCN = "登录失败，用户名或密码错误！";
    public static String InitializeFailedNullCN = "配置参数不能为空！";
    public static String InitializeFailedCN = "初始化失败！";

    /**上传文件的guid目标路径对照表*/
    public static Map<String,String> guid2path= new HashMap<>();

    //系统公用参数
    /**是否为新安装服务器*/
    public static Boolean NEW_SYSTEM = null;

    /**网站名称*/
    public static String WEB_NAME = "IManCloud";

    /**网盘文件系统路径*/
    public static String FILE_PATH = "./file";

    /**文件类型标识-文件夹*/
    public static String FILE_TYPE_FOLDER = "folder";

    /**文件类型标识-文件*/
    public static String FILE_TYPE_FILE = "file";

    /**
     * 刷新系统配置缓存
     * @param serverConfInitialize:需要更新的参数
     * */
    public static void renovateSystemConfig(ServerConfInitialize serverConfInitialize){
        WEB_NAME = serverConfInitialize.getWebName();
        FILE_PATH = serverConfInitialize.getFilePath();
    }

}
