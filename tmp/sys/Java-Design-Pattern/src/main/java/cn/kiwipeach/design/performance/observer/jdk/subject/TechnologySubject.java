/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.performance.observer.jdk.subject;

import cn.kiwipeach.design.performance.observer.modal.News;

import java.util.Observable;


/**
 * Create Date: 2017/11/21 
 * Description: 技术新闻主题
 * @author kiwipeach [1099501218@qq.com]
 */
public class TechnologySubject extends Observable{

    /**
     * 技术新闻
     */
    private News technologyNews;

    public News getTechnologyNews() {
        return technologyNews;
    }

    public void setTechnologyNews(News technologyNews) {
        this.technologyNews = technologyNews;
        subjectChanged();
    }

    private void subjectChanged(){
        setChanged();
        notifyObservers(technologyNews.getId());
    }




}
