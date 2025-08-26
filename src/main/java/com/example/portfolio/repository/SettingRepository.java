package com.example.portfolio.repository;


import com.example.portfolio.domain.Setting;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;
import java.util.UUID;


public interface SettingRepository extends JpaRepository<Setting, UUID> {
Optional<Setting> findByKeyName(String keyName);
}