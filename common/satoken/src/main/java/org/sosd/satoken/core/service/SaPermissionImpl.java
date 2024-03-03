package org.sosd.satoken.core.service;

import cn.dev33.satoken.stp.StpInterface;

import java.util.List;

public class SaPermissionImpl implements StpInterface {

    // TODO 根据业务逻辑返回用户的角色
    @Override
    public List<String> getPermissionList(Object loginId, String s) {
        return null;
    }

    @Override
    public List<String> getRoleList(Object loginId, String s) {
        return null;
    }
}
