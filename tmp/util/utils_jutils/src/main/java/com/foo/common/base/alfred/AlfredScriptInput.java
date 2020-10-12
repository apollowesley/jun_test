package com.foo.common.base.alfred;

import java.util.List;
import java.util.Set;

import com.foo.common.base.utils.FooUtils;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class AlfredScriptInput {

	private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger("alfredLogger");
	private final static String yes = "yes";
	// private final static String no = "no";

	public static void main(String[] args) throws Exception {

		// all possible methods.
		List<ImmutableMap<String, Object>> items = Lists.newArrayList();

		int uid = 0;
		items.add(ImmutableMap.<String, Object> of("uid", uid++, "arg",
				"generateUUID", "valid", yes));
		items.add(ImmutableMap.<String, Object> of("uid", uid++, "arg",
				"formatDouble", "valid", yes));
		items.add(ImmutableMap.<String, Object> of("uid", uid++, "arg",
				"encrypt", "valid", yes));

		if (args == null || args.length < 1 || args[0].equals("")) {
			String result = FooUtils.generateDataWithMustache(ImmutableMap
					.<String, Object> of("isEmpty", false, "items", items),
					"alfred/script-input.xml");
			logger.info(result);
			return;
		} else {
			String token = args[0];
			Set<String> possibleMethodNames = Sets.newHashSet();
			possibleMethodNames.add("generateUUID");
			possibleMethodNames.add("formatDouble");
			possibleMethodNames.add("encrypt");
			if (possibleMethodNames.contains(token)) {
				String result = FooUtils.generateDataWithMustache(
						ImmutableMap.<String, Object> of("isEmpty", true),
						"alfred/script-input.xml");
				logger.info(result);
				return;
			} else {
				String result = FooUtils.generateDataWithMustache(
						ImmutableMap.<String, Object> of("isEmpty", true),
						"alfred/script-input.xml");
				logger.info(result);
				return;
			}
		}
	}
}
