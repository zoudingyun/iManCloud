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

@Controller
public class LoginController {

    @Autowired
    LoginService loginService;

    @Autowired
    FileService fileService;

    @PostMapping("/login")
    public ModelAndView login(ModelAndView modelAndView, @Valid User user, BindingResult bindingResult){
        //shiro 24小时后失效
        SecurityUtils.getSubject().getSession().setTimeout(24*30*60*1000);
        if(bindingResult.hasErrors()){
            modelAndView.addObject("error",bindingResult.getFieldError().getDefaultMessage());
            modelAndView.setViewName("login");
            return modelAndView;
        }
        ModelAndView mv = new ModelAndView();
        //添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
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
        mv.addObject("url","/");
        mv.setViewName("/pages/page/goto.html");
        return mv;
    }

    @GetMapping("/login")
    public ModelAndView login(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/pages/page/login.html");
        if (loginService.isNewService()){
            mv.addObject("url","/start");
            mv.setViewName("/pages/page/goto.html");
        }else {
            mv.setViewName("/pages/page/login.html");
        }
        return mv;
    }

    @GetMapping("/start")
    public ModelAndView start(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/pages/page/start.html");
        return mv;
    }
    @RequestMapping("/start")
    public ModelAndView start(ModelAndView modelAndView, @RequestBody ServerConfInitialize serverConfInitialize, BindingResult bindingResult){
        ModelAndView mv = new ModelAndView();
        if (serverConfInitialize.checkNull()){
            if (fileService.pathIsExist(serverConfInitialize.getFilePath())){
                try{
                    loginService.systemConf(serverConfInitialize);
                    Boolean a  = loginService.isNewService();
                    mv.addObject("url","/");
                    mv.setViewName("/pages/page/goto.html");
                    loginService.renovateSystemConf();
                }catch (Exception ex){
                    LogFactory.get().error(ex);
                    mv.addObject("action",InitializeFailedCN+ex.getMessage());
                }
            }else {
                mv.addObject("action",InitializeFailedNullCN);
            }
        }
        return mv;
    }

    @GetMapping("/register")
    public ModelAndView register(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/pages/page/register.html");
        return mv;
    }
  /*  @RequestMapping("/login")
    public ModelAndView login(User user) {
        ModelAndView mv = new ModelAndView();
        //添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
                user.getUserName(),
                user.getPassword()
        );
        try {
            //进行验证，这里可以捕获异常，然后返回对应信息
            subject.login(usernamePasswordToken);
//            subject.checkRole("admin");
//            subject.checkPermissions("query", "add");
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
        mv.setViewName("/index.html");
        return mv;
    }*/
    //注解验角色和权限
    @RequiresRoles("admin")
    @RequiresPermissions("add")
    @RequestMapping("/index")
    public String index() {
        return "index!";
    }
}