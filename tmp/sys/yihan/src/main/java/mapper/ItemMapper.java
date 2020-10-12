package mapper;

import org.apache.ibatis.annotations.Param;
import pojo.Detail;
import pojo.Item;
import pojo.QueryVo;
import pojo.Type;

import java.util.ArrayList;
import java.util.List;

public interface ItemMapper {

    List<Type> getAllType();

    Type getTypeById(Integer id);

    void addType(Type type);

    void editType(Type type);

    void delTypeById(Integer id);

    List<Item> getAllItem();

    Integer getItemSize(@Param("vo") QueryVo vo);

    ArrayList<Item> getItemList(@Param("vo") QueryVo vo, @Param("start") Integer start);

    Item getItemById(Integer id);

    void editItemById(Item item);

    void addItem(Item item);

    void delItem(Integer id);

    List<Item> getItemByTypeId(Integer typeId);

    List<Detail> getDetailByItemId(Integer id);

    Detail getDetailById(Integer id);

    void editDetail(Detail detail);

    void addDetail(Detail detail);

    void delDetail(Integer id);

    void delDetailByItemId(Integer id);
}
