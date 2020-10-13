package org.iartisan.admin.template.service.entity;

import lombok.Data;

import java.util.List;

/**
 * @author King  2019-7-22
 */
@Data
public class DeptTreeEntity {

    private String title;
    private String id;
    private String href;
    private boolean spread = false;
    private boolean checked = false;
    private boolean disabled = false;
    private List<DeptTreeEntity> children;
}
