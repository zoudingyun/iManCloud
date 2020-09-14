package per.zdy.iManCloud.service;

import per.zdy.iManCloud.domain.pojo.FilePath;
import per.zdy.iManCloud.domain.pojo.User;

import java.util.List;

/**
 * @author zdy
 * */
public interface FileService {

    /**
     * 查询子路径
     * @param path 需要查询的父路径
     * */
    void queryChildPaths(String path);

    /**
     * 判断路径是否存在
     * @param path 路径
     * @return 是否存在
     * */
    boolean pathIsExist(String path);

    /**
     * 查询数据库中的用户文件记录
     * @param userName：查询用户
     * @param parentPath:查询路径
     * @return 文件列表
     * */
    List<FilePath> queryUserFilePathFromDbRecord(String userName,String parentPath);

    /**
     * 检查用户目录下指定路径的文件路径列表
     * @param userName:当前用户
     * @param parentPath :检查的目录
     * @return 文件列表
     * */
    List<FilePath> queryUserFilePathFromFileSystemByPath(String userName,String parentPath);

    /**
     * 检查用户目录下指定文件路径列表
     * @param userName:当前用户
     * @return 文件列表
     * */
    List<FilePath> queryUserFilePathFromFileSystem(String userName);

    /**
     * 插入用户路径信息
     * @param userName：当前用户
     * */
    void insertUserFilePath(String userName);

    /**
     * 更新用户当前路径下的文件信息
     * @param userName：当前用户
     * @param nowPath 当前所在路径
     * */
    void updateUserFilePath(String userName,FilePath nowPath);


    /**
     * 检查分享文件是否需要提取码
     * @param shareUrl 分享文件的url
     * @return 是否需要提取码 0 不需要 1 需要 -1不存在文件
     * * */
    int sharedFileNeedPw(String shareUrl);

    /**
     * 查询提取码
     * @param shareUrl 分享文件的url
     * @return 文件分享提取码
     * * */
    User sharedFileUser(String shareUrl);


}
