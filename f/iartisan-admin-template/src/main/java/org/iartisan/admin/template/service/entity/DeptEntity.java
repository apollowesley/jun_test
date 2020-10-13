package org.iartisan.admin.template.service.entity;

import lombok.Data;

/**
 * @author King  2019-7-19
 */
@Data
public class DeptEntity {

    private String deptId;

    private String deptName;

    private String deptPath;

    private String deptParent;

    private String deptLeader;

    private String deptLeaderName;

}
