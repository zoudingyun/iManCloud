package per.zdy.intranetman.domain.dao;

import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.annotatoin.SqlResource;
import org.beetl.sql.core.engine.PageQuery;
import org.beetl.sql.core.mapper.BaseMapper;
import per.zdy.intranetman.domain.pojo.User;

import java.util.List;

/**
 * @Auther: xf
 * @Date: 2018/12/2 21:45
 * @Description:
 * BaseMapper包含了内置的常用 CRUD
 */
// 通过@SqlResource 注解来指定Mapper对应的sql文件 user.md 文件
// @SqlResource("ron.user")  多级目录
@SqlResource("user")
public interface UserDao extends BaseMapper<User> {
    // JDK 1.8 可不加@Param  但java编译的时候开启-parameters选项
    List<User> selectUserByName(@Param("name") String name);
    void getUserListPageQuery(PageQuery query);
    List<User> createTable();
    List<User> findByName();
    List<User> getUserList();


}
