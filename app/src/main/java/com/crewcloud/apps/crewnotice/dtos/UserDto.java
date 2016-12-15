package com.crewcloud.apps.crewnotice.dtos;


import com.crewcloud.apps.crewnotice.CrewCloudApplication;
import com.crewcloud.apps.crewnotice.util.PreferenceUtilities;

public class UserDto {

    public int Id;

    public int CompanyNo;

    public int PermissionType;//0 normal, 1 admin

    public String userID;

    public String FullName;

    public String MailAddress;

    public String session;

    public String avatar;

    public String NameCompany;

    public PreferenceUtilities prefs = CrewCloudApplication.getInstance().getPreferenceUtilities();

    public UserDto() {
        prefs = CrewCloudApplication.getInstance().getPreferenceUtilities();
    }
}
