/**
 * Copyright 2017 KiWiPeach
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package cn.kiwipeach.design.structure.facade;

import java.util.ArrayList;
import java.util.List;

/**
 * Create Date: 2017/11/19 
 * Description: 客户的开关：他希望通过一个开关的控制，控制所有子模块
 * @author kiwipeach [1099501218@qq.com]
 */
public class CustomerSwitch implements SwitchAction {

    private List<SwitchAction> switchActionList = new ArrayList<SwitchAction>();

    public List<SwitchAction> getSwitchActionList() {
        return switchActionList;
    }

    public void setSwitchActionList(List<SwitchAction> switchActionList) {
        this.switchActionList = switchActionList;
    }

    @Override
    public void startUp() {
        for(SwitchAction switchItem:switchActionList){
            switchItem.startUp();
        }
    }

    @Override
    public void shutDown() {
        for(SwitchAction switchItem:switchActionList){
            switchItem.shutDown();
        }
    }
}
