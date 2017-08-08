package com.team.newsdemo.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Android_ZzT on 17/8/8.
 */

public class NewsResponse {

    private int code;

    private String message;

    private List<New> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<New> getNews() {
        return data;
    }

    public void setNews(List<New> news) {
        this.data = news;
    }

    @Override
    public String toString() {
        return "NewsResponse{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", news=" + data +
                '}';
    }

    public static class New implements Parcelable {

        private int id;

        private String title;

        private String category;

        private String thumb_url;

        private String page_url;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getThumb_url() {
            return thumb_url;
        }

        public void setThumb_url(String thumb_url) {
            this.thumb_url = thumb_url;
        }

        public String getPage_url() {
            return page_url;
        }

        public void setPage_url(String page_url) {
            this.page_url = page_url;
        }

        public New() {}

        @Override
        public String toString() {
            return "New{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", category='" + category + '\'' +
                    ", thumb_url='" + thumb_url + '\'' +
                    ", page_url='" + page_url + '\'' +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(title);
            dest.writeString(category);
            dest.writeString(thumb_url);
            dest.writeString(page_url);
        }

        public static final Creator<New> CREATOR = new Creator<New>() {
            @Override
            public New createFromParcel(Parcel source) {
                return new New(source);
            }

            @Override
            public New[] newArray(int size) {
                return new New[0];
            }
        };

        private New(Parcel source) {
            id = source.readInt();
            title = source.readString();
            category = source.readString();
            thumb_url = source.readString();
            page_url = source.readString();
        }
    }
}
