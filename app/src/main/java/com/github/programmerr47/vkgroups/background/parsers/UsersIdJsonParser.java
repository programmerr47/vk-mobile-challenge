package com.github.programmerr47.vkgroups.background.parsers;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael Spitsin
 * @since 2016-01-16
 */
public class UsersIdJsonParser {

    private static final String RESPONSE = "response";
    private static final String ITEMS = "items";

    public List<Integer> parse(JSONObject jsonObject) {
        JSONObject responseJsonObject = jsonObject.optJSONObject(RESPONSE);
        JSONArray idJsonArray = responseJsonObject.optJSONArray(ITEMS);

        return getIdsFromJsonArray(idJsonArray);
    }

    private List<Integer> getIdsFromJsonArray(JSONArray idsJsonArray) {
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < idsJsonArray.length(); i++) {
            result.add(idsJsonArray.optInt(i));
        }

        return result;
    }
}
