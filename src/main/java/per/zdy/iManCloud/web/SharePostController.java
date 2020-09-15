package per.zdy.iManCloud.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import per.zdy.iManCloud.domain.pojo.ShareFileList;
import per.zdy.iManCloud.domain.pojo.User;
import per.zdy.iManCloud.service.FileService;
import per.zdy.iManCloud.service.LoginService;
import per.zdy.iManCloud.share.Result;
import per.zdy.iManCloud.share.ResultGenerator;

import javax.validation.Valid;

import java.util.List;

import static per.zdy.iManCloud.share.PublicValue.*;

/**
 * 文件分享
 * @author zdy
 * */
@RequestMapping("/share")
@RestController
public class SharePostController {

    @Autowired
    LoginService loginService;

    @Autowired
    FileService fileService;


    @PostMapping("/checkFileCode")
    public Result login(@RequestBody User user){

        if (fileService.checkFileCode(user.getUserName(),user.getPassword())){
            /*存入缓存*/
            userShareFileMap.put(SecurityUtils.getSubject().getPrincipal().toString()+user.getUserName(),System.currentTimeMillis());
            return ResultGenerator.genSuccessResult();
        }else {
            return ResultGenerator.genFailResult("提取码有误！");
        }

    }

    @PostMapping("/getShareFileInfo")
    public Result getShareFileInfo(String fileUrl){

        /*是否需要验证提取码*/
        Boolean needCode = true;
        /*用户是否已登录*/
        Boolean userReady = false;

        String user = SecurityUtils.getSubject().getPrincipal().toString();
        /*这部分鉴权查看用户是否有权查询分享文件信息*/
        try{
            if (SecurityUtils.getSubject().hasRole("user")||SecurityUtils.getSubject().hasRole("admin")){
                /*已登陆用户*/
                if (null!=userShareFileMap.get(user+fileUrl)){
                    /*曾经输入过提取码*/
                    needCode = false;
                }else {
                    /*没有输入过提取码*/
                    needCode = true;
                }
            }
        }catch (Exception ex){
            /*已输入验证码的用户*/
            if (!user.equals(fileUrl)){
                /*已输入验证码的用户和当前文件不一致*/
                needCode = true;
            }else {
                needCode = false;
            }
        }

        /*判断当前请求是否合法*/
        if (!needCode){
            List<ShareFileList> shareFileLists = fileService.getShareFileInfo(fileUrl);
            if (shareFileLists.size()==1){
                ShareFileList shareFileList = shareFileLists.get(0);
                shareFileList.setEndTime("");
                shareFileList.setFilePassWord("");
                shareFileList.setFilePath(null);
                shareFileList.setUserName("");
                shareFileList.setShareTime("");
                return ResultGenerator.genSuccessResult(shareFileList);
            }else {
                return ResultGenerator.genFailResult("文件分享异常！");
            }
        }else {
            return ResultGenerator.genFailResult("未经授权的访问！");
        }
    }

}