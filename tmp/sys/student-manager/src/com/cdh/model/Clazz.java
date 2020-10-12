package com.cdh.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Clazz {
    private Integer claId;
    private String claName;
    private String claInfo;
    private LocalDateTime createTime;
}
