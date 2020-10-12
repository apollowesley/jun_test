package com.foo.common.base.pojo;

import java.util.List;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Area {
	private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(Area.class);
	private String n;
	private List<Area> s;

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("n", n).add("s", s)
				.toString();
	}

	public static void main(String[] args) {
		List<Area> area = Lists.newArrayList();
		Area tmpArea = new Area();
		tmpArea.setN("北京");

		List<Area> s = Lists.newArrayList();
		Area tmpArea2 = new Area();
		tmpArea2.setN("东城区");
		s.add(tmpArea2);

		tmpArea.setS(s);
		area.add(tmpArea);

		Gson gson = new GsonBuilder().create();
		String json = gson.toJson(area);
		logger.info("json is:{}", json);
	}

	public String getN() {
		return n;
	}

	public void setN(String n) {
		this.n = n;
	}

	public List<Area> getS() {
		return s;
	}

	public void setS(List<Area> s) {
		this.s = s;
	}
}
