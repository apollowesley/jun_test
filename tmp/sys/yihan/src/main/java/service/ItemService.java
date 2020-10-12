package service;

import pojo.Detail;
import pojo.Item;
import pojo.QueryVo;
import pojo.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface ItemService {
    List<Type> getAllType();

    Type getTypeById(Integer id);

    void editType(Type type);

    void delTypeById(Integer id);

    List<Item> getAllItem();

    ArrayList<ArrayList<Item>> getItemList(QueryVo vo);

    Integer getItemSize(QueryVo vo);

    Item getItemById(Integer id);

    void itemWrite(Item item);

    void delItem(Integer id);

    List<Item> getItemByTypeId(Integer typeId);

    List<Detail> getDetailByItemId(Integer id);

    Detail getDetailById(Integer id);

    void editDetail(Detail detail);

    void delDetail(Integer id);

    void delDetailByItemId(Integer id);
}
