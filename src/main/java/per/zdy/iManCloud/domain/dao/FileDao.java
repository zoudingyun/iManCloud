package per.zdy.iManCloud.domain.dao;

import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.annotatoin.SqlResource;
import org.springframework.stereotype.Repository;
import per.zdy.iManCloud.domain.pojo.FilePath;

import java.io.File;
import java.util.List;

/**
 * 网盘文件相关操作
 * @author zdy
 * */
@SqlResource("file")
@Repository
public interface FileDao {

    public List<FilePath> queryUserFilePath(@Param("userName") String userName,@Param("parentPath") String parentPath);

    public void insertUserFilePath(FilePath filePath);

    public void deleteUserFilePath(FilePath filePath);

    /**
     * 查询分享文件、文件夹的提取码
     * @param shareUrl 分享地址
     * @return 分享文件的提取码
     * */
    public List<String> querySharedFilePw(@Param("shareUrl") String shareUrl);

}
