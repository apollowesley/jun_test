package org.iartisan.admin.template.service.bpm.entity;

import java.util.Date;

/**
 * <p>
 *
 * @author King
 * @since 2017/4/28
 */
public class DeploymentEntity {

    private String id;

    private String name;

    private Date deploymentTime;

    private String category;

    private String tenantId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDeploymentTime() {
        return deploymentTime;
    }

    public void setDeploymentTime(Date deploymentTime) {
        this.deploymentTime = deploymentTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
