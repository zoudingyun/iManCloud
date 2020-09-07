package per.zdy.iManCloud.domain.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 文件信息
 * @author zdy
 * */
@Entity
@Data
public class FilePath {
    @Id
    String filePath;
    String userName;
    String fileType;
    String parentPath;
    String fileName;
    String fileRelativePath;
    String parentFileRelativePath;
    String fileSize;
    String changeTime;

    public String getParentFileRelativePath() {
        return parentFileRelativePath;
    }

    public void setParentFileRelativePath(String parentFileRelativePath) {
        this.parentFileRelativePath = parentFileRelativePath;
    }

    public String getFileRelativePath() {
        return fileRelativePath;
    }

    public void setFileRelativePath(String fileRelativePath) {
        this.fileRelativePath = fileRelativePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
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

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
