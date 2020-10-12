package org.durcframework.core.support;

import org.durcframework.core.Edit;
import org.durcframework.core.GridResult;
import org.durcframework.core.controller.CrudController;

public class BsgridController<Entity, Service extends Edit<Entity>> extends CrudController<Entity, Service> {

	@Override
	protected GridResult getGridResult() {
		return new BsgridResult();
	}
	
}
