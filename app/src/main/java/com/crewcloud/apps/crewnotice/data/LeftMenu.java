package com.crewcloud.apps.crewnotice.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tunglam on 12/23/16.
 */

public class LeftMenu implements Parcelable {
    @SerializedName("DivisionNo")
    private int divisionNo;

    @SerializedName("RegUserNo")
    private String regUserNo;

    @SerializedName("RegDate")
    private String regDate;

    @SerializedName("ModUserNo")
    private int modUserNo;

    @SerializedName("ModDate")
    private String modDate;

    @SerializedName("Name")
    private String name;

    public LeftMenu() {
    }


    protected LeftMenu(Parcel in) {
        divisionNo = in.readInt();
        regUserNo = in.readString();
        regDate = in.readString();
        modUserNo = in.readInt();
        modDate = in.readString();
        name = in.readString();
    }

    public static final Creator<LeftMenu> CREATOR = new Creator<LeftMenu>() {
        @Override
        public LeftMenu createFromParcel(Parcel in) {
            return new LeftMenu(in);
        }

        @Override
        public LeftMenu[] newArray(int size) {
            return new LeftMenu[size];
        }
    };

    public int getDivisionNo() {
        return divisionNo;
    }

    public void setDivisionNo(int divisionNo) {
        this.divisionNo = divisionNo;
    }

    public String getRegUserNo() {
        return regUserNo;
    }

    public void setRegUserNo(String regUserNo) {
        this.regUserNo = regUserNo;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public int getModUserNo() {
        return modUserNo;
    }

    public void setModUserNo(int modUserNo) {
        this.modUserNo = modUserNo;
    }

    public String getModDate() {
        return modDate;
    }

    public void setModDate(String modDate) {
        this.modDate = modDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(divisionNo);
        parcel.writeString(regUserNo);
        parcel.writeString(regDate);
        parcel.writeInt(modUserNo);
        parcel.writeString(modDate);
        parcel.writeString(name);
    }
}
