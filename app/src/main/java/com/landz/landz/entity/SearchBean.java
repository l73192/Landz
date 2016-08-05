package com.landz.landz.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lizhangfeng on 2016/7/25 0025.
 * Description:
 */
public class SearchBean implements Parcelable {

//    {
//        "id": "f661",
//            "name": "泰禾1号院",
//            "type": 1
//    },

    public String id;//商圈ID
    public String name;//楼盘名称
    public String type;// type = 0,1; resblockName(楼盘名称) type=2 ,传circleTypeCode（商圈ID）

    @Override
    public String toString() {
        return "SearchBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.type);
    }

    public SearchBean() {
    }

    protected SearchBean(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.type = in.readString();
    }

    public static final Parcelable.Creator<SearchBean> CREATOR = new Parcelable.Creator<SearchBean>() {
        @Override
        public SearchBean createFromParcel(Parcel source) {
            return new SearchBean(source);
        }

        @Override
        public SearchBean[] newArray(int size) {
            return new SearchBean[size];
        }
    };
}
