package com.example.onboarding.persistence.repository;

import com.example.onboarding.persistence.domain.SdkConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SdkConfigRepo extends JpaRepository<SdkConfigEntity, String> {
    List<SdkConfigEntity> findAllByStatusNfcOrderByCreateDateDesc(String status);
}
