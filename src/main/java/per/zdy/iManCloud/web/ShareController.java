package per.zdy.iManCloud.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import per.zdy.iManCloud.domain.pojo.User;
import per.zdy.iManCloud.service.FileService;
import per.zdy.iManCloud.service.LoginService;

import javax.validation.Valid;

import static per.zdy.iManCloud.share.PublicValue.loginMessageFailedCN;
import static per.zdy.iManCloud.share.PublicValue.loginMessageReloginCN;

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
     * 文件分享连接
     * */
    @GetMapping("/s")
    public ModelAndView register(String f){
        ModelAndView mv = new ModelAndView();
        /**是否需要提取码 0不需要提取码、1需要提取码、-1文件不存在*/
        int needPw = fileService.sharedFileNeedPw(f);
        mv.setViewName("/pages/page/share.html");
        mv.addObject("type",needPw);
        mv.addObject("fileCode",f);


        return mv;
    }

    @PostMapping("/checkFileCode")
    public ModelAndView login(ModelAndView modelAndView, @Valid User user, BindingResult bindingResult){
        //shiro 24小时后失效
        SecurityUtils.getSubject().getSession().setTimeout(24*3600*1000);

        user.setUserName(user.getUserName()+"$"+"checkFileCode");
        if(bindingResult.hasErrors()){
            modelAndView.addObject("error",bindingResult.getFieldError().getDefaultMessage());
            modelAndView.setViewName("login");
            return modelAndView;
        }
        ModelAndView mv = new ModelAndView();
        //添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = null;
        /*用户是否选择记住我*/
        usernamePasswordToken = new UsernamePasswordToken(
                user.getUserName(),
                user.getPassword()
        );

        try {
            //进行验证，这里可以捕获异常，然后返回对应信息
            subject.login(usernamePasswordToken);
        } catch (AuthenticationException e) {
            if (null == usernamePasswordToken.getUsername()) {
                mv.setViewName("/pages/page/login.html");
                mv.addObject("action",loginMessageReloginCN);
            }
            else {
                mv.setViewName("/pages/page/login.html");
                mv.addObject("action",loginMessageFailedCN);
            }
            e.getMessage();
            return mv;

        } catch (AuthorizationException e) {
            e.getMessage();
            mv.setViewName("/pages/page/500.html");
            return mv;
        }

        mv.setViewName("/pages/page/404.html");
        return mv;
    }

}