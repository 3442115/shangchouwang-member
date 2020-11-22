package com.atguigu.crowd.Controller;

import com.atguigu.crowd.MYSQLremoteservice;
import com.atguigu.crowd.config.OSSProperties;
import com.atguigu.crowd.constant.CrowdConstant;
import com.atguigu.crowd.util.CrowdUtil;
import com.atguigu.crowd.util.ResultEntity;
import com.atguigu.crowd.vo.*;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProjectConsumerController {

    @Resource
    private OSSProperties ossProperties;
    @Resource
    private MYSQLremoteservice mysqLremoteservice;
    private String path;


    @RequestMapping("/get/project/detail/{projectId}")
    public String getProjectDetail(@PathVariable("projectId") Integer id,Model model){
        ResultEntity<DetailProjectVO> detailProjectRemote =
                mysqLremoteservice.getDetailProjectRemote(id);
        if (detailProjectRemote.getResult().equals(ResultEntity.SUCCESS)){
            DetailProjectVO data = detailProjectRemote.getData();
            model.addAttribute("detailProjectVO",data);
        }

        return "project-show-detail";
    }

    // 完善易付宝信息
    @RequestMapping("/create/confirm")
    public String createConfirm(HttpSession session,
                                MemberConfirmInfoVO memberConfirmInfoVO,
                                ModelMap modelMap  ){
        // 取对象
        ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);
         if (projectVO == null){
             throw new RuntimeException();
         }
         projectVO.setMemberConfirmInfoVO(memberConfirmInfoVO);
          // 获取当前登录的会员信息
          MemberLoginVO login = (MemberLoginVO) session.getAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER);
          // 获取当前会员的id
          Integer id = login.getId();
          // 调用远程方法 存入数据库
          ResultEntity<String> resultEntity=  mysqLremoteservice.saveProjectVORemote(projectVO,id);
             if (ResultEntity.FAILED.equals(resultEntity.getResult())){
                 modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,"存入失败，请稍后再试!");
                 return "project-confirm";
             }
           // 如果存储成功了就将临时对象从session中删除
           session.removeAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);
           // 成功了就跳转到成功界面
           return "redirect:http://www.abc.com/project/create/success";
    }


   // 收集回报信息
    @ResponseBody
    @RequestMapping("/create/save/return.json")
    public ResultEntity<String> saveReturnMessage(ReturnVO returnVO,HttpSession session){
        System.out.println(path);

     // 从session域中取出来旧的projectvo对象
        ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT);
         if (projectVO == null){
             return ResultEntity.failed(CrowdConstant.ATTR_NAME_TEMPROJECR_MISS);
         }

         // 从队形中获取list以后先判断一下 是否可用
        List<ReturnVO> returnVOList = projectVO.getReturnVOList();

         if( returnVOList == null || returnVOList.size()==0){
             returnVOList=new ArrayList<>();
             projectVO.setReturnVOList(returnVOList);
         }
         returnVO.setDescriptionPicPath(path);
         System.out.println(returnVO);
         // 将returnVO对象加入到returnVOList集合里
         returnVOList.add(returnVO);
         // 把有变化的projectVO对象重新存入到session里面取
        session.setAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT,projectVO);
        return ResultEntity.successWithoutData();
    }

     // JavaScript代码 ：formData.append（”returnPicture“,file）
     // returnPicture是请求参数的名字
    // file 是请求参数的值  也就是上传的文件
    // 回报图片展示
    @ResponseBody   // 这是一个ajax请求 不跳转到任何界面  则返回json
    @RequestMapping("/create/upload/return/picture.json")
    public ResultEntity<String> createUploadPicture(
                                     // 接受用户上传的文件
                                       @RequestParam("returnPicture") MultipartFile returnPicture,
                                       ModelMap  modelmap) throws IOException {
        ResultEntity<String> returnPictureEntity = CrowdUtil.uploadFileToOss(ossProperties.getEndPoint(),
                ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret(),
                returnPicture.getInputStream(),
                ossProperties.getBucketName(),
                ossProperties.getBucketDomain(),
                returnPicture.getOriginalFilename());

       path=returnPictureEntity.getData();
        return returnPictureEntity;
    }


    // 上传项目信息
    @RequestMapping("/information")
    public String saveProjectBasicInfo(// 接受除了图片以外的其他信息
                                       ProjectVO projectVO,
                                       // 接收头图     因为没写requestparam这里值需要和表单的名字一致
                                       MultipartFile headerPicture,
                                       // 接受详情图
                                       List<MultipartFile> detailPictureList,
                                       HttpSession session,
                                       // 携带信息
                                       ModelMap modelMap) throws IOException {


        boolean empty = headerPicture.isEmpty();

        if(!empty) {

            ResultEntity<String> resultEntity = CrowdUtil.uploadFileToOss(ossProperties.getEndPoint(),
                    ossProperties.getAccessKeyId(),
                    ossProperties.getAccessKeySecret(),
                     headerPicture.getInputStream(),
                    ossProperties.getBucketName(),
                    ossProperties.getBucketDomain(),
                    headerPicture.getOriginalFilename());
            String result=resultEntity.getResult();
            // 头图上传成功
            if (ResultEntity.SUCCESS.equals(result)){
                // 从返回数据过去头图访问路径
                String headerpicturePath = resultEntity.getData();
                // 将路径存入到vo对象里面
                projectVO.setHeaderPicturePath(headerpicturePath);
            }else{
                // 上传头图失败
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,"上传失败!");
                return "project-launch";
            }
            // 头图如果为空，则返回到页面 并提示信息
        }else{
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,"上传头图不可为空!");
            return "project-launch";
        }

        // 创建一个用来存放详情图片的集合
        List<String> list = new ArrayList<>();
        // 检查页面传过来的list是否有效
        if (detailPictureList == null || detailPictureList.size()==0){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,"详情图片不可为空!");
            return "project-launch";
        }
        for (MultipartFile detailPictruePath : detailPictureList) {
            if (!detailPictruePath.isEmpty()){
                // 执行上传详情图片
                ResultEntity<String> resultEntity = CrowdUtil.uploadFileToOss(
                        ossProperties.getEndPoint(),
                        ossProperties.getAccessKeyId(),
                        ossProperties.getAccessKeySecret(),
                        detailPictruePath.getInputStream(),
                        ossProperties.getBucketName(),
                        ossProperties.getBucketDomain(),
                        detailPictruePath.getOriginalFilename());
                 // 判断上传结果
                String result = resultEntity.getResult();
                // 上传结果成功
                if (ResultEntity.SUCCESS.equals(result)){
                    // 获取这个图片路径
                    String detailPath = resultEntity.getData();
                    // 获取刚刚上传的图片路径
                    list.add(detailPath);
                }else{
                    // 上传结果失败
                    modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,"上传失败!");
                    return "project-launch";
                }
                // 当前图片如果为空则提示
            }else{
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,"文件不可为空!");
                return "project-launch";
            }
        }

        // 获取到详情图片的路径后 存放进 projectvo对象
        projectVO.setDetailPictruePathList(list);
        // 将projectvo对象存入到session域  其实就是redis里卖弄等到存入到mysql里面去以后 就会删除
        session.setAttribute(CrowdConstant.ATTR_NAME_TEMPLE_PROJECT,projectVO);
        // 去下一个表单页面
                 //  走zuul
        return "redirect:http://www.abc.com/project/return/info/page";
    }



}