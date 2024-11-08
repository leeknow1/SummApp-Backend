package com.leeknow.summapp.ai.service;

public class GeminiAIUtil {

    public static String getURL(String key) {
        return "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=" + key;
    }

    public static String getJSON(String message) {
        return String.format("{\"contents\":[{\"parts\":[{\"text\":\"%s\"}]}]}", message);
    }
}
