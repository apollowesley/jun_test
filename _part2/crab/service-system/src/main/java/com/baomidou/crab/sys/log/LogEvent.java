package com.baomidou.crab.sys.log;

import java.io.Serializable;

import com.baomidou.crab.sys.entity.Log;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * lmax disruptor Event
 * </p>
 *
 * @author jobob
 * @since 2018-10-07
 */
@Data
@Accessors(chain = true)
public class LogEvent implements Serializable {

    private Log log;

}
