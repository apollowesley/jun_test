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

import java.util.SortedMap;
import java.util.TreeMap;

import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.util.LogUtils;

/**
 * This class is a simple implementation of the *real* Prefs class to make it
 * possible to compile with JDK 1.3 and older. It uses _prefsMap to store user
 * preferences. <br>
 * The current implementation of Prefs for the JDK 1.3 (and less) flattens the
 * XML representation of the _prefsMap. It works as long as you don't need any
 * ordering and don't use children with the same names.
 *
 * @author <a href="mailto:aslak.hellesoy@bekk.no">Aslak Helles</a>
 * @author <a href="mailto:ludovicc@users.sourceforge.net>Ludovic Claude</a>
 * @created June 6, 2002
 */
public class Prefs {

	/**
	 * @todo-javadoc Describe the field
	 */
	private SortedMap<String, String> _prefsMap;

	/**
	 * @todo-javadoc Describe the field
	 */
	private static Prefs _instance;

	/**
	 * Description of the Method
	 */
	public void set(String pathName, String key, String value) {
		_prefsMap.put(toProperty(pathName, key), value);
	}

	/**
	 * Description of the Method
	 *
	 * @param pathName
	 *            Description of the Parameter
	 * @param key
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String get(String pathName, String key) {
		return _prefsMap.get(toProperty(pathName, key));
	}

	/**
	 * Description of the Method
	 *
	 * @param pathName
	 *            Description of the Parameter
	 * @param key
	 *            Description of the Parameter
	 * @param defaultValue
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public String get(String pathName, String key, String defaultValue) {
		String value = get(pathName, key);
		if (value == null) {
			value = defaultValue;
		}
		return value;
	}

	/**
	 * Description of the Method
	 *
	 * @todo-javadoc Write javadocs for exception
	 * @param prefsDir
	 *            Description of the Parameter
	 * @param prefsId
	 *            Description of the Parameter
	 * @exception MiddlegenException
	 *                Describe the exception
	 * @throws Exception
	 *             Description of the Exception
	 */
	public void init() throws MiddlegenException {
		_prefsMap = new TreeMap<String, String>();
	}

	/**
	 * Describe what the method does
	 */
	private String toProperty(String pathName, String key) {
		String property = pathName.replace('/', '.');
		if (!property.endsWith(".")) {
			property += ".";
		}
		property += key;
		return property;
	}

	/**
	 * Gets the _instance
	 *
	 * @return The _instance value
	 */
	public static Prefs getInstance() {
		if (_instance == null) {
			_instance = new Prefs();
			try {
				_instance.init();
			} catch (Exception ex) {
				LogUtils.error(ex.getMessage(), ex);
			}
		}

		return _instance;
	}

}
