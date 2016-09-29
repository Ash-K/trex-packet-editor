package com.xored.javafx.packeteditor.scapy;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.util.Base64;

/** This class defines the change, which needs to be applied to protocol field
 * all fields except 'id' are optional.
 * */
public class ReconstructField {
    public String id; // required
    public JsonElement value;
    public String hvalue;
    public String value_base64;
    Boolean delete; // clear field and use default value
    Boolean randomize; // set random value

    public ReconstructField(String id) { this.id = id; }

    /** sets random field value */
    public static ReconstructField randomizeValue(String fieldId) {
        ReconstructField res = new ReconstructField(fieldId);
        res.randomize = true;
        return res;
    }

    /** resets field value */
    public static ReconstructField resetValue(String fieldId) {
        ReconstructField res = new ReconstructField(fieldId);
        res.delete = true;
        return res;
    }

    /** set human-value(hvalue) and let Scapy_server to guess the value type and convert from string */
    public static ReconstructField setHumanValue(String fieldId, String hvalue) {
        ReconstructField res = new ReconstructField(fieldId);
        res.hvalue = hvalue;
        return res;
    }

    /** set field value */
    public static ReconstructField setValue(String fieldId, JsonElement value) {
        ReconstructField res = new ReconstructField(fieldId);
        res.value = value;
        return res;
    }

    /** set field value */
    public static ReconstructField setValue(String fieldId, String value) {
        return setValue(fieldId, new JsonPrimitive(value));
    }

    /** set field value */
    public static ReconstructField setValue(String fieldId, Number value) {
        return setValue(fieldId, new JsonPrimitive(value));
    }

    /** set field value */
    public static ReconstructField setValue(String fieldId, byte[] bytes) {
        ReconstructField res = new ReconstructField(fieldId);
        res.value_base64 = Base64.getEncoder().encodeToString(bytes);
        return res;
    }

    /** set raw eval python expression as a value */
    public static ReconstructField setExpressionValue(String fieldId, String expr) {
        JsonObject val = new JsonObject();
        val.add("vtype", new JsonPrimitive("expr"));
        val.add("expr", new JsonPrimitive(expr));
        return setValue(fieldId, val);
    }
}