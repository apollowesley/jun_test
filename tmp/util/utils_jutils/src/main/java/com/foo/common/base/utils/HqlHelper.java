package com.foo.common.base.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Splitter;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class HqlHelper {
	private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(HqlHelper.class);

	public static void main(String[] args) {
		// String queryHql = "select new Map( sum(priceSumIncludeTax) as id, subject as subject, createTime as createTime) from SysAnnounce where 1=1 ";
		String queryHql = "select Map(a.id,a.name,b.brandId,b.productId) from ShopBrand as a,SysProductType b where a.id=b.aid ";

		String tableStatement = StringUtils.substringBetween(queryHql, "from",
				"where");
		String mappingStatement = StringUtils.substringBetween(queryHql, "Map",
				"from");
		mappingStatement = StringUtils.replaceOnce(mappingStatement, "(", "");

		Set<String> estimateTransformFields = Sets.newHashSet();
		for (String t : Splitter.on(",").split(mappingStatement)) {
			t = t.trim();
			estimateTransformFields.add(Splitter.on("as").trimResults()
					.omitEmptyStrings().limit(2).splitToList(t).get(0));
		}

		boolean hasAlias = true;
		String singleEntityName = "";
		Set<String> estimateTableNames = Sets.newHashSet();
		BiMap<String, String> entityName2aliasBimap = HashBiMap.create();
		String separator;
		List<String> tList;
		for (String t : Splitter.on(",").split(tableStatement)) {
			t = t.trim();
			if (t.contains("as")) {
				separator = "as";
			} else {
				separator = " ";
			}
			tList = Splitter.on(separator).trimResults().omitEmptyStrings()
					.limit(2).splitToList(t);

			if (tList.size() == 1) {
				singleEntityName = tList.get(0);
				hasAlias = false;
				break;
			}

			entityName2aliasBimap.put(tList.get(0), tList.get(1));
			estimateTableNames.add(Splitter.on(separator).trimResults()
					.omitEmptyStrings().limit(2).splitToList(t).get(0));
		}
		BiMap<String, String> alias2EntityNameBimap = entityName2aliasBimap
				.inverse();

		logger.info("estimateTransformFields is:{}", estimateTransformFields);
		logger.info("estimateTableNames is:{}", estimateTableNames);
		logger.info("entityName2aliasBimap is:{}", entityName2aliasBimap);

		Map<String, Set<String>> estimateResultMap = Maps.newHashMap();
		String tmpAlias;
		String tmpFiledNameWithoutAlias;
		String tmpEntityName;
		if (hasAlias) {
			for (String field : estimateTransformFields) {
				tmpAlias = field.substring(0, 1);
				tmpFiledNameWithoutAlias = field.substring(2);
				tmpEntityName = alias2EntityNameBimap.get(tmpAlias);
				if (estimateResultMap.get(tmpEntityName) != null) {
					estimateResultMap.get(tmpEntityName)
							.add(tmpFiledNameWithoutAlias);
				} else {
					estimateResultMap.put(tmpEntityName,
							Sets.newHashSet(tmpFiledNameWithoutAlias));
				}
			}
		} else {
			estimateResultMap.put(singleEntityName, estimateTransformFields);
		}
		logger.info("estimateResultMap is:{}", estimateResultMap);
	}
}
