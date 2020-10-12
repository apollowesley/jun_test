package com.foo.common.base.alfred;

import java.util.List;

import com.foo.common.base.utils.FooUtils;
import com.google.common.base.Ascii;
import com.google.common.base.CaseFormat;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

public class AlfredScriptAction {

	private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger("alfredLogger");
	private final static String yes = "yes";

	public static void main(String[] args) throws Exception {

		String actionCommand = args[0];

		if (actionCommand.equals("generateUUID")) {
			logger.info("{}", FooUtils.generateUUID());
			return;
		} else if (actionCommand.equals("encrypt")) {
			logger.info("{}", FooUtils.encrypt(args[1]));
			return;
		} else if (actionCommand.equals("transform")) {
			String token = args[1];
			CaseFormat sourceCaseFormat;
			char firstChar = token.toCharArray()[0];

			if (token.contains("-")) {
				if (Ascii.isLowerCase(firstChar)) {
					sourceCaseFormat = CaseFormat.LOWER_HYPHEN;
				} else {
					sourceCaseFormat = null;
				}
			} else if (token.contains("_")) {
				if (Ascii.isLowerCase(firstChar)) {
					sourceCaseFormat = CaseFormat.LOWER_UNDERSCORE;
				} else {
					sourceCaseFormat = CaseFormat.UPPER_UNDERSCORE;
				}
			} else {
				if (Ascii.isLowerCase(firstChar)) {
					sourceCaseFormat = CaseFormat.LOWER_CAMEL;
				} else {
					sourceCaseFormat = CaseFormat.UPPER_CAMEL;
				}
			}
			List<CaseFormat> targetCaseFormats = ImmutableList.<CaseFormat> of(
					CaseFormat.LOWER_CAMEL, CaseFormat.LOWER_HYPHEN,
					CaseFormat.LOWER_UNDERSCORE, CaseFormat.UPPER_CAMEL,
					CaseFormat.UPPER_UNDERSCORE);

			if (sourceCaseFormat == null) {
				String result = FooUtils.generateDataWithMustache(
						ImmutableMap.<String, Object> of("isEmpty", true),
						"alfred/script-input.xml");
				logger.info(result);
				return;
			}

			List<ImmutableMap<String, Object>> items = Lists.newArrayList();
			int uid = 0;

			for (CaseFormat targetCaseFormat : targetCaseFormats) {
				items.add(ImmutableMap.<String, Object> of("uid", uid++, "arg",
						sourceCaseFormat.to(targetCaseFormat, token), "valid",
						yes));
			}

			String result = FooUtils.generateDataWithMustache(ImmutableMap
					.<String, Object> of("isEmpty", false, "items", items),
					"alfred/script-input.xml");
			logger.info(result);

			return;
		} else {

		}
		// Map<String, Object> map = ImmutableMap.<String, Object> of("arg",
		// args[0]);
		// String result = FooUtils.generateDataWithMustache(map,
		// "alfred/script-action.xml");
	}
}
