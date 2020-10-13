package com.cosc436002fitzarr.brm.models.user;

import com.cosc436002fitzarr.brm.enums.SiteRole;
import com.cosc436002fitzarr.brm.models.Entity;
import com.cosc436002fitzarr.brm.models.EntityTrail;
import java.time.LocalDateTime;
import java.util.List;

public class SiteAdmin extends User implements Entity {

    public SiteAdmin(String id, LocalDateTime createdAt, LocalDateTime updatedAt, List<EntityTrail> entityTrail, String publisherId,
                     SiteRole siteRole, String firstName, String lastName, String username, String email, String phone,
                     String hashPassword) {
        super(id, createdAt, updatedAt, entityTrail, publisherId, siteRole, firstName, lastName, username, email, phone, hashPassword);
    }

}
