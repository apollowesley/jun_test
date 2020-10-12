package service.impl;

import mapper.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pojo.Detail;
import pojo.Item;
import pojo.QueryVo;
import pojo.Type;
import service.ItemService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper mapper;


    @Override
    public List<Type> getAllType() {
        return mapper.getAllType();
    }

    @Override
    public Type getTypeById(Integer id) {
        return mapper.getTypeById(id);
    }

    @Override
    public void editType(Type type) {
        if (type.getId() == null) {
            mapper.addType(type);
        } else {
            mapper.editType(type);
        }
    }

    @Override
    public void delTypeById(Integer id) {
        mapper.delTypeById(id);
    }

    @Override
    public List<Item> getAllItem() {
        return mapper.getAllItem();
    }

    @Override
    public Integer getItemSize(QueryVo vo) {
        return mapper.getItemSize(vo);
    }

    @Override
    public Item getItemById(Integer id) {
        return mapper.getItemById(id);
    }

    @Override
    public void itemWrite(Item item) {
        if (item.getId() != null) {
            mapper.editItemById(item);
        } else {
            mapper.addItem(item);
        }
    }

    @Override
    public void delItem(Integer id) {
        mapper.delItem(id);
    }

    @Override
    public List<Item> getItemByTypeId(Integer typeId) {
        return mapper.getItemByTypeId(typeId);
    }

    @Override
    public List<Detail> getDetailByItemId(Integer id) {
        return mapper.getDetailByItemId(id);
    }

    @Override
    public Detail getDetailById(Integer id) {
        return mapper.getDetailById(id);
    }

    @Override
    public void editDetail(Detail detail) {
        if (detail.getId() != null) {
            mapper.editDetail(detail);
        } else {
            if (detail.getType1() == 1)
                detail.setType2("标题");
            if (detail.getType1() == 2)
                detail.setType2("正文");
            if (detail.getType1() == 3)
                detail.setType2("图片");
            if (detail.getType1() == 4)
                detail.setType2("链接");
            mapper.addDetail(detail);
        }
    }

    @Override
    public void delDetail(Integer id) {
        mapper.delDetail(id);
    }

    @Override
    public void delDetailByItemId(Integer id) {
        mapper.delDetailByItemId(id);
    }


    @Override
    public ArrayList<ArrayList<Item>> getItemList(QueryVo vo) {
        Integer limit = vo.getLimitLine();
        ArrayList<ArrayList<Item>> items = new ArrayList<>();
        for (Integer i = 0; i < 2; i++) {
            Integer start = (vo.getCurrentPage() - 1) * vo.getLimitPage() + i * limit;
            ArrayList<Item> list = mapper.getItemList(vo, start);
            if (list != null) {
//                list = des(list, limit);
                items.add(list);
            }
        }
        return items;
    }


    private ArrayList<Item> des(ArrayList<Item> list, Integer limit) {
        Integer max = 1136;
        double k = 0.0;
        for (Integer i = list.size(); i < limit - 1; i++) {
            k = k + 1.0;
        }
        for (Item item : list) {
            k += (double) item.getWidth() / (double) item.getHeight();
        }
        Integer height = new Double(max / k).intValue();
        for (Item item : list) {
            double k1 = (double) item.getWidth() / (double) item.getHeight();
            item.setWidth1(new Double(k1 * height).intValue());
            item.setHeight1(height);
        }
        return list;
    }
}