package boot.spring.service.impl;

import boot.spring.mapper.TestMapper;
import boot.spring.po.TestVo;
import boot.spring.service.TestService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 5)
@Service("testservice")
public class TestServiceImpl implements TestService {

    @Autowired
    public TestMapper testMapper;

    @Override
    public List<TestVo> query(int pagenum, int pagesize) {
        PageHelper.startPage(pagenum, pagesize);
        List<TestVo> list = testMapper.query();
        return list;
    }

    @Override
    @Transactional(timeout = 180)
    public void batchAdd(List<TestVo> listTest) {
        testMapper.batchAdd(listTest);
    }

    @Override
    public Set<String> querySet() {
        return testMapper.querySet();
    }
}
