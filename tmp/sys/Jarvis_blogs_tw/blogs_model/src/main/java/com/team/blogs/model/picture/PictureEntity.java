package com.team.blogs.model.picture;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @Author: xiaokai
 * @Description:
 * @Date: 2019/5/31
 * @Version: 1.0
 */
@Data
@Entity
@NoArgsConstructor
@Table(name = "t_picture", schema = "jarvis_blog", catalog = "")
@ApiModel("图片表")
public class PictureEntity {

    @Id
    @Column
    @ApiModelProperty("主键ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Basic
    @Column(name = "file_uid")
    @ApiModelProperty("图片的UID")
    private String fileUid;

    @Basic
    @Column(name = "pick_name")
    @ApiModelProperty("图片名称")
    private String picName;

    @Basic
    @Column(name = "picture_sort_uid")
    @ApiModelProperty("所属相册分类id")
    private Long pictureSortUid;

    @Basic
    @Column(name = "picture_url")
    @ApiModelProperty("图片路径")
    private String pictureUrl;
}
