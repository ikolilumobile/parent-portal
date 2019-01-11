package com.ikolilu.ikolilu.portal.model;

/**
 * Created by Genuis on 22/08/2018.
 */

public class ListWard {
    private String wardName;
    private String wardCode;
    private String wardImage;
    private String schoolCode;
    private boolean isSelected;

    public ListWard(String wardName, String wardCode, String wardImage, String schoolCode, boolean isSelected) {
        this.wardName = wardName;
        this.wardCode = wardCode;
        this.wardImage = wardImage;
        this.schoolCode = schoolCode;
        this.isSelected = isSelected;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }

    public String getWardCode() {
        return wardCode;
    }

    public void setWardCode(String wardCode) {
        this.wardCode = wardCode;
    }

    public String getWardImage() {
        return wardImage;
    }

    public void setWardImage(String wardImage) {
        this.wardImage = wardImage;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
