package com.slavic.hors.service;

import com.slavic.hors.orm.entity.HorsPortalLoginRecord;
import com.slavic.hors.orm.entity.HorsPortalUser;

import java.util.List;

public interface PortalUserService {

    HorsPortalUser login(String username, String password);

    HorsPortalUser findByUserName(String username);

    HorsPortalUser findById(Long id);

    List<HorsPortalUser> list(HorsPortalUser query);

    HorsPortalUser update(HorsPortalUser user);

    boolean delete(Long id);

    boolean add(HorsPortalUser user);

    void saveLoginRecord(Long userId);

    List<HorsPortalLoginRecord> nearLoginRecords(Long userId);
}
