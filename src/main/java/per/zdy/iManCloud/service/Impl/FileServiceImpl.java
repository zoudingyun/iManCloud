package per.zdy.iManCloud.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import per.zdy.iManCloud.domain.dao.FileDao;
import per.zdy.iManCloud.domain.pojo.FilePath;
import per.zdy.iManCloud.service.FileService;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
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
        List<FilePath> filePaths = fileDao.queryUserFilePath(userName,parentPath+"%",parentPath,parentPath+"/");
        List<FilePath> reList = new ArrayList<>();
        for (int i= 0;i<filePaths.size();i++){
            String filePath = filePaths.get(i).getFilePath();
            filePath = filePath.substring(parentPath.length());
            if (filePath.indexOf("/")>0){
                reList.add(filePaths.get(i));
            }else if (filePath.indexOf("/")==0){
                filePath = filePath.substring(1);
                if (filePath.indexOf("/")>=0){
                    reList.add(filePaths.get(i));
                }
            }
        }
        for (FilePath integer:reList){
            filePaths.remove(integer);
        }
        return filePaths;
    }

}
