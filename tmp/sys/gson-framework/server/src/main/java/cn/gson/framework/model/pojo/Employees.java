package cn.gson.framework.model.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * Created by Mybatis Generator 2019/03/01
 */
@Data
@TableName(value = "fw_employees")
public class Employees {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "create_date", fill = FieldFill.INSERT)
    private Date createDate;

    @TableField(value = "update_date", fill = FieldFill.INSERT_UPDATE)
    private Date updateDate;

    @TableField(value = "address")
    private String address;

    @TableField(value = "city")
    private String city;

    @TableField(value = "county")
    private String county;

    @TableField(value = "email")
    private String email;

    @TableField(value = "entry_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date entryDate;

    @TableField(value = "gender")
    private String gender;

    @TableField(value = "id_code")
    private String idCode;

    @TableField(value = "job_number")
    private String jobNumber;

    @TableField(value = "leave_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date leaveDate;

    @TableField(value = "province")
    private String province;

    @TableField(value = "status")
    private String status;

    @TableField(value = "telephone")
    private String telephone;

    @TableField(value = "type")
    private String type;

    @TableField(value = "user_name")
    private String userName;

    @TableField(value = "company_id")
    private Long companyId;

    @TableField(value = "master_department_id")
    private Long masterDepartmentId;

    public static final String COL_CREATE_DATE = "create_date";

    public static final String COL_UPDATE_DATE = "update_date";

    public static final String COL_ADDRESS = "address";

    public static final String COL_CITY = "city";

    public static final String COL_COUNTY = "county";

    public static final String COL_EMAIL = "email";

    public static final String COL_ENTRY_DATE = "entry_date";

    public static final String COL_GENDER = "gender";

    public static final String COL_ID_CODE = "id_code";

    public static final String COL_JOB_NUMBER = "job_number";

    public static final String COL_LEAVE_DATE = "leave_date";

    public static final String COL_PROVINCE = "province";

    public static final String COL_STATUS = "status";

    public static final String COL_TELEPHONE = "telephone";

    public static final String COL_TYPE = "type";

    public static final String COL_USER_NAME = "user_name";

    public static final String COL_COMPANY_ID = "company_id";

    public static final String COL_MASTER_DEPARTMENT_ID = "master_department_id";
}
