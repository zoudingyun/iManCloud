package per.zdy.iManCloud.web;

import cn.hutool.log.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import per.zdy.iManCloud.domain.pojo.ServerConfInitialize;
import per.zdy.iManCloud.domain.pojo.User;
import per.zdy.iManCloud.service.FileService;
import per.zdy.iManCloud.service.LoginService;

import javax.validation.Valid;

import static per.zdy.iManCloud.share.PublicValue.*;

/**
 * 文件分享
 * @author zdy
 * */
@Controller
public class ShareController {

    @Autowired
    LoginService loginService;

    @Autowired
    FileService fileService;

    /**
     * 文件访问连接
     * */
    @GetMapping("/s")
    public ModelAndView register(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/pages/page/share.html");
        return mv;
    }

}