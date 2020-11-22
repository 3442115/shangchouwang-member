import com.atguigu.crowd.MainMysql2000;
import com.atguigu.crowd.mapper.ProjectPOMapper;
import com.atguigu.crowd.vo.PortalProjectVO;
import com.atguigu.crowd.vo.PortalTyprVO;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest(classes = MainMysql2000.class)
public class MyBatisTest {
  @Resource
  private ProjectPOMapper projectPOMapper;
    @Test
    public void  getConnection() throws SQLException {
        List<PortalTyprVO> portalTyprVOS =
                projectPOMapper.selectPortalTypeVOList();
        for (PortalTyprVO portalTyprVO : portalTyprVOS) {
            System.out.println(portalTyprVO.getName()+"   "+portalTyprVO.getRemark());
            List<PortalProjectVO> portalProjectVOList = portalTyprVO.getPortalProjectVOList();
            for (PortalProjectVO portalProjectVO : portalProjectVOList) {
                System.out.println(portalProjectVO);
            }
        }
    }


}
