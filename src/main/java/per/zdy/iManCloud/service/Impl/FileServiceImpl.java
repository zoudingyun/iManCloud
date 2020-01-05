package per.zdy.iManCloud.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import per.zdy.iManCloud.domain.dao.FileDao;
import per.zdy.iManCloud.domain.pojo.FilePath;
import per.zdy.iManCloud.service.FileService;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

import static cn.hutool.core.io.FileUtil.*;
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    FileDao fileDao;

    public void queryChildPaths(String path){
        File[] a = ls(path);
        a[0].isDirectory();
        int aa = 0;
    }

    public boolean pathIsExist(String path){
        return isDirectory(Paths.get(path),false);
    }

    public List<FilePath> queryUserFilePathFromDbRecord(String userName, String parentPath){
        if (null == parentPath){
            parentPath = "";
        }
        return fileDao.queryUserFilePath(userName,parentPath+"%");
    }

}
