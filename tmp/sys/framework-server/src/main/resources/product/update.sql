ALTER TABLE rooms.t_room_topic MODIFY topicType VARCHAR(20) COMMENT '主题类型';
ALTER TABLE rooms.t_room_topic MODIFY topicTypeId INT(11) COMMENT '主题类型ID';

ALTER TABLE admin.t_file_mapping CHANGE mime_type mimetype VARCHAR(128) COMMENT 'MIME类型';