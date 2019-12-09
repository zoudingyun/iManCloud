package per.zdy.intranetman.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import per.zdy.intranetman.domain.pojo.UserEntity;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

@Controller
@ResponseBody
public class AdminController {

    @GetMapping(value = "/test")
    public ModelAndView test(HttpServletRequest req) {
        // UserEntity userEntity = getCurrentUser(req);
        UserEntity user = new UserEntity();
        user.setLoginName("tom");
        user.setId("234");
        user.setBindType("1");
        ModelAndView mv = new ModelAndView();
        mv.addObject("user", user);
        mv.setViewName("/model/Upload.html");
        return mv;
    }

    private final int BYTES_PER_SLICE = 1<<20;
    @RequestMapping(value="sliceUpload",method= RequestMethod.POST)
    public int upload(@RequestParam("slice") MultipartFile slice, String fileName, int index) {
        int result = 0;
        if(slice.isEmpty()){
            return 0;
        }
        int size = (int) slice.getSize();
        System.out.println(fileName + "-->" + size);

        String path = "C:/迅雷下载" ;
        File dest = new File(path + "/" + fileName);
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(dest,"rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try {
            byte[] bytes = slice.getBytes(); //保存文件
            randomAccessFile.seek(index*BYTES_PER_SLICE);
            randomAccessFile.write(bytes);
            result = 1;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            try {
                randomAccessFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
