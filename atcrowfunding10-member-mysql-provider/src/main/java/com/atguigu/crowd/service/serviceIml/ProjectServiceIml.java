package com.atguigu.crowd.service.serviceIml;

import com.atguigu.crowd.mapper.*;
import com.atguigu.crowd.po.MemberConfirmInfoPO;
import com.atguigu.crowd.po.MemberLaunchInfoPO;
import com.atguigu.crowd.po.ProjectPO;
import com.atguigu.crowd.po.ReturnPO;
import com.atguigu.crowd.service.ProjectService;
import com.atguigu.crowd.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Transactional(readOnly = true)
@Service
public class ProjectServiceIml implements ProjectService {
    @Resource
    private ProjectPOMapper projectPOMapper;
    @Resource
    private ProjectItemPicPOMapper projectItemPicPOMapper;
    @Resource
    private MemberLaunchInfoPOMapper memberLaunchInfoPOMapper;
    @Resource
    private MemberConfirmInfoPOMapper memberConfirmInfoPOMapper;
    @Resource
    private ReturnPOMapper returnPOMapper;

    @Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW,rollbackFor =Exception.class )
    @Override
    public void saveProject(ProjectVO projectVO, Integer id) {

        // 创建空的project PO对象
        ProjectPO projectPO = new ProjectPO();

        // 将vo对象复制给po对象
        BeanUtils.copyProperties(projectVO,projectPO);
        projectPO.setMemberid(id);
        // 设置创建日期
        projectPO.setCreatedate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

        // 设置众筹即将开始
        projectPO.setStatus(0);

        // 为了获得projectpo自增后的主键，需要到projectpommapper.xml进行设置
        // <insert id="insertSelective" useGeneratedKeys="true" keyProperty="id"
        // 有选择地去保存
        projectPOMapper.insertSelective(projectPO);
        // 获得项目的主键
        Integer projectPOId = projectPO.getId();

        // 二、保存项目、分类的关联关系信息
          // 1、获得projectpo对象里面的typelist
        List<Integer> typeIdList = projectVO.getTypeIdList();
        projectPOMapper.insertSelect(typeIdList,projectPOId);

        // 三、保存项目、标签关联关系信息
           // 1、先获得对象里面的tagIdList信息
        List<Integer> tagIdList = projectVO.getTagIdList();
        projectPOMapper.insertSelectTag(tagIdList,projectPOId);

        // 四、保存项目中详情图片路径信息
          // 1、先获的图片路径
        List<String> detailPictruePathList = projectVO.getDetailPictruePathList();
        projectItemPicPOMapper.saveDetailPicturePath(projectPOId,detailPictruePathList);

        // 五、保存项目发起人信息
        MemberLauchInfoVO memberLauchInfoVO = projectVO.getMemberLauchInfoVO();
        // 新建PO对象并将VO对象转换为PO对象
        MemberLaunchInfoPO memberLaunchInfoPO = new MemberLaunchInfoPO();
        BeanUtils.copyProperties(memberLauchInfoVO,memberLaunchInfoPO);
        memberLaunchInfoPO.setMemberid(id);
        memberLaunchInfoPOMapper.insert(memberLaunchInfoPO);

        // 六、保存项目的回报信息
         // 获的对象信息
        List<ReturnVO> returnVOList = projectVO.getReturnVOList();
        // 将VO对象换位 PO对象
        List<ReturnPO> list=new ArrayList<>();
        for (ReturnVO returnVO : returnVOList) {
            ReturnPO returnPO=
                    new ReturnPO(null,null,returnVO.getType(),returnVO.getSupportmoney(),
                                 returnVO.getContent(),returnVO.getCount(),
                                 returnVO.getSignalpurchase(),returnVO.getPurchase(),
                                 returnVO.getFreight(),returnVO.getInvoice(),returnVO.getReturndate()
                            ,returnVO.getDescriptionPicPath()
                                 );

            list.add(returnPO);
        }

        returnPOMapper.insertSelectReturnVO(projectPOId,list);

        // 七、发起人的确认信息
        MemberConfirmInfoVO memberConfirmInfoVO = projectVO.getMemberConfirmInfoVO();
        MemberConfirmInfoPO memberConfirmInfoPO = new MemberConfirmInfoPO();
        BeanUtils.copyProperties(memberConfirmInfoVO,memberConfirmInfoPO);
        memberConfirmInfoPO.setMemberid(id);
        memberConfirmInfoPOMapper.insert(memberConfirmInfoPO);



    }

    @Override
    public List<PortalTyprVO> selectPortalTypeVOList() {

        return  projectPOMapper.selectPortalTypeVOList();
    }

    @Override
    public DetailProjectVO selectDetailProjectVO(Integer id) {
        try {
            DetailProjectVO detailProjectVO = projectPOMapper.selectDetailProjectVO(id);
            Integer status = detailProjectVO.getStatus();
            switch (status) {
                case 0:
                    detailProjectVO.setStatusText("审核中！");
                    break;
                case 1:
                    detailProjectVO.setStatusText("众筹中！");
                    break;
                case 2:
                    detailProjectVO.setStatusText("众筹成功！");
                    break;
                case 3:
                    detailProjectVO.setStatusText("已关闭！");
                    break;

                default:
                    break;
            }

            // 根据deployDate计算lastDay
            String deployDate = detailProjectVO.getDeployDate();
            Date currentDate = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parse = simpleDateFormat.parse(deployDate);
            // 获取当前时间的时间戳
            long currentDateTime = currentDate.getTime();
            // 开始时间戳
            long parseTime = parse.getTime();
            // 获得过去时间
            long time = (currentDateTime - parseTime) / 1000 / 60 / 60 / 24;
            // 获取总的天数
            Integer day = detailProjectVO.getDay();
            Integer shengYv = (int) (day - time);
            detailProjectVO.setLastDay(shengYv);

            System.out.println(detailProjectVO);
            return detailProjectVO;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
