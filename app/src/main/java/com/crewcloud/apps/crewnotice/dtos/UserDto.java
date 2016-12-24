package com.crewcloud.apps.crewnotice.dtos;


import com.crewcloud.apps.crewnotice.CrewCloudApplication;
import com.crewcloud.apps.crewnotice.util.PreferenceUtilities;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserDto implements Serializable {

    @SerializedName("Id")
    public int Id;

    @SerializedName("CompanyNo")
    public int CompanyNo;

    @SerializedName("PermissionType")
    public int PermissionType;//0 normal, 1 admin

    @SerializedName("userID")
    public String userID;

    @SerializedName("FullName")
    public String FullName;

    @SerializedName("MailAddress")
    public String MailAddress;

    @SerializedName("session")
    public String session;

    @SerializedName("avatar")
    public String avatar;

    @SerializedName("nameCompany")
    public String NameCompany;

    public PreferenceUtilities prefs = CrewCloudApplication.getInstance().getPreferenceUtilities();

    public UserDto() {
        prefs = CrewCloudApplication.getInstance().getPreferenceUtilities();
    }
}
