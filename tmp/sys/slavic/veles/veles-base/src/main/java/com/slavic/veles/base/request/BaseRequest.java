package com.slavic.veles.base.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
public class BaseRequest implements Serializable {

    private String traceId = UUID.randomUUID().toString().replaceAll("-","");

    private Date traceDate = new Date();

}
