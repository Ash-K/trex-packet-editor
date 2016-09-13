package com.xored.javafx.packeteditor.scapy;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class ScapyUtils {
    public static JsonObject layer(String type) {
        JsonObject res = new JsonObject();
        res.add("id", new JsonPrimitive(type));
        return res;
    }

    public static JsonArray tcpIpTemplate() {
        JsonArray payload = new JsonArray();
        payload.add(layer("Ether"));
        payload.add(layer("TCP"));
        payload.add(layer("IP"));
        return payload;
    }
}
