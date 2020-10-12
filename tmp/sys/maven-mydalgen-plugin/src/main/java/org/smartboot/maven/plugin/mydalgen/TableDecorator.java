/*
 * Copyright (c) 2001, Aslak Hellesøy, BEKK Consulting
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * - Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * - Neither the name of BEKK Consulting nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */
package org.smartboot.maven.plugin.mydalgen;

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;
import org.smartboot.maven.plugin.mydalgen.predicates.column.Basic;
import org.smartboot.maven.plugin.mydalgen.predicates.column.ForeignKey;
import org.smartboot.maven.plugin.mydalgen.predicates.column.Mandatory;
import org.smartboot.maven.plugin.mydalgen.predicates.column.MandatoryBasic;
import org.smartboot.maven.plugin.mydalgen.predicates.column.MandatoryNotKey;
import org.smartboot.maven.plugin.mydalgen.predicates.column.NotKey;
import org.smartboot.maven.plugin.mydalgen.predicates.column.PrimaryKey;

/**
 * Baseclass for DbTable decorators. Subclasses can add additional
 * functionality.
 *
 * @author <a href="mailto:aslak.hellesoy@bekk.no">Aslak Helles</a>
 * @created 3. oktober 2001
 * @version $Id: TableDecorator.java,v 1.1 2005/10/25 14:59:22 lusu Exp $
 */
public class TableDecorator extends PreferenceAware implements Table {

	/**
	 * @todo-javadoc Describe the column
	 */
	private final Table _subject;
	/**
	 * @todo-javadoc Describe the column
	 */
	private Collection<Column> _columnDecorators;

	/**
	 * @todo-javadoc Describe the column
	 */
	private Column _pkColumnDecorator;

	/**
	 * @todo-javadoc Describe the column
	 */
	private boolean _isSimplePk;

	/**
	 * @todo-javadoc Describe the field
	 */
	private boolean _isGenerate = true;

	/**
	 * Describe what the DbTableDecorator constructor does
	 */
	public TableDecorator(Table subject) {
		if (!(subject instanceof DbTable)) {
			throw new IllegalArgumentException("subject must be of class " + DbTable.class.getName() + ". Was:"
				+ subject.getClass().getName());
		}

		_subject = subject;
	}

	/**
	 * Sets generation property.
	 *
	 * @param flag
	 *            The new Generate value
	 * @see #isGenerate()
	 */
	public void setGenerate(boolean flag) {
		_isGenerate = flag;
	}

	/**
	 * Indicates whether or not to generate for this table. Useful for e.g.
	 * omitting generation of tables that only serve as join tables in m:n
	 * relationships.
	 *
	 * @return The Generate value
	 */
	public boolean isGenerate() {
		return _isGenerate;
	}

	/**
	 * @return the name of the sub directory of the original directory
	 */
	public String getSubDirPath() {
		return "";
	}

	/**
	 * Gets the ReplaceName attribute of the TableDecorator object
	 *
	 * @return The ReplaceName value
	 */
	public String getReplaceName() {
		return getSqlName();
	}

	/**
	 * Gets the Name attribute of the DbTableDecorator object
	 *
	 * @return The Name value
	 */
	public final String getName() {
		return _subject.getName();
	}

	/**
	 * Gets the SqlName attribute of the DbTableDecorator object
	 *
	 * @return The SqlName value
	 */
	public final String getSqlName() {
		return _subject.getSqlName();
	}

	/**
	 * Gets the SqlName attribute of the TableDecorator object
	 *
	 * @todo-javadoc Write javadocs for method parameter
	 * @param withSchemaPrefix
	 *            Describe what the parameter does
	 * @return The SqlName value
	 */
	public final String getSqlName(boolean withSchemaPrefix) {
		return _subject.getSqlName(withSchemaPrefix);
	}

	/**
	 * Gets the SchemaPrefixedSqlName attribute of the TableDecorator object
	 *
	 * @return The SchemaPrefixedSqlName value
	 */
	public String getSchemaPrefixedSqlName() {
		return _subject.getSchemaPrefixedSqlName();
	}

	public final String getNamespace() {
		String tabName = this.getSqlName(true);
		tabName = StringUtils.upperCase(tabName);
		return StringUtils.replace(tabName, "_", "-");
	}

	/**
	 * Gets the PkTableSqlName attribute of the TableDecorator object
	 *
	 * @return The PkTableSqlName value
	 */
	public TableElement getTableElement() {
		return _subject.getTableElement();
	}

	/**
	 * Gets the SqlNameLowerCase attribute of the TableDecorator object
	 *
	 * @return The SqlNameLowerCase value
	 */
	public final String getSqlNameLowerCase() {
		return getSqlName().toLowerCase();
	}

	/**
	 * Gets the Column attribute of the TableDecorator object
	 *
	 * @todo-javadoc Write javadocs for method parameter
	 * @param sqlName
	 *            Describe what the parameter does
	 * @return The Column value
	 */
	public Column getColumn(String sqlName) {
		return getPlugin().decorate(_subject.getColumn(sqlName));
	}

	/**
	 * Gets the decorated columns
	 *
	 * @return The Columns value
	 */
	public final Collection<Column> getColumns() {
		return _columnDecorators;
	}

	/**
	 * Gets all the mandatory columns (columns that are not nullable)
	 *
	 * @return the mandatory columns
	 */
	public final Collection getMandatoryColumns() {
		return getColumns(Mandatory.getInstance());
	}

	/**
	 * Gets all the basic columns (columns that are not foreign keys)
	 *
	 * @return the basic columns
	 */
	public Collection getBasicColumns() {
		return getColumns(Basic.getInstance());
	}

	/**
	 * Gets all the foreign key columns
	 *
	 * @return the foreign key columns
	 */
	public Collection getForeignKeyColumns() {
		return getColumns(ForeignKey.getInstance());
	}

	/**
	 * Gets all the primary key columns
	 *
	 * @return the primary key columns
	 */
	public Collection getPrimaryKeyColumns() {
		return getColumns(PrimaryKey.getInstance());
	}

	/**
	 * Gets all the mandatory basic columns (columns that are not nullable and
	 * not foreign keys)
	 *
	 * @return The MandatoryBasicColumns value
	 */
	public Collection getMandatoryBasicColumns() {
		return getColumns(MandatoryBasic.getInstance());
	}

	/**
	 * Gets all the mandatory columns that are not fk or pk
	 *
	 * @return The MandatoryBasicColumns value
	 */
	public Collection getMandatoryNotKeyColumns() {
		return getColumns(MandatoryNotKey.getInstance());
	}

	/**
	 * Gets all the columns that are not fk or pk
	 *
	 * @return The MandatoryBasicColumns value
	 */
	public Collection getNotKeyColumns() {
		return getColumns(NotKey.getInstance());
	}

	/**
	 * Returns the pk column if this table has a single-column pk. Otherwise,
	 * null is returned.
	 *
	 * @return The PkColumn value
	 */
	public Column getPkColumn() {
		return _pkColumnDecorator;
	}

	/**
	 * Gets the SimplePk attribute of the Entity11DbTable object
	 *
	 * @return The SimplePk value
	 */
	public boolean isSimplePk() {
		return _isSimplePk;
	}

	/**
	 * Gets the Index attribute of the DbTableDecorator object
	 *
	 * @todo-javadoc Write javadocs for method parameter
	 * @param columnSqlName
	 *            Describe what the parameter does
	 * @return The Index value
	 */
	public int getIndex(String columnSqlName) {
		return _subject.getIndex(columnSqlName);
	}

	/**
	 * Gets the Columns attribute of the TableDecorator object
	 *
	 * @todo-javadoc Write javadocs for method parameter
	 * @param predicate
	 *            Describe what the parameter does
	 * @return The Columns value
	 */
	public Collection getColumns(Predicate predicate) {
		return CollectionUtils.select(getColumns(), predicate);
	}

	/**
	 * Describe what the method does
	 *
	 * @todo-javadoc Write javadocs for method
	 * @todo-javadoc Write javadocs for return value
	 * @return Describe the return value
	 */
	@Override
	protected final String prefsPrefix() {
		return getPlugin().getName() + "/tables/" + getSqlName();
	}

	/**
	 * Describe what the method does
	 *
	 * @todo-javadoc Write javadocs for method
	 */
	@Override
	protected void init() {
		super.init();
		// default to simple pk column if we have exactly one pk column
		_isSimplePk = getPkColumn() != null;

	}

	/**
	 * Sets the ColumnDecorators attribute of the DbTableDecorator object
	 *
	 * @param columnDecorators
	 *            The new ColumnDecorators value
	 */
	void setColumnDecorators(Collection<Column> columnDecorators) {
		_columnDecorators = columnDecorators;
	}

	/**
	 * Sets the PkColumnDecorator attribute of the DbTableDecorator object
	 *
	 * @param pkColumnDecorator
	 *            The new PkColumnDecorator value
	 */
	void setPkColumnDecorator(ColumnDecorator pkColumnDecorator) {
		_pkColumnDecorator = pkColumnDecorator;
	}
}
