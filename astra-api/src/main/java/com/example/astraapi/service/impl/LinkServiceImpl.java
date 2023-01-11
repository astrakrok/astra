package com.example.astraapi.service.impl;

import com.example.astraapi.meta.LinkSource;
import com.example.astraapi.service.LinkService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class LinkServiceImpl implements LinkService {
    @Override
    public LinkSource getSource(String link) {
        if (StringUtils.startsWithAny(link, "https://тестування.укр/", "https://xn--80adi8aaufcj8j.xn--j1amh/")) {
            return LinkSource.TESTING_UKR;
        }
        return LinkSource.UNKNOWN;
    }
}
