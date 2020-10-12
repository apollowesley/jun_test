package com.mini.jdbc.generator;

import java.util.Map;

public interface IDialectMapping {
	Map<String, Class<?>> getMapping();
}
