package com.sam.sam_biz.db;

import com.sam.sap_commons.utils.SysDefaults;
import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public abstract class BaseEntity {
    @Column(name = "create_time", nullable = false, updatable = false)
    private String createTime;
    
    @Column(name = "create_by", length = 50, updatable = false)
    private String createBy;
    
    @Column(name = "update_time")
    private String updateTime;
    
    @Column(name = "update_by", length = 50)
    private String updateBy;
    
    @Column(name = "is_active")
    private boolean active = true;

    @PrePersist
    protected void onCreate() {
        createTime = SysDefaults.now();
        updateTime = SysDefaults.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updateTime = SysDefaults.now();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}