/**
 * 
 */
package com.laycms.base;

import java.io.Serializable;

import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

/**
 * @author <p>Innate Solitary 于 2012-5-24 下午2:51:41</p>
 *
 */
public class EntityPersistInterceptor extends EmptyInterceptor {

	/**
     * 
     */
    private static final long serialVersionUID = 8387713433754766374L;

	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
		return super.onSave(entity, id, state, propertyNames, types);
	}
    
    
  
}
