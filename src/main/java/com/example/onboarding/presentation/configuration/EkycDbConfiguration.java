package com.example.onboarding.presentation.configuration;

import com.example.onboarding.persistence.domain.SdkConfigEntity;
import com.example.onboarding.persistence.repository.SdkConfigRepo;
import com.example.onboarding.presentation.model.ErrorInfoSaveDb;
import com.example.onboarding.presentation.model.SdkConfigModel;
import com.example.onboarding.presentation.type.ErrorCodeEnum;
import com.example.onboarding.presentation.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class EkycDbConfiguration {
    @Autowired
    private SdkConfigRepo sdkConfigRepo;

    private List<SdkConfigModel> configModelList = new ArrayList<>();

    private Map<String, ErrorInfoSaveDb> errorInfoSaveDbMap = new HashMap<>();

    @Scheduled(initialDelay =  30 * 1000, fixedDelay = 30 * 60 * 1000)
    public void loadConfigDb(){
        getNfcPartner();
        getErrorCodeConfig();
    }

    public void getNfcPartner(){
        List<SdkConfigEntity> sdkConfigEntityList = sdkConfigRepo.findAll();
        configModelList = sdkConfigEntityList.stream().map(
                e-> {
                    SdkConfigModel sdkConfigModel = new SdkConfigModel();
                    sdkConfigModel.setId(e.getId());
                    sdkConfigModel.setPriority(e.getPriority());
                    sdkConfigModel.setPartnerName(e.getParterName());
                    sdkConfigModel.setStatus(e.getStatusNfc());
                    return sdkConfigModel;
                }
        ).collect(Collectors.toList());
        System.out.println("EkycDbConfiguration getNfcPartner: " + LogUtil.toJson(configModelList));
    }

    private void getErrorCodeConfig(){
        errorInfoSaveDbMap = Arrays.stream(ErrorCodeEnum.values())
                .collect(Collectors.toMap(
                        ErrorCodeEnum::getErrorCode,
                        errorInfo -> new ErrorInfoSaveDb(errorInfo.getErrorCode(), errorInfo.getErrorDesc())));
        System.out.println("====End EkycDbConfiguration getErrorCodeConfig errorInfoSaveDbMap: {}====" + LogUtil.toJson(errorInfoSaveDbMap));
    }

    public ErrorInfoSaveDb getErrorInfo(String errorCode){
        return errorInfoSaveDbMap.get(errorCode);
    }
}
