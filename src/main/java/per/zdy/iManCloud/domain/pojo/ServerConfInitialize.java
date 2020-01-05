package per.zdy.iManCloud.domain.pojo;

/**
 * 系统基本配置参数
 * @author zdy
 * */
public class ServerConfInitialize {

    /**站点名称*/
    private String webName;

    /**管理员账号*/
    private String adminId;

    /**管理员密码*/
    private String adminPwd;

    /**管理员昵称*/
    private String adminName;

    /**网盘存储路径*/
    private String filePath;

    public String getWebName() {
        return webName;
    }

    public void setWebName(String webName) {
        this.webName = webName;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminPwd() {
        return adminPwd;
    }

    public void setAdminPwd(String adminPwd) {
        this.adminPwd = adminPwd;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean checkNull(){
        if (null==this.adminId||null==this.adminName||null==this.adminPwd||null==this.filePath||null==this.webName){
            return false;
        }else {
            return true;
        }
    }
}
