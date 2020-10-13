package com.cjlgb.design.common.core.util;

import java.util.*;

/**
 * @author WFT
 * @date 2020/7/13
 * description:树形工具类
 */
public class TreeUtils {

    private TreeUtils(){

    }

    private static class Holder {

        private final static TreeUtils UTILS = new TreeUtils();
    }

    public static TreeUtils getInstance(){
        return Holder.UTILS;
    }

    public interface TreeEntity<T> {

        /**
         * 获取Id
         * @return java.lang.Long
         */
        Long getId();

        /**
         * 获取父级Id
         * @return java.lang.Long
         */
        Long getPid();

        /**
         * 获取排序值
         * @return java.lang.Integer
         */
        Integer getSort();

        /**
         * 获取子节点列表
         * @return java.util.List<T>
         */
        List<T> getChildren();

        /**
         * 设置子节点列表
         * @param list java.util.List<T>
         */
        void setChildren(List<T> list);

    }

    public <T extends TreeEntity<T>> List<T> build(List<T> entityList,Long rootId){
        //  排序
        entityList.sort(Comparator.comparing(TreeEntity::getSort));

        Map<Long, T> map = new HashMap<>(entityList.size());
        entityList.forEach(item -> map.put(item.getId(),item));

        List<T> result = new ArrayList<>();

        entityList.forEach(item -> {
            //  判断是否为根节点
            if (item.getPid().equals(rootId)){
                result.add(item);
            } else {
                //  获取父节点
                T parent = map.get(item.getPid());
                if (null != parent){
                    //  获取父节点的子节点列表
                    List<T> childList = parent.getChildren();
                    if (null == childList) {
                        parent.setChildren(childList = new ArrayList<>());
                    }
                    childList.add(item);
                }
            }
        });
        return result;
    }

    public <T extends TreeEntity<T>> List<T> build(List<T> list) {
        return build(list,0L);
    }
}
