package org.smartboot.maven.plugin.mydalgen.plugins.mybatis.operation;

import org.smartboot.maven.plugin.mydalgen.Table;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.IWalletOperation;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.config.IWalletOperationConfig;

public class IWalletDelete extends IWalletOperation {

	public static final String OP_TYPE = "delete";

	/**
	 * Constructor for IWalletDelete.
	 */
	public IWalletDelete(IWalletOperationConfig conf) {
		super(conf);

		if (PARAM_TYPE_OBJECT.equals(conf.getParamType())) {
			paramType = PARAM_TYPE_OBJECT;
		} else {
			// default
			paramType = PARAM_TYPE_PRIMITIVE;
		}

		multiplicity = MULTIPLICITY_ONE;
	}

	/**
	 * @return
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.plugins.mybatis.Operation#getReturnType()
	 */
	public String getReturnType() {
		return "int";
	}

	/**
	 * @return
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.plugins.mybatis.Operation#getSimpleReturnType()
	 */
	public String getSimpleReturnType() {
		return "int";
	}

	/**
	 * @return
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.plugins.mybatis.Operation#getTemplateSuffix()
	 */
	public String getTemplateSuffix() {
		return OP_TYPE;
	}

	/**
	 * @param t
	 *
	 * @see org.smartboot.maven.plugin.mydalgen.plugins.mybatis.IWalletOperation#setTable(org.smartboot.maven.plugin.mydalgen.Table)
	 */
	@Override
	public void setTable(Table t) {
		super.setTable(t);
	}

	/**
	 * @return
	 */
	public String getMappedStatementType() {
		return OP_TYPE;
	}
}
