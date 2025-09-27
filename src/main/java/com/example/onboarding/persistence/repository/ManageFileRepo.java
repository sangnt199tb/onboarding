package com.example.onboarding.persistence.repository;

import com.example.onboarding.persistence.domain.ManageFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManageFileRepo extends JpaRepository<ManageFileEntity, String> {
    ManageFileEntity findFirstById(String id);
}
