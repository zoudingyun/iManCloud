package per.zdy.iManCloud.service.Impl;

import cn.hutool.core.io.FileUtil;
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
import static per.zdy.iManCloud.share.PublicValue.*;

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

    public List<FilePath> queryUserFilePathFromFileSystemByPath(String userName,String parentPath){
        return null;
    }

    public List<FilePath> queryUserFilePathFromFileSystem(String userName){
        String userPath = FILE_PATH+"/"+userName;
        if (!pathIsExist(userPath)){
            FileUtil.mkdir(userPath);
        }
        return getPath(userPath,userName);
    }

    List<FilePath> getPath(String path,String username){
        File[] files = ls(path);
        List<FilePath> filePathList = new ArrayList<>();
        for (File file:files){
            FilePath f=new FilePath();
            f.setFilePath(file.getPath());
            f.setUserName(username);
            if (file.isDirectory()){
                f.setFileType(FILE_TYPE_FOLDER);
                filePathList.addAll(getPath(f.getFilePath(),username));
            }else {
                f.setFileType(FILE_TYPE_FILE);
            }
            filePathList.add(f);
        }
        return filePathList;
    }

}
