/**
 * Copyright (c) 2015, biezhi 王爵 (biezhi.me@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.blade.web.multipart;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * HTTP multipart/form-data Request
 * 
 * @author	<a href="mailto:biezhi.me@gmail.com" target="_blank">biezhi</a>
 * @since	1.0
 */
public class FileItem {

	private String name;

	private String fileName;

	private String contentType;

	private long contentLength;

	private File file;

	private Map<String,String> headers;

	public FileItem(String fieldName, String fileName, String contentType, long contentLength, File file, Map<String,String> headers) {
		
		this.fileName = fileName;
		this.contentType = contentType;
		this.contentLength = contentLength;
		this.file = file;
		this.headers = headers;
		if (headers == null) {
			this.headers = new HashMap<String,String>();
		}
	}

	public String getName() {
		return name;
	}

	public String getFileName() {
		return fileName;
	}

	public String getContentType() {
		return contentType;
	}

	public long getContentLength() {
		return contentLength;
	}

	public File getFile() {
		return file;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

}
