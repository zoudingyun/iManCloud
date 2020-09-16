package per.zdy.iManCloud.web;

import cn.hutool.core.io.FileUtil;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import per.zdy.iManCloud.domain.pojo.ShareFileList;
import per.zdy.iManCloud.domain.pojo.User;
import per.zdy.iManCloud.service.FileService;
import per.zdy.iManCloud.service.LoginService;

import javax.validation.Valid;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

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
        /**是否需要提取码 0不需要提取码的分享、1需要提取码的分享、-1文件不存在*/
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
                        /*已输入验证码的用户和当前文件不一致*/
                        needCode = true;
                    }else {
                        needCode = false;
                    }
                }


            }catch (Exception ex){
                needCode = true;
            }

            mv.addObject("userReady",userReady);
            mv.addObject("needCode",needCode);
        }

        mv.setViewName("/share.html");
        mv.addObject("fileState",needPw);
        mv.addObject("fileShareUrl",f);
        mv.addObject("fileCode",f);

        return mv;
    }

    @PostMapping("/checkFileCode")
    public ModelAndView login(ModelAndView modelAndView, @Valid User user, BindingResult bindingResult){
        //shiro 24小时后失效
        SecurityUtils.getSubject().getSession().setTimeout(24*3600*1000);

        String fileUrl = user.getUserName();
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

        mv.addObject("url","/s?f="+fileUrl);
        mv.setViewName("/pages/page/goto.html");
        return mv;
    }

    /**
     * 文件分享连接
     * */
    @GetMapping("/sharedownload")
    public ResponseEntity<FileSystemResource> sharedownload(@RequestParam("isFolder") Boolean isFolder ,@RequestParam("furl") String furl,@RequestParam("fp") String fp
            ,@RequestParam("fileName") String fileName) {

        /*是否需要验证提取码*/
        Boolean needCode = true;

        String user = SecurityUtils.getSubject().getPrincipal().toString();
        /*这部分鉴权查看用户是否有权查询分享文件信息*/
        try {
            if (SecurityUtils.getSubject().hasRole("user") || SecurityUtils.getSubject().hasRole("admin")) {
                /*已登陆用户*/
                if (null != userShareFileMap.get(user + furl)) {
                    /*曾经输入过提取码*/
                    needCode = false;
                } else {
                    /*没有输入过提取码*/
                    needCode = true;
                }
            }
        } catch (Exception ex) {
            /*已输入验证码的用户*/
            if (!user.equals(furl)) {
                /*已输入验证码的用户和当前文件不一致*/
                needCode = true;
            } else {
                needCode = false;
            }
        }

        if (!needCode) {
            if (isFolder) {

            } else {
                List<ShareFileList> shareFileLists = fileService.getShareFileInfo(furl);
                if (shareFileLists.size() == 1) {
                    ShareFileList shareFileList = shareFileLists.get(0);

                    try {
                        // 获取文件名称，中文可能被URL编码
                        String rfileName = URLDecoder.decode(shareFileList.getFilePath(), "UTF-8");
                        File directory = FileUtil.touch(FILE_PATH + "/" + shareFileList.getUserName() + "/" + rfileName);

                        // 获取本地文件系统中的文件资源
                        FileSystemResource resource = new FileSystemResource(directory.getAbsolutePath());

                        // 解析文件的 mime 类型
                        String mediaTypeStr = URLConnection.getFileNameMap().getContentTypeFor(shareFileList.getFileName());
                        // 无法判断MIME类型时，作为流类型
                        mediaTypeStr = (mediaTypeStr == null) ? MediaType.APPLICATION_OCTET_STREAM_VALUE : mediaTypeStr;
                        // 实例化MIME
                        MediaType mediaType = MediaType.parseMediaType(mediaTypeStr);


                        /*
                         * 构造响应的头
                         */
                        HttpHeaders headers = new HttpHeaders();
                        // 下载之后需要在请求头中放置文件名，该文件名按照ISO_8859_1编码。
                        String filenames = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
                        headers.setContentDispositionFormData("attachment", filenames);
                        headers.setContentType(mediaType);

                        /*
                         * 返还资源
                         */
                        return ResponseEntity.ok()
                                .headers(headers)
                                .contentLength(resource.getInputStream().available())
                                .body(resource);
                    } catch (IOException e) {
                        return null;
                    }
                } else {
                    return null;
                }
            }

        }
        return null;
    }

}