package com.szfore.util.dataprocessor.command;

import java.util.Map;

public interface Command {
	
	Map<String, Object> execute(Map<String, Object> ma);
}
