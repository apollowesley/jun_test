package com.slavic.hors.service;

import com.slavic.hors.orm.entity.HorsPortalResource;

import java.util.List;

public interface PortalResourceService {

    List<HorsPortalResource> list(HorsPortalResource query);

}
