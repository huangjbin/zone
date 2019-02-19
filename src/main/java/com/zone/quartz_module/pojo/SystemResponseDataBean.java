package com.zone.quartz_module.pojo;

import java.util.List;

public class SystemResponseDataBean {
    private List<Integer> appIds;//	[array]	是		展开
    private Boolean includeMenu;//	[boolean]	是		展开
    private List<Long> groupScopeOrgIds;//	[array]	是		展开
    private List<Long> dataScopeOrgIds;//	[array]	是		展开
    private Boolean includeApp;//	[boolean]	是		展开
    private Long userId;//	[number]	是		展开
    private Long orgId;// 复制	[number]	是		展开
    private String token;//

    public List<Integer> getAppIds() {
        return appIds;
    }

    public void setAppIds(List<Integer> appIds) {
        this.appIds = appIds;
    }

    public Boolean getIncludeMenu() {
        return includeMenu;
    }

    public void setIncludeMenu(Boolean includeMenu) {
        this.includeMenu = includeMenu;
    }

    public List<Long> getGroupScopeOrgIds() {
        return groupScopeOrgIds;
    }

    public void setGroupScopeOrgIds(List<Long> groupScopeOrgIds) {
        this.groupScopeOrgIds = groupScopeOrgIds;
    }

    public List<Long> getDataScopeOrgIds() {
        return dataScopeOrgIds;
    }

    public void setDataScopeOrgIds(List<Long> dataScopeOrgIds) {
        this.dataScopeOrgIds = dataScopeOrgIds;
    }

    public Boolean getIncludeApp() {
        return includeApp;
    }

    public void setIncludeApp(Boolean includeApp) {
        this.includeApp = includeApp;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
