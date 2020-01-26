package per.zdy.iManCloud.web;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import per.zdy.iManCloud.domain.pojo.FilePath;
import per.zdy.iManCloud.service.FileService;
import per.zdy.iManCloud.share.Result;
import per.zdy.iManCloud.share.ResultGenerator;


@RequestMapping("/fileExplorerController")
@RestController
public class FileExplorerController {

    @Autowired
    FileService fileService;

    /**
     * 查询当前用户的文件列表（指定目录）
     * @param filePath:可传入filePath指定查询的文件夹
     * */
    @PostMapping("/queryUserPath")
    @CrossOrigin
    public Result queryUserPath(@RequestBody FilePath filePath) {
        try{
            return ResultGenerator.genSuccessResult(fileService.queryUserFilePathFromDbRecord(SecurityUtils.getSubject().getPrincipal().toString(),filePath.getFilePath()));
        }catch (Exception ex){
            return ResultGenerator.genFailResult(ex.getMessage());
        }
    }

    @PostMapping("/updateFilePath")
    @CrossOrigin
    public Result updateFilePath() {
        try{
            fileService.insertUserFilePath(SecurityUtils.getSubject().getPrincipal().toString());
            return ResultGenerator.genSuccessResult();
        }catch (Exception ex){
            ex.printStackTrace();
            return ResultGenerator.genFailResult(ex.getMessage());
        }
    }

    @PostMapping("/updateFilePathByPath")
    @CrossOrigin
    public Result updateFilePathByPath(@RequestBody FilePath filePath) {
        try{
            fileService.insertUserFilePath(SecurityUtils.getSubject().getPrincipal().toString());
            return ResultGenerator.genSuccessResult();
        }catch (Exception ex){
            ex.printStackTrace();
            return ResultGenerator.genFailResult(ex.getMessage());
        }
    }

    /**
     * 查询当前用户的文件列表（根目录）
     * */
    /*@PostMapping("/queryUserPath2")
    @CrossOrigin
    public Result queryUserPath() {
        try{
            return ResultGenerator.genSuccessResult(fileService.queryUserFilePathFromDbRecord(SecurityUtils.getSubject().getPrincipal().toString(),"/"));
        }catch (Exception ex){
            return ResultGenerator.genFailResult(ex.getMessage());
        }
    }*/

}
