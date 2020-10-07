package com.cosc436002fitzarr.brm.models;

import java.time.LocalDateTime;
import java.util.List;

public interface Entity {
    public String getId();
    public LocalDateTime getCreatedAt();
    public LocalDateTime getUpdatedAt();
    public List<EntityTrail> getEntityTrail();
    public String getPublisherId();

    public void setId(String id);
    public void setCreatedAt(LocalDateTime createdAt);
    public void setUpdatedAt(LocalDateTime updatedAt);
    public void setEntityTrail(List<EntityTrail> entityTrail);
    public void setPublisherId(String publisherId);
}
