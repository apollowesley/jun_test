package com.kiss.jbpm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import com.kiss.entity.GenericEntity;


/**
 * JbpmInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="j_jbpm_info")

public class JbpmInfo extends GenericEntity{


    /**
	 * 
	 */
	private static final long serialVersionUID = -4678694321041252523L;
	// Fields    

     private String jbpmInfoId;
     private String jbpmFileName;
     private String jbpmFileId;
     private String jbpmBusinessId;

    // Constructors

    /** default constructor */
    public JbpmInfo() {
    }

	/** minimal constructor */
    public JbpmInfo(String jbpmFileName, String jbpmFileId) {
        this.jbpmFileName = jbpmFileName;
        this.jbpmFileId = jbpmFileId;
    }
    
    /** full constructor */
    public JbpmInfo(String jbpmFileName, String jbpmFileId, String jbpmBusinessId) {
        this.jbpmFileName = jbpmFileName;
        this.jbpmFileId = jbpmFileId;
        this.jbpmBusinessId = jbpmBusinessId;
    }

   
    // Property accessors
    @GenericGenerator(name="generator", strategy="uuid.hex")@Id @GeneratedValue(generator="generator")
    @Column(name="jbpm_info_id", unique=true, nullable=false, length=32)
    public String getJbpmInfoId() {
        return this.jbpmInfoId;
    }
    
    public void setJbpmInfoId(String jbpmInfoId) {
        this.jbpmInfoId = jbpmInfoId;
    }
   
    @Column(name="jbpm_file_name", nullable=false, length=100)
    public String getJbpmFileName() {
        return this.jbpmFileName;
    }
    
    public void setJbpmFileName(String jbpmFileName) {
        this.jbpmFileName = jbpmFileName;
    }
    
    @Column(name="jbpm_file_id", nullable=false, length=100)
    public String getJbpmFileId() {
        return this.jbpmFileId;
    }
    
    public void setJbpmFileId(String jbpmFileId) {
        this.jbpmFileId = jbpmFileId;
    }
    
    @Column(name="jbpm_business_id", length=100)
    public String getJbpmBusinessId() {
        return this.jbpmBusinessId;
    }
    
    public void setJbpmBusinessId(String jbpmBusinessId) {
        this.jbpmBusinessId = jbpmBusinessId;
    }

}