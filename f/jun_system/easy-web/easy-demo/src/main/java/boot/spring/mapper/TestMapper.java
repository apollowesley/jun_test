package boot.spring.mapper;

import boot.spring.po.TestVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface TestMapper {
    List<TestVo> query();

    void batchAdd(@Param("testList") List<TestVo> testList);

    Set<String> querySet();
}