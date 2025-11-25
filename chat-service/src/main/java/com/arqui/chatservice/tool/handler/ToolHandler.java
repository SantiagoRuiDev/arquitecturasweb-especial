package com.arqui.chatservice.tool.handler;

import java.util.Map;

public interface ToolHandler {
    boolean supports(String toolName);

    Map<String, Object> execute(Map<String, Object> args);
}