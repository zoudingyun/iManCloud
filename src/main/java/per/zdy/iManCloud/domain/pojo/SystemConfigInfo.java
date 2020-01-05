package per.zdy.iManCloud.domain.pojo;

/**
 * 系统基本配置参数
 * @author zdy
 * */
public class SystemConfigInfo {

    /**文件存储路径*/
    private String filePath;

    /**站点名称*/
    private String webName;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getWebName() {
        return webName;
    }

    public void setWebName(String webName) {
        this.webName = webName;
    }
}
