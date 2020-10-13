CREATE TABLE cuckoo_client_job_detail
(
	id                             bigint          NOT NULL AUTO_INCREMENT	COMMENT '��׼ID',
	job_class_application          varchar(50)     DEFAULT ''         NOT NULL	COMMENT '��ҵִ��Ӧ����',
	cuckoo_client_ip               varchar(30)     DEFAULT ''         NOT NULL	COMMENT 'ִ����IP',
	cuckoo_client_tag              varchar(128)    DEFAULT ''         NOT NULL	COMMENT '�ͻ��˱�ʶ',
	cuckoo_client_status           varchar(10)     DEFAULT ''         NOT NULL	COMMENT '�ͻ���״̬',
	job_name                       varchar(100)    DEFAULT ''         NOT NULL	COMMENT '��������',
	bean_name                      varchar(256)    DEFAULT ''         NOT NULL	COMMENT 'ʵ��������',
	method_name                    varchar(100)    DEFAULT ''         NOT NULL	COMMENT '��������',
	create_date                    decimal(13,0)   DEFAULT 0          NOT NULL	COMMENT '����ʱ��',
	modify_date                    decimal(13,0)   DEFAULT 0          NOT NULL	COMMENT '�޸�ʱ��',
PRIMARY KEY(id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin
COMMENT='�ͻ�������ע���'
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT;
CREATE UNIQUE INDEX uk_clientjob ON cuckoo_client_job_detail(job_class_application ASC ,cuckoo_client_tag ASC ,job_name ASC );
CREATE INDEX idx_clientjob_jobname ON cuckoo_client_job_detail(job_name ASC );





CREATE TABLE cuckoo_job_dependency
(
	id                             bigint          NOT NULL AUTO_INCREMENT	COMMENT '��׼ID',
	job_id                         bigint          DEFAULT 0          NOT NULL	COMMENT '����ID',
	dependency_job_id              bigint          DEFAULT 0          NOT NULL	COMMENT '��������ID',
PRIMARY KEY(id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin
COMMENT='�ϼ�����������'
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT;
CREATE INDEX idx_jobdependency_jobid ON cuckoo_job_dependency(job_id ASC );
CREATE INDEX idx_jobdependency_depid ON cuckoo_job_dependency(dependency_job_id ASC );



CREATE TABLE cuckoo_job_detail
(
	id                             bigint          NOT NULL AUTO_INCREMENT	COMMENT '��׼ID',
	group_id                       bigint          DEFAULT 0          NOT NULL	COMMENT '����ID',
	exec_job_type                  varchar(10)     DEFAULT ''         NOT NULL	COMMENT '��������',
	job_class_application          varchar(50)     DEFAULT ''         NOT NULL	COMMENT '��ҵִ��Ӧ����',
	job_name                       varchar(100)    DEFAULT ''         NOT NULL	COMMENT '��������',
	job_desc                       varchar(500)    DEFAULT ''         NOT NULL	COMMENT '��������',
	trigger_type                   varchar(10)     DEFAULT ''         NOT NULL	COMMENT '��������',
	type_daily                     varchar(6)      DEFAULT ''         NOT NULL	COMMENT '�Ƿ�Ϊ��������',
	cron_expression                varchar(20)     DEFAULT ''         NOT NULL	COMMENT 'cron������ʽ',
	offset                         int             DEFAULT 0          NOT NULL	COMMENT 'ƫ����',
	job_status                     varchar(10)     DEFAULT ''         NOT NULL	COMMENT '����״̬',
	cuckoo_parallel_job_args       varchar(256)    DEFAULT ''         NOT NULL	COMMENT '����/��Ⱥ�������',
PRIMARY KEY(id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin
COMMENT='�����'
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT;
CREATE INDEX idx_jobdetail_groupid ON cuckoo_job_detail(group_id ASC );
CREATE INDEX idx_jobdetail_app ON cuckoo_job_detail(job_class_application ASC );
CREATE INDEX idx_jobdetail_name ON cuckoo_job_detail(job_name ASC );



CREATE TABLE cuckoo_job_exec_log
(
	id                             bigint          NOT NULL AUTO_INCREMENT	COMMENT '��׼ID',
	job_id                         bigint          DEFAULT 0          NOT NULL	COMMENT '����ID',
	group_id                       bigint          DEFAULT 0          NOT NULL	COMMENT '����ID',
	exec_job_type                  varchar(10)     DEFAULT ''         NOT NULL	COMMENT '��������',
	job_class_application          varchar(50)     DEFAULT ''         NOT NULL	COMMENT '��ҵִ��Ӧ����',
	job_name                       varchar(100)    DEFAULT ''         NOT NULL	COMMENT '��������',
	trigger_type                   varchar(10)     DEFAULT ''         NOT NULL	COMMENT '��������',
	type_daily                     varchar(6)      DEFAULT ''         NOT NULL	COMMENT '�Ƿ�Ϊ��������',
	cron_expression                varchar(20)     DEFAULT ''         NOT NULL	COMMENT 'cron������ʽ',
	tx_date                        int             DEFAULT 0          NOT NULL	COMMENT '����ִ��ҵ������',
	flow_last_time                 bigint          DEFAULT 0          NOT NULL	COMMENT '��ʽ������һ��ʱ�����',
	flow_cur_time                  bigint          DEFAULT 0          NOT NULL	COMMENT '��ʽ����ǰʱ�����',
	cuckoo_parallel_job_args       varchar(256)    DEFAULT ''         NOT NULL	COMMENT '����/��Ⱥ�������',
	job_start_time                 bigint          DEFAULT 0          NOT NULL	COMMENT '����ʼʱ��',
	job_exec_time                  bigint          DEFAULT 0          NOT NULL	COMMENT '����ִ��ʱ��',
	job_end_time                   bigint          DEFAULT 0          NOT NULL	COMMENT '�������ʱ��',
	exec_job_status                varchar(10)     DEFAULT ''         NOT NULL	COMMENT 'ִ��״̬',
	cuckoo_client_ip               varchar(30)     DEFAULT ''         NOT NULL	COMMENT 'ִ����IP',
	cuckoo_client_port             int             DEFAULT 0          NOT NULL	COMMENT '�ͻ���port',
	latest_check_time              bigint          DEFAULT 0          NOT NULL	COMMENT '������ʱ��',
	need_triggle_next              boolean         DEFAULT 1          NOT NULL	COMMENT '�Ƿ񴥷��¼�����',
	force_triggle                  boolean         DEFAULT 1          NOT NULL	COMMENT '�Ƿ�ǿ�ƴ���',
	remark                         varchar(500)    DEFAULT ''         NOT NULL	COMMENT '��ע',
PRIMARY KEY(id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin
COMMENT='����ִ����ˮ��'
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT;
CREATE INDEX idx_joblog_jobid ON cuckoo_job_exec_log(job_id ASC );
CREATE INDEX idx_joblog_groupid ON cuckoo_job_exec_log(group_id ASC );
CREATE INDEX idx_joblog_starttime ON cuckoo_job_exec_log(job_start_time ASC );
CREATE INDEX idx_joblog_endtime ON cuckoo_job_exec_log(job_end_time ASC );



CREATE TABLE cuckoo_job_extend
(
	job_id                         bigint          DEFAULT 0          NOT NULL	COMMENT '����ID',
	email_list                     varchar(2000)   DEFAULT ''         NOT NULL	COMMENT '�ʼ��б��ŷָ�',
	over_time_long                 bigint          DEFAULT 0          NOT NULL	COMMENT '�ʼ���ʱʱ������(����)',
PRIMARY KEY(job_id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin
COMMENT='����������Ϣ'
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT;


CREATE TABLE cuckoo_job_group
(
	id                             bigint          NOT NULL AUTO_INCREMENT	COMMENT '��׼ID',
	group_name                     varchar(100)    DEFAULT ''         NOT NULL	COMMENT '��������',
	group_desc                     varchar(500)    DEFAULT ''         NOT NULL	COMMENT '��������',
PRIMARY KEY(id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin
COMMENT='��������'
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT;



CREATE TABLE cuckoo_job_next_job
(
	id                             bigint          NOT NULL AUTO_INCREMENT	COMMENT '��׼ID',
	job_id                         bigint          DEFAULT 0          NOT NULL	COMMENT '����ID',
	next_job_id                    bigint          DEFAULT 0          NOT NULL	COMMENT '�¼�����ID',
PRIMARY KEY(id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin
COMMENT='�¼����񴥷���'
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT;
CREATE UNIQUE INDEX uk_cuckoo_next_job ON cuckoo_job_next_job(next_job_id ASC );
CREATE INDEX idx_jobnext_jobid ON cuckoo_job_next_job(job_id ASC );


-- ����ͨ�Ź����

CREATE TABLE cuckoo_net_client_info
(
	id                             bigint          NOT NULL AUTO_INCREMENT	COMMENT '��׼ID',
	server_id                      bigint          DEFAULT 0          NOT NULL	COMMENT '������ID',
	ip                             varchar(30)     DEFAULT ''         NOT NULL	COMMENT 'IP��ַ',
	port                           int             DEFAULT 0          NOT NULL	COMMENT '�˿ں�',
	modify_date                    bigint          DEFAULT 0          NOT NULL	COMMENT '�޸�ʱ��',
	client_tag                     varchar(64)     DEFAULT ''         NOT NULL	COMMENT '�ͻ��˱�ʶ',
PRIMARY KEY(id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin
COMMENT='�ͻ�����Ϣ��'
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT;
CREATE INDEX idx_netclient_serverid ON cuckoo_net_client_info(server_id ASC );




CREATE TABLE cuckoo_net_client_job_map
(
	id                             bigint          NOT NULL AUTO_INCREMENT	COMMENT '��׼ID',
	client_id                      bigint          DEFAULT 0          NOT NULL	COMMENT '�ͻ���ID',
	regist_id                      bigint          DEFAULT 0          NOT NULL	COMMENT '����ע��ID',
PRIMARY KEY(id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin
COMMENT='�ͻ�����ע�����������'
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT;
CREATE UNIQUE INDEX idx_clientjob_clientid_registid ON cuckoo_net_client_job_map(client_id ASC ,regist_id ASC );
CREATE INDEX idx_clientjob_regiestid ON cuckoo_net_client_job_map(regist_id ASC );


CREATE TABLE cuckoo_net_regist_job
(
	id                             bigint          NOT NULL AUTO_INCREMENT	COMMENT '��׼ID',
	job_class_application          varchar(50)     DEFAULT ''         NOT NULL	COMMENT '��ҵִ��Ӧ����',
	job_name                       varchar(100)    DEFAULT ''         NOT NULL	COMMENT '��������',
	bean_name                      varchar(256)    DEFAULT ''         NOT NULL	COMMENT 'ʵ��������',
	method_name                    varchar(100)    DEFAULT ''         NOT NULL	COMMENT '��������',
	create_date                    decimal(13,0)   DEFAULT 0          NOT NULL	COMMENT '����ʱ��',
	modify_date                    decimal(13,0)   DEFAULT 0          NOT NULL	COMMENT '�޸�ʱ��',
PRIMARY KEY(id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin
COMMENT='����ע���'
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT;
CREATE INDEX idx_clientjob_jobname ON cuckoo_net_regist_job(job_name ASC );
CREATE UNIQUE INDEX uk_clientjob_app_jobname ON cuckoo_net_regist_job(job_class_application ASC ,job_name ASC );

CREATE TABLE cuckoo_net_server_info
(
	id                             bigint          NOT NULL AUTO_INCREMENT	COMMENT '��׼ID',
	ip                             varchar(30)     DEFAULT ''         NOT NULL	COMMENT 'IP��ַ',
	port                           int             DEFAULT 0          NOT NULL	COMMENT '�˿ں�',
	modify_date                    decimal(13,0)   DEFAULT 0          NOT NULL	COMMENT '�޸�ʱ��',
PRIMARY KEY(id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin
COMMENT='�������Ϣ��'
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT;
CREATE UNIQUE INDEX uk_netserver_ip_port ON cuckoo_net_server_info(ip ASC ,port ASC );


CREATE TABLE cuckoo_net_server_job_map
(
	id                             bigint          NOT NULL AUTO_INCREMENT	COMMENT '��׼ID',
	server_id                      bigint          DEFAULT 0          NOT NULL	COMMENT '������ID',
	regist_id                      bigint          DEFAULT 0          NOT NULL	COMMENT '����ע��ID',
PRIMARY KEY(id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin
COMMENT='�������ע�����������'
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT;
CREATE INDEX idx_serverjob_serverid_registid ON cuckoo_net_server_job_map(server_id ASC ,regist_id ASC );
CREATE INDEX idx_serverjob_registid ON cuckoo_net_server_job_map(regist_id ASC );


-- Ȩ����֤��
CREATE TABLE cuckoo_auth_user
(
	id                             bigint          NOT NULL AUTO_INCREMENT	COMMENT '��׼ID',
	user_name                      varchar(64)     DEFAULT ''         NOT NULL	COMMENT '�û�����',
	user_pwd                       varchar(100)    DEFAULT ''         NOT NULL	COMMENT '�û�����',
	user_auth_type                 varchar(8)      DEFAULT ''         NOT NULL	COMMENT '�û�Ȩ������',
	phone                          varchar(20)     DEFAULT ''         NOT NULL	COMMENT '�绰',
	email                          varchar(32)     DEFAULT ''         NOT NULL	COMMENT '�ʼ�',
	org_name                       varchar(100)    DEFAULT ''         NOT NULL	COMMENT '��������',
PRIMARY KEY(id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin
COMMENT='�û���'
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT;
CREATE UNIQUE INDEX uk_authuser_name ON cuckoo_auth_user(user_name ASC );


CREATE TABLE cuckoo_auth_jobgrp
(
	id                             bigint          NOT NULL AUTO_INCREMENT	COMMENT '��׼ID',
	group_id                       bigint          DEFAULT 0          NOT NULL	COMMENT '����ID',
	user_id                        bigint          DEFAULT 0          NOT NULL	COMMENT '�û�ID',
	writable                       varchar(3)      DEFAULT ''         NOT NULL	COMMENT '��д',
	readable                       varchar(3)      DEFAULT ''         NOT NULL	COMMENT '�ɶ�',
	grantable                      varchar(3)      DEFAULT ''         NOT NULL	COMMENT '�ɷ���',
PRIMARY KEY(id)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_bin
COMMENT='����Ȩ�޿��Ʊ�'
AUTO_INCREMENT=1
ROW_FORMAT=COMPACT;
CREATE INDEX idx_authjobgrp_groupid ON cuckoo_auth_jobgrp(group_id ASC );
CREATE INDEX idx_authjobgrp_userid ON cuckoo_auth_jobgrp(user_id ASC );




