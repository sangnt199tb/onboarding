package com.example.onboarding.presentation.service.impl;

import com.example.onboarding.persistence.domain.SdkConfigEntity;
import com.example.onboarding.persistence.repository.SdkConfigRepo;
import com.example.onboarding.presentation.model.SdkConfigModel;
import com.example.onboarding.presentation.service.SdkConfigService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SdkConfigServiceImpl implements SdkConfigService {
    private final SdkConfigRepo sdkConfigRepo;

    @Autowired
    public SdkConfigServiceImpl(SdkConfigRepo sdkConfigRepo) {
        this.sdkConfigRepo = sdkConfigRepo;
    }

    @Override
    public boolean saveSdk(SdkConfigModel sdkConfigModel) {
        SdkConfigEntity entity = new SdkConfigEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setParterName(sdkConfigModel.getPartnerName());
        entity.setDesctiption(sdkConfigModel.getDescription());
        entity.setStatusNfc(sdkConfigModel.getStatus());
        entity.setPriority(sdkConfigModel.getPriority());
        entity.setCreateDate(new Timestamp(System.currentTimeMillis()));
        entity.setRequestLimit(sdkConfigModel.getRequestLimit());
        entity.setRequestQuantity(sdkConfigModel.getRequestQuantity());
        entity = sdkConfigRepo.save(entity);
        return entity != null ? true : false;
    }

    @Override
    public List<SdkConfigModel> findAllSdk() {
        List<SdkConfigEntity> list = sdkConfigRepo.findAll();
        List<SdkConfigModel> configModelList = list.stream().map(
                e-> {
                    SdkConfigModel sdkConfigModel = new SdkConfigModel();
                    sdkConfigModel.setId(e.getId());
                    sdkConfigModel.setPriority(e.getPriority());
                    sdkConfigModel.setPartnerName(e.getParterName());
                    sdkConfigModel.setStatus(e.getStatusNfc());
                    return sdkConfigModel;
                }
        ).collect(Collectors.toList());
        return configModelList;
    }

    @Override
    @Async("onboardingAsync")
    public void testAsync() {
        try {
            System.out.println("====Start SdkConfigServiceImpl testAsync====");
            long startTime = System.currentTimeMillis();
            Thread.sleep(30000);
            long endTime = System.currentTimeMillis();
            System.out.println("====End SdkConfigServiceImpl testAsync with timeCall: ====" + String.valueOf(endTime - startTime));
        } catch (Exception e){

        }
    }

    @Override
    public boolean checkStatusSdk(String sdkCode, HttpServletRequest httpServletRequest) {
        System.out.println("====Start SdkConfigServiceImpl checkStatusSdk with: " + sdkCode);
        List<SdkConfigEntity> list = sdkConfigRepo.findAll();

        return list.stream()
                .filter(e -> StringUtils.equalsIgnoreCase(e.getParterName(), sdkCode))
                .anyMatch(e -> e.getStatusNfc().equalsIgnoreCase("ACTIVE"));
    }

    @Override
    public List<String> getAllListSdkByStatus(String status, HttpServletRequest httpServletRequest) {
        System.out.println("====Start SdkConfigServiceImpl getAllListSdkByStatus with status: " + status);
        List<SdkConfigEntity> sdkConfigEntityList = sdkConfigRepo.findAll();
        return sdkConfigEntityList.stream()
                .filter(e->StringUtils.equalsIgnoreCase(e.getStatusNfc(), status))
                .map(SdkConfigEntity::getParterName)
                .collect(Collectors.toList());
    }
}
