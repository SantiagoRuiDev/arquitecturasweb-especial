package com.arqui.chatservice.tool.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ToolHandlerRegistry {

    private final List<ToolHandler> handlers;

    @Autowired
    public ToolHandlerRegistry(List<ToolHandler> handlers) {
        this.handlers = handlers;
    }

    public ToolHandler getHandler(String toolName) {
        return handlers.stream()
                .filter(h -> h.supports(toolName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Tool handler not found: " + toolName));
    }
}
