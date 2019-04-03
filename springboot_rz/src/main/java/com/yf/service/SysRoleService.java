package com.yf.service;

import java.util.List;

public interface SysRoleService {

    public List<String> findRoleByUserId(long userId);

}
