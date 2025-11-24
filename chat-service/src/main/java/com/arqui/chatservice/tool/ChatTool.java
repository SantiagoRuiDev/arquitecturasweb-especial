package com.arqui.chatservice.tool;

import com.fasterxml.jackson.databind.JsonNode;

// Defino una interfaz con caracteristicas que comparten las herramientas.
public interface ChatTool {
    String getName();
    String getDescription();
    JsonNode getJsonSchema();
}