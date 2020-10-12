package com.itmuch.gen.db.entity.impl;

import com.itmuch.gen.db.entity.Model;

public class BehaviorModel extends Model {
    // 行为类中的实体
    private EntityModel entityModel;

    public EntityModel getEntityModel() {
        return this.entityModel;
    }

    public void setEntityModel(EntityModel entityModel) {
        this.entityModel = entityModel;
    }
}
