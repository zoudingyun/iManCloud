package per.zdy.iManCloud.service;

import per.zdy.iManCloud.domain.pojo.FilePath;

import java.util.List;

public interface FileService {

    public void queryChildPaths(String path);

    public boolean pathIsExist(String path);

    /**
     * 查询数据库中的用户文件记录
     * @param userName：查询用户
     * @param parentPath:查询路径
     * */
    public List<FilePath> queryUserFilePathFromDbRecord(String userName,String parentPath);

    /**
     * 检查用户目录下指定路径的文件路径列表
     *
     * */
    public List<FilePath> queryUserFilePathFromFileSystemByPath(String userName,String parentPath);

    /**
     * 检查用户目录下指定文件路径列表
     * @param userName:用户名
     * */
    public List<FilePath> queryUserFilePathFromFileSystem(String userName);

}
