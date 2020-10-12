package mapper;

import org.apache.ibatis.annotations.Param;
import pojo.Turnimg;

import java.util.List;
import java.util.Map;

public interface SiteMapper {

    List<Map<String, String>> getAll();

    void writeSite(@Param("key") String key, @Param("s") String s);
}
