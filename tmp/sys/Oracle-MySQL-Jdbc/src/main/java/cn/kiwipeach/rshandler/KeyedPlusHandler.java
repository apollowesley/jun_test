/*
 * Copyright 2018 KiWiPeach.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.kiwipeach.rshandler;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.AbstractKeyedHandler;

import java.lang.reflect.ParameterizedType;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Create Date: 2018/04/20
 * Description: 基于KeyedHandler改造办，将原返回Map<empno, Map<column, value>>
 * 改造成返回Map<empno,Employ>
 *
 * @author kiwipeach [1099501218@qq.com]
 */
public class KeyedPlusHandler<K, T> extends AbstractKeyedHandler<K, T> {

    protected final RowProcessor convert;

    protected final int columnIndex;

    protected final String columnName;

    private Class<T> targetClass = null;

    public KeyedPlusHandler(Class<T> targetClass) {
        this(new BasicRowProcessor(), 1, null, targetClass);
    }

    public KeyedPlusHandler(Class<T> targetClass, String columnName) {
        this(new BasicRowProcessor(), 1, columnName, targetClass);
    }


    private KeyedPlusHandler(RowProcessor convert, int columnIndex,
                             String columnName, Class<T> targetClass) {
        super();
        this.convert = convert;
        this.columnIndex = columnIndex;
        this.columnName = columnName;
        this.targetClass = targetClass;
    }

    @Override
    protected K createKey(ResultSet rs) throws SQLException {
        return (columnName == null) ?
                (K) rs.getObject(columnIndex) :
                (K) rs.getObject(columnName);
    }

    @Override
    protected T createRow(ResultSet rs) throws SQLException {
        return this.convert.toBean(rs, targetClass);
    }
}
