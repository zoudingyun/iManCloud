package per.zdy.iManCloud.service.Impl;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import org.apache.tomcat.jni.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import per.zdy.iManCloud.domain.dao.FileDao;
import per.zdy.iManCloud.domain.pojo.FilePath;
import per.zdy.iManCloud.service.FileService;

import java.io.File;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static cn.hutool.core.io.FileUtil.*;
import static per.zdy.iManCloud.share.PublicValue.*;

/**
 * @author zdy
 * */
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    FileDao fileDao;

    @Override
    public void queryChildPaths(String path){
        File[] a = ls(path);
        a[0].isDirectory();
        int aa = 0;
    }

    @Override
    public boolean pathIsExist(String path){
        return isDirectory(Paths.get(path),false);
    }

    @Override
    public List<FilePath> queryUserFilePathFromDbRecord(String userName, String parentPath){
        String[] queryPathStr = parentPath.split("\\./");

        if (queryPathStr.length<=0){
            parentPath = FILE_PATH+"/"+userName+"/";
        }else{
            parentPath = FILE_PATH+"/"+userName+"/"+queryPathStr[1];
        }
        parentPath = parentPath.replace('\\','/');
        List<FilePath> filePaths =getThisPath(parentPath,userName);
        List<FilePath> reList = new ArrayList<>();
        return filePaths;
    }

    @Override
    public List<FilePath> queryUserFilePathFromFileSystemByPath(String userName,String parentPath){
        return null;
    }

    @Override
    public List<FilePath> queryUserFilePathFromFileSystem(String userName){
        String userPath = FILE_PATH+"/"+userName;
        if (!FileUtil.exist(userPath)||!FileUtil.isDirectory(userPath)){
            FileUtil.mkdir(userPath);
        }
        return getPath(userPath,userName);
    }

    @Override
    public void insertUserFilePath(String userName){
        List<FilePath> filePathList = queryUserFilePathFromFileSystem(userName);
        for (FilePath filePath:filePathList){
            fileDao.insertUserFilePath(filePath);
        }
    }

    @Override
    public void updateUserFilePath(String userName,FilePath nowPath){

        List<FilePath> filePaths = fileDao.queryUserFilePath(userName,nowPath.getFilePath());
        for (FilePath filePath:filePaths){
            if (!FileUtil.exist(filePath.getFilePath())){
                fileDao.deleteUserFilePath(filePath);
            }
        }
    }

    List<FilePath> getPath(String path,String username){
        File[] files = ls(path);
        List<FilePath> filePathList = new ArrayList<>();
        for (File file:files){
            FilePath f=new FilePath();
            f.setFilePath(file.getPath().replace('\\','/'));
            f.setUserName(username);
            f.setParentPath(file.getParent().replace('\\','/')+"/");
            f.setFileName(file.getName());
            String xdPath = FileUtil.getAbsolutePath(FILE_PATH+"/"+username);
            f.setFileRelativePath(f.getFilePath().replaceAll(xdPath,"."));
            String [] fp = f.getFileRelativePath().split("/");
            String parentFileRelativePath = "";
            for(int i=0;i<fp.length-1;i++){
                parentFileRelativePath+=fp[i]+"/";
            }
            f.setParentFileRelativePath(parentFileRelativePath);
            if (file.isDirectory()){
                f.setFileType(FILE_TYPE_FOLDER);
                f.setFilePath(f.getFilePath()+"/");
                filePathList.addAll(getPath(f.getFilePath(),username));
            }else {
                f.setFileType(FILE_TYPE_FILE);
            }
            filePathList.add(f);
        }
        return filePathList;
    }

    List<FilePath> getThisPath(String path,String username){
        File[] files = ls(path);
        List<FilePath> filePathList = new ArrayList<>();
        for (File file:files){
            FilePath f= new FilePath();
            f.setFilePath(file.getPath().replace('\\','/'));
            f.setUserName(username);
            f.setParentPath(file.getParent().replace('\\','/')+"/");
            f.setFileName(file.getName());
            String xdPath = FileUtil.getAbsolutePath(FILE_PATH+"/"+username);
            f.setFileRelativePath(f.getFilePath().replaceAll(xdPath,"."));
            String [] fp = f.getFileRelativePath().split("/");
            String parentFileRelativePath = "";
            for(int i=0;i<fp.length-1;i++){
                parentFileRelativePath+=fp[i]+"/";
            }
            f.setParentFileRelativePath(parentFileRelativePath);
            if (file.isDirectory()){
                f.setFileType(FILE_TYPE_FOLDER);
                f.setFilePath(f.getFilePath()+"/");
                f.setDisplayFileSize("--");
            }else {
                f.setFileType(FileTypeUtil.getType(file));
                long fileSize = file.length();
                String displayFilesize = "";
                //文件大于1G
                if (fileSize>=gbSize){
                    displayFilesize = (Math.round((fileSize/(gbSize+0F))*100F))/100F+" GB";
                }else if (fileSize>=mbSize){
                    //文件大于1M
                    displayFilesize = (Math.round((fileSize/(mbSize+0F))*100F))/100F+" MB";
                }else if (fileSize>=kbSize){
                    //文件大于1KB
                    displayFilesize = (Math.round((fileSize/(kbSize+0F))*100F))/100F+" KB";
                }else {
                    displayFilesize = fileSize+" B";
                }

                f.setFileSize(fileSize);
                f.setDisplayFileSize(displayFilesize+"");

                SimpleDateFormat sdf = new SimpleDateFormat(timeFormatNos);
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(file.lastModified());

                f.setChangeTime(sdf.format(cal.getTime()));

            }

            filePathList.add(f);
        }
        return filePathList;
    }

}
