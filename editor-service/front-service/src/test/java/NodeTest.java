import com.onesports.editor.FrontServiceApplication;
import com.onesports.editor.dao.EtNodeDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @program: odf-editor-system
 * @description: 节点单元测试
 * @author: xjr
 * @create: 2020-08-18 10:17
 **/
@SpringBootTest(classes = FrontServiceApplication.class)
@RunWith(SpringRunner.class)
public class NodeTest {

    @Autowired
    EtNodeDao nodeDao;

    @Test
    public void testNodeRelation() throws Exception {
        nodeDao.nodesList().forEach(System.out::println);

    }
}
