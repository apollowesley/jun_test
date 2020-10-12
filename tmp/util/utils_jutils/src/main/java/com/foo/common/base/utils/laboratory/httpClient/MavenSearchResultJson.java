package com.foo.common.base.utils.laboratory.httpClient;

import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.foo.common.base.utils.FooUtils;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MavenSearchResultJson {

	static final Logger logger = LoggerFactory
			.getLogger(MavenSearchResultJson.class);

	class ResponseHeader {

	}

	class Docs {
		private String latestVersion;

		public String getLatestVersion() {
			return latestVersion;
		}

		public void setLatestVersion(String latestVersion) {
			this.latestVersion = latestVersion;
		}

	}

	class Response {
		private int numFound;
		private List<Docs> docs;

		public int getNumFound() {
			return numFound;
		}

		public void setNumFound(int numFound) {
			this.numFound = numFound;
		}

		public List<Docs> getDocs() {
			return docs;
		}

		public void setDocs(List<Docs> docs) {
			this.docs = docs;
		}
	}

	public String getVersion() {
		Preconditions.checkArgument(response.getDocs().size() >= 1);
		return response.getDocs().get(0).getLatestVersion();
	}

	private ResponseHeader responseHeader;
	private Response response;

	public static void main(String[] args) throws IOException {

		MavenSearchResultJson object = new MavenSearchResultJson();
		Gson gson = new GsonBuilder().serializeNulls().create();

		String source = FileUtils.readLines(FooUtils.getTestLogFile()).get(0);

		logger.info("From source of:{}", source);

		object = gson.fromJson(source, MavenSearchResultJson.class);

		logger.info("result:{}", object.getResponse().getDocs().get(0)
				.getLatestVersion());

	}

	public ResponseHeader getResponseHeader() {
		return responseHeader;
	}

	public void setResponseHeader(ResponseHeader responseHeader) {
		this.responseHeader = responseHeader;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

}
