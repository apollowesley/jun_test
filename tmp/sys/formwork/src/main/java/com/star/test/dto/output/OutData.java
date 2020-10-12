package com.star.test.dto.output;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OutData {
    private String key;
    private Long value;
}
