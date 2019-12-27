package per.zdy.iManCloud.service.Impl;

import org.springframework.stereotype.Service;
import per.zdy.iManCloud.service.FileService;

import java.io.File;
import java.nio.file.Paths;

import static cn.hutool.core.io.FileUtil.*;
@Service
public class FileServiceImpl implements FileService {

    public void queryChildPaths(String path){
        File[] a = ls(path);
        a[0].isDirectory();
        int aa = 0;
    }

    public boolean pathIsExist(String path){
        return isDirectory(Paths.get(path),false);
    }

}
