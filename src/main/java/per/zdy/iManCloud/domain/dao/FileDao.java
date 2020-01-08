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

    public List<FilePath> queryUserFilePath(@Param("userName") String userName,@Param("parentPathSearch") String parentPathSearch,@Param("parentPath") String parentPath,@Param("parentPath1") String parentPath1);

    public void insertUserFilePath(FilePath filePath);

}
