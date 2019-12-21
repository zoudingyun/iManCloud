package per.zdy.iManCloud.service.Impl;

import org.springframework.stereotype.Service;
import per.zdy.iManCloud.service.FileService;

import java.io.File;

import static cn.hutool.core.io.FileUtil.ls;

@Service
public class FileServiceImpl implements FileService {

    public void queryChildPaths(String path){
        File[] a = ls(path);
        a[0].isDirectory();
        int aa = 0;
    }

}
