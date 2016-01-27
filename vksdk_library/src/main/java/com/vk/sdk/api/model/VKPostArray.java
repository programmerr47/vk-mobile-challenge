//
//  Copyright (c) 2014 VK.com
//
//  Permission is hereby granted, free of charge, to any person obtaining a copy of
//  this software and associated documentation files (the "Software"), to deal in
//  the Software without restriction, including without limitation the rights to
//  use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
//  the Software, and to permit persons to whom the Software is furnished to do so,
//  subject to the following conditions:
//
//  The above copyright notice and this permission notice shall be included in all
//  copies or substantial portions of the Software.
//
//  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
//  FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
//  COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
//  IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
//  CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
//

package com.vk.sdk.api.model;

import android.os.Parcel;

import com.vk.sdk.VKSdk;
import com.vk.sdk.api.methods.VKApiUsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Array of Post
 * Created by alex_xpert on 29.01.14.
 */
public class VKPostArray extends VKList<VKApiPost> {

    private List<VKApiUser> users = new ArrayList<>();
    private List<VKApiCommunity> communities = new ArrayList<>();

    public VKPostArray() {
        super();
    }

    public VKPostArray(Parcel in) {
        super(in);

        int usersSize = in.readInt();
        for(int i = 0; i < usersSize; i++) {
            users.add((VKApiUser) in.readParcelable(((Object) this).getClass().getClassLoader()));
        }

        int communitiesSize = in.readInt();
        for(int i = 0; i < communitiesSize; i++) {
            communities.add((VKApiCommunity) in.readParcelable(((Object) this).getClass().getClassLoader()));
        }
    }

    @Override
    public VKApiModel parse(JSONObject response) throws JSONException {
        fill(response, VKApiPost.class);

        JSONObject responseBody = getResponseBody(response);
        fill(responseBody, "profiles", users, new ReflectParser<>(VKApiUser.class));
        fill(responseBody, "groups", communities, new ReflectParser<>(VKApiCommunity.class));

        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);

        dest.writeInt(users.size());
        for (VKApiUser item : users) {
            dest.writeParcelable(item, flags);
        }

        dest.writeInt(communities.size());
        for (VKApiCommunity item : communities) {
            dest.writeParcelable(item, flags);
        }
    }

    private JSONObject getResponseBody(JSONObject from) {
        if (from.has("response")) {
            return getResponseBody(from.optJSONObject("response"));
        } else {
            return from;
        }
    }

    private <T> void fill(JSONObject from, String arrayTag, Collection<T> targetCollection, Parser<? extends T> creator) {
        if(from != null) {
            fill(from.optJSONArray(arrayTag), targetCollection, creator);
        }
    }

    private <T> void fill(JSONArray from, Collection<T> targetCollection, Parser<? extends T> creator) {
        if (from != null) {
            for(int i = 0; i < from.length(); i++) {
                try {
                    T object = creator.parseObject(from.getJSONObject(i));
                    if(object != null) {
                        targetCollection.add(object);
                    }
                } catch (Exception e) {
                    if (VKSdk.DEBUG)
                        e.printStackTrace();
                }
            }
        }
    }

    public static Creator<VKPostArray> CREATOR = new Creator<VKPostArray>() {
        public VKPostArray createFromParcel(Parcel source) {
            return new VKPostArray(source);
        }

        public VKPostArray[] newArray(int size) {
            return new VKPostArray[size];
        }
    };
}
