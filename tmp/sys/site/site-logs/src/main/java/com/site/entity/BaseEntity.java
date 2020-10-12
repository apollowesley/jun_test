package com.site.entity;

import java.io.Serializable;

/**
 * @author Vincent.wang
 *
 */
public interface BaseEntity<E extends Serializable> extends Serializable {

	public E getId();

}
