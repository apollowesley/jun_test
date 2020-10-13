package boot.spring.service;

import boot.spring.po.TestVo;

import java.util.List;
import java.util.Set;

public interface TestService {
    List<TestVo> query(int pagenum, int pagesize);

    void batchAdd(List<TestVo> testList);

    Set<String> querySet();
}