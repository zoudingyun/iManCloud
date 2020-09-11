package per.zdy.iManCloud.domain.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 分享文件
 * @author zdy
 * */
@Entity
@Data
public class ShareFileList {


    @Id
    /**分享文件的代码*/
    String shareUrl;

    /**分享类型 文件、文件夹*/
    String shareType;

    /**文件相对路径*/
    String filePath;

    /**分享人*/
    String userName;

    /**分享时间*/
    String shareTime;

    /**分享时限*/
    String endTime;

    /**提取码*/
    String filePassWord;

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getShareType() {
        return shareType;
    }

    public void setShareType(String shareType) {
        this.shareType = shareType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getShareTime() {
        return shareTime;
    }

    public void setShareTime(String shareTime) {
        this.shareTime = shareTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getFilePassWord() {
        return filePassWord;
    }

    public void setFilePassWord(String filePassWord) {
        this.filePassWord = filePassWord;
    }
}
