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
import per.zdy.iManCloud.domain.pojo.User;
import per.zdy.iManCloud.service.FileService;
import per.zdy.iManCloud.service.LoginService;
import per.zdy.iManCloud.share.Result;
import per.zdy.iManCloud.share.ResultGenerator;

import javax.validation.Valid;

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

}