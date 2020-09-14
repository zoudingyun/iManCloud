package per.zdy.iManCloud.web;

import com.sun.org.apache.xpath.internal.operations.Bool;
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
     * 文件分享连接
     * */
    @GetMapping("/s")
    public ModelAndView register(String f){

        ModelAndView mv = new ModelAndView();
        /*是否需要验证提取码*/
        Boolean needCode = true;
        /*用户是否已登录*/
        Boolean userReady = false;
        /**是否需要提取码 0不需要提取码、1需要提取码、-1文件不存在*/
        int needPw = fileService.sharedFileNeedPw(f);
        if (needPw ==1){

            try{
                String user = SecurityUtils.getSubject().getPrincipal().toString();
                try{
                    if (SecurityUtils.getSubject().hasRole("user")||SecurityUtils.getSubject().hasRole("admin")){
                        /*已登陆用户*/
                        userReady = true;
                        if (null!=userShareFileMap.get(user+f)){
                            /*曾经输入过提取码*/
                            needCode = false;
                        }else {
                            /*没有输入过提取码*/
                            needCode = true;
                        }
                    }
                }catch (Exception ex){
                    /*已输入验证码的用户*/
                    userReady = false;
                    if (!user.equals(f)){
                        needCode = true;
                    }else {
                        needCode = false;
                    }
                }


            }catch (Exception ex){
                needCode = true;
            }

            mv.addObject("userReady",userReady);
            mv.setViewName("/pages/page/share.html");
            mv.addObject("type",needPw);
            mv.addObject("fileCode",f);
            mv.addObject("needCode",needCode);
        }else if (needPw ==0){

        }else {

        }





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

        mv.addObject("url","/s?f=111");
        mv.setViewName("/pages/page/goto.html");
        return mv;
    }

}