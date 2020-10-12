/*
 * Copyright (c) 2001, Aslak Hellesy, BEKK Consulting
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
package org.smartboot.maven.plugin.mydalgen.javax;

import java.sql.Types;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * This class has static methods for mapping SQL types to Java types. Both
 * supported and preferred types are supported.
 *
 * @author <a href="mailto:aslak.hellesoy@bekk.no">Aslak Helles</a>
 * @created 3. oktober 2001
 * @todo mark non-serializable types with a warning. they will call an exception
 *       in cmr (and maybe other cases too) (at least on wls 6.1). This is
 *       InputStream, Reader, Clob, Blob, Array, Ref (at least I think it's a
 *       problem. Have to test it)
 * @version $Id: Sql2Java.java,v 1.1 2005/10/25 14:59:22 lusu Exp $
 */
public class Sql2Java {
	// see resultset.gif (25 SQL types)
	// see java.sql.Types (28 SQL types in JDK1.3, 30 SQL types in JDK1.4)
	// JDK 1.3 java.sql.Types missing from table: DISTINCT, NULL, OTHER
	// JDK 1.4 java.sql.Types missing from table: DISTINCT, NULL, OTHER,
	// DATALINK, BOOLEAN

	/**
	 * @todo-javadoc Describe the column
	 */
	private static final IntStringMap _preferredJavaTypeForSqlType = new IntStringMap();

	/**
	 * @todo-javadoc Describe the column
	 */
	private static final Comparator<String> _typeComparator = new Comparator<String>() {
		public int compare(String s1, String s2) {
			boolean isS1Class = s1.indexOf('.') != -1;
			boolean isS2Class = s2.indexOf('.') != -1;
			if (isS1Class && isS2Class || !isS1Class && !isS2Class) {
				// Both are class or both are primitive. Compare normally
				return s1.compareTo(s2);
			} else {
				// One is primitive and one is class. Primitive always first
				return isS1Class ? 1 : -1;
			}
		}
	};

	/**
	 * @todo-javadoc Describe the column
	 */
	private static final HashMap<String, String> _primitiveToClassMap = new HashMap<String, String>();

	/**
	 * @todo-javadoc Describe the field
	 */
	private static final Set<String> _numericClasses = new HashSet<String>();

	/**
	 * Gets the TypeComparator attribute of the Sql2Java object
	 *
	 * @return The TypeComparator value
	 */
	public static Comparator<String> getTypeComparator() {
		return _typeComparator;
	}

	/**
	 * Gets the preferred Java Type for an SQL type. It has special logic for
	 * handling DECIMAL and NUMERICs with zero decimal places to return the most
	 * apppropriate type.
	 */
	public static String getPreferredJavaType(int sqlType, int size, int decimalDigits) {
		if ((sqlType == Types.DECIMAL || sqlType == Types.NUMERIC) && decimalDigits == 0) {
			if (size == 1) {
				// https://sourceforge.net/tracker/?func=detail&atid=415993&aid=662953&group_id=36044
				return "boolean";
			} else if (size < 3) {
				return "byte";
			} else if (size < 5) {
				return "short";
			} else if (size < 10) {
				return "int";
			} else if (size < 19) {
				return "long";
			} else {
				return "java.math.BigDecimal";
			}
		}
		String result = _preferredJavaTypeForSqlType.getString(sqlType);
		if (result == null) {
			result = "java.lang.Object";
		}
		return result;
	}

	/**
	 * Gets the preferred Java Type for non-key columns. It has special coding
	 * for handling DECIMAL and NUMERICs with zero decimal places.
	 *
	 * @todo-javadoc Write javadocs for method parameter
	 * @todo-javadoc Write javadocs for method parameter
	 * @param sqlType
	 *            The SQL type code. Must be one of java.sql.Types
	 * @param size
	 *            Describe what the parameter does
	 * @param decimalDigits
	 *            Describe what the parameter does
	 * @return The Java Type for this non-key column
	 * @author Jonathan O'Connor
	 * @created 03 April 2002
	 */
	public static String getPreferredJavaTypeNoPrimitives(int sqlType, int size, int decimalDigits) {
		String pjt = getPreferredJavaType(sqlType, size, decimalDigits);
		String pjtc = getClassForPrimitive(pjt);
		if (pjtc != null) {
			return pjtc;
		} else {
			return pjt;
		}
	}

	/**
	 * Gets the ClassForPrimitive attribute of the Sql2Java class
	 *
	 * @todo-javadoc Write javadocs for method parameter
	 * @param primitive
	 *            Describe what the parameter does
	 * @return The ClassForPrimitive value
	 */
	public static String getClassForPrimitive(String primitive) {
		return _primitiveToClassMap.get(primitive);
	}

	/**
	 * Gets the Primitive attribute of the Sql2Java class
	 *
	 * @todo-javadoc Write javadocs for method parameter
	 * @param type
	 *            Describe what the parameter does
	 * @return The Primitive value
	 */
	public static boolean isPrimitive(String type) {
		return getClassForPrimitive(type) != null;
	}

	/**
	 * Gets the NumericClass attribute of the Sql2Java class
	 *
	 * @todo-javadoc Write javadocs for method parameter
	 * @param type
	 *            Describe what the parameter does
	 * @return The NumericClass value
	 */
	public static boolean isNumericClass(String type) {
		return _numericClasses.contains(type);
	}

	/**
	 * Describe what this class does
	 *
	 * @author Aslak Helles
	 * @created 24. mars 2002
	 * @todo-javadoc Write javadocs
	 */
	private static class IntStringMap extends HashMap<Integer, String> {

		/** */
		private static final long serialVersionUID = 4848369595562226459L;

		/**
		 * Gets the String attribute of the IntStringMap object
		 *
		 * @todo-javadoc Write javadocs for method parameter
		 * @param i
		 *            Describe what the parameter does
		 * @return The String value
		 */
		public String getString(int i) {
			return get(new Integer(i));
		}

		/**
		 * Describe what the method does
		 *
		 * @todo-javadoc Write javadocs for method
		 * @todo-javadoc Write javadocs for method parameter
		 * @todo-javadoc Write javadocs for method parameter
		 * @param i
		 *            Describe what the parameter does
		 * @param s
		 *            Describe what the parameter does
		 */
		public void put(int i, String s) {
			put(new Integer(i), s);
		}

	}

	static {
		_primitiveToClassMap.put("byte", "java.lang.Byte");
		_primitiveToClassMap.put("short", "java.lang.Short");
		_primitiveToClassMap.put("int", "java.lang.Integer");
		_primitiveToClassMap.put("long", "java.lang.Long");
		_primitiveToClassMap.put("float", "java.lang.Float");
		_primitiveToClassMap.put("boolean", "java.lang.Boolean");
		_primitiveToClassMap.put("double", "java.lang.Double");
	}

	static {
		_numericClasses.add("java.lang.Byte");
		_numericClasses.add("java.lang.Short");
		_numericClasses.add("java.lang.Integer");
		_numericClasses.add("java.lang.Long");
		_numericClasses.add("java.lang.Float");
		_numericClasses.add("java.lang.Boolean");
		_numericClasses.add("java.lang.Double");
	}

	static {
		_preferredJavaTypeForSqlType.put(Types.TINYINT, "java.lang.Byte");
		_preferredJavaTypeForSqlType.put(Types.SMALLINT, "java.lang.Short");
		_preferredJavaTypeForSqlType.put(Types.INTEGER, "java.lang.Integer");
		_preferredJavaTypeForSqlType.put(Types.BIGINT, "java.lang.Long");
		_preferredJavaTypeForSqlType.put(Types.REAL, "java.lang.Float");
		_preferredJavaTypeForSqlType.put(Types.FLOAT, "java.lang.Double");
		_preferredJavaTypeForSqlType.put(Types.DOUBLE, "java.lang.Double");
		_preferredJavaTypeForSqlType.put(Types.DECIMAL, "java.math.BigDecimal");
		_preferredJavaTypeForSqlType.put(Types.NUMERIC, "java.math.BigDecimal");
		_preferredJavaTypeForSqlType.put(Types.BIT, "java.lang.Boolean");
		_preferredJavaTypeForSqlType.put(Types.CHAR, "java.lang.String");
		_preferredJavaTypeForSqlType.put(Types.VARCHAR, "java.lang.String");
		// according to resultset.gif, we should use java.io.Reader, but String
		// is more convenient for EJB
		_preferredJavaTypeForSqlType.put(Types.LONGVARCHAR, "java.lang.String");
		_preferredJavaTypeForSqlType.put(Types.BINARY, "byte[]");
		_preferredJavaTypeForSqlType.put(Types.VARBINARY, "byte[]");
		_preferredJavaTypeForSqlType.put(Types.LONGVARBINARY, "java.io.InputStream");
		_preferredJavaTypeForSqlType.put(Types.DATE, "java.sql.Date");
		_preferredJavaTypeForSqlType.put(Types.TIME, "java.sql.Time");
		_preferredJavaTypeForSqlType.put(Types.TIMESTAMP, "java.sql.Timestamp");
		_preferredJavaTypeForSqlType.put(Types.CLOB, "java.sql.Clob");
		_preferredJavaTypeForSqlType.put(Types.BLOB, "java.sql.Blob");
		_preferredJavaTypeForSqlType.put(Types.ARRAY, "java.sql.Array");
		_preferredJavaTypeForSqlType.put(Types.REF, "java.sql.Ref");
		_preferredJavaTypeForSqlType.put(Types.STRUCT, "java.lang.Object");
		_preferredJavaTypeForSqlType.put(Types.JAVA_OBJECT, "java.lang.Object");
	}

}
