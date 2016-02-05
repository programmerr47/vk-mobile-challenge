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

/**
 * VKAttachments.java
 * vk-android-sdk
 *
 * Created by Babichev Vitaly on 01.02.14.
 * Copyright (c) 2014 VK. All rights reserved.
 */
package com.vk.sdk.api.model;

import android.os.Parcel;

import com.vk.sdk.util.VKStringJoiner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.vk.sdk.api.model.VKAttachments.Type.fromStr;

/**
 * A list of attachments in {@link VKApiComment}, {@link VKApiPost}, {@link VKApiMessage}
 */
public class VKAttachments extends VKList<VKAttachments.VKApiAttachment> implements android.os.Parcelable {

    public enum Type {

        /**
         * @see com.vk.sdk.api.model.VKApiPhoto
         */
        PHOTO("photo"),

        /**
         * @see com.vk.sdk.api.model.VKApiVideo
         */
        VIDEO("video"),

        /**
         * @see com.vk.sdk.api.model.VKApiAudio
         */
        AUDIO("audio"),

        /**
         * @see com.vk.sdk.api.model.VKApiDocument
         */
        DOC("doc"),

        /**
         * @see com.vk.sdk.api.model.VKApiPost
         */
        POST("wall"),

        /**
         * @see com.vk.sdk.api.model.VKApiPostedPhoto
         */
        POSTED_PHOTO("posted_photo"),

        /**
         * @see com.vk.sdk.api.model.VKApiLink
         */
        LINK("link"),

        /**
         * @see com.vk.sdk.api.model.VKApiNote
         */
        NOTE("note"),

        /**
         * @see com.vk.sdk.api.model.VKApiPoll
         */
        POLL("poll"),

        /**
         * @see com.vk.sdk.api.model.VKApiApplicationContent
         */
        APP("app"),

        /**
         * @see com.vk.sdk.api.model.VKApiWikiPage
         */
        WIKI_PAGE("page"),

        /**
         * @see com.vk.sdk.api.model.VKApiPhotoAlbum
         */
        ALBUM("album");

        private final String typeStr;

        Type(String typeStr) {
            this.typeStr = typeStr;
        }

        public String getTypeStr() {
            return typeStr;
        }

        @Override
        public String toString() {
            return typeStr;
        }

        public static Type fromStr(String typeStr) {
            for (Type type : Type.values()) {
                if (type.typeStr.equals(typeStr)) {
                    return type;
                }
            }

            throw new IllegalStateException("There is no type for string: " + typeStr);
        }
    }


    public VKAttachments() {
        super();
    }

    public VKAttachments(VKApiAttachment... data) {
        super(Arrays.asList(data));
    }

    public VKAttachments(List<? extends VKApiAttachment> data) {
        super(data);
    }

    public VKAttachments(JSONObject from) {
        super();
        fill(from);
    }

    public VKAttachments(JSONArray from) {
        super();
        fill(from);
    }

    public void fill(JSONObject from) {
        super.fill(from, parser);
    }

    public void fill(JSONArray from) {
        super.fill(from, parser);
    }

    public String toAttachmentsString() {
        ArrayList<CharSequence> attachments = new ArrayList<CharSequence>();
        for (VKApiAttachment attach : this) {
            attachments.add(attach.toAttachmentString());
        }
        return VKStringJoiner.join(attachments, ",");
    }

    /**
     * Parser that's used for parsing photo sizes.
     */
    private static final Parser<VKApiAttachment> parser = new Parser<VKApiAttachment>() {
        @Override
        public VKApiAttachment parseObject(JSONObject attachment) throws Exception {
            Type type = fromStr(attachment.optString("type"));
            VKApiAttachment apiAttachment = initAttachmentFromType(type);

            if (apiAttachment != null) {
                apiAttachment.parse(attachment.getJSONObject(type.getTypeStr()));
            }

            return apiAttachment;
        }

        private VKApiAttachment initAttachmentFromType(Type type) {
            switch (type) {
                case ALBUM:
                    return new VKApiPhotoAlbum();
                case APP:
                    return new VKApiApplicationContent();
                case AUDIO:
                    return new VKApiAudio();
                case DOC:
                    return new VKApiDocument();
                case LINK:
                    return new VKApiLink();
                case NOTE:
                    return new VKApiNote();
                case PHOTO:
                    return new VKApiPhoto();
                case POLL:
                    return new VKApiPoll();
                case POST:
                    return new VKApiPost();
                case POSTED_PHOTO:
                    return new VKApiPostedPhoto();
                case VIDEO:
                    return new VKApiVideo();
                case WIKI_PAGE:
                    return new VKApiWikiPage();
                default:
                    return null;
            }
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(size());
        for(VKApiAttachment attachment: this) {
            dest.writeString(attachment.getType().getTypeStr());
            dest.writeParcelable(attachment, 0);
        }
    }

    public VKAttachments(Parcel parcel) {
        int size = parcel.readInt();
        for(int i = 0; i < size; i++) {
            Type type = fromStr(parcel.readString());
            Class attachmentClass = getAttachmentClassFromType(type);

            if (attachmentClass != null) {
                add((VKApiAttachment) parcel.readParcelable(attachmentClass.getClassLoader()));
            }
        }
    }

    private Class getAttachmentClassFromType(Type type) {
        switch (type) {
            case ALBUM:
                return VKApiPhotoAlbum.class;
            case APP:
                return VKApiApplicationContent.class;
            case AUDIO:
                return VKApiAudio.class;
            case DOC:
                return VKApiDocument.class;
            case LINK:
                return VKApiLink.class;
            case NOTE:
                return VKApiNote.class;
            case PHOTO:
                return VKApiPhoto.class;
            case POLL:
                return VKApiPoll.class;
            case POST:
                return VKApiPost.class;
            case POSTED_PHOTO:
                return VKApiPostedPhoto.class;
            case VIDEO:
                return VKApiVideo.class;
            case WIKI_PAGE:
                return VKApiWikiPage.class;
            default:
                return null;
        }
    }

    public static Creator<VKAttachments> CREATOR = new Creator<VKAttachments>() {
        public VKAttachments createFromParcel(Parcel source) {
            return new VKAttachments(source);
        }

        public VKAttachments[] newArray(int size) {
            return new VKAttachments[size];
        }
    };

    /**
     * An abstract class for all attachments
     */
    @SuppressWarnings("unused")
    public abstract static class VKApiAttachment extends VKApiModel implements Identifiable {

        /**
         * Convert attachment to special string to attach it to the post, message or comment.
         */
        public abstract CharSequence toAttachmentString();

        /**
         * @return type of this attachment
         */
        public abstract Type getType();
    }
}
