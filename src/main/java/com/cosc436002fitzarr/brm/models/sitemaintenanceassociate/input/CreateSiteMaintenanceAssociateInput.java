package com.cosc436002fitzarr.brm.models.sitemaintenanceassociate.input;

import com.cosc436002fitzarr.brm.models.user.input.CreateUserInput;

import java.util.Objects;

public class CreateSiteMaintenanceAssociateInput {
    private CreateUserInput createUserInput;
    private String siteMaintenanceManagerId;

    public CreateSiteMaintenanceAssociateInput(CreateUserInput createUserInput, String siteMaintenanceManagerId) {
        this.createUserInput = createUserInput;
        this.siteMaintenanceManagerId = siteMaintenanceManagerId;
    }

    public CreateUserInput getCreateUserInput() {
        return createUserInput;
    }

    public void setCreateUserInput(CreateUserInput createUserInput) {
        this.createUserInput = createUserInput;
    }

    public String getSiteMaintenanceManagerId() {
        return siteMaintenanceManagerId;
    }

    public void setSiteMaintenanceManagerId(String siteMaintenanceManagerId) {
        this.siteMaintenanceManagerId = siteMaintenanceManagerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateSiteMaintenanceAssociateInput that = (CreateSiteMaintenanceAssociateInput) o;
        return getCreateUserInput().equals(that.getCreateUserInput()) &&
                getSiteMaintenanceManagerId().equals(that.getSiteMaintenanceManagerId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCreateUserInput(), getSiteMaintenanceManagerId());
    }

    @Override
    public String toString() {
        return "CreateSiteMaintenanceAssociateInput{" +
                "createUserInput=" + createUserInput +
                ", siteMaintenanceManagerId='" + siteMaintenanceManagerId + '\'' +
                '}';
    }
}
