package com.example.portfolio.service;


import com.example.portfolio.domain.Setting;
import com.example.portfolio.repository.SettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SettingService {
private final SettingRepository repository;


public Setting upsert(String key, String value) {
Setting s = repository.findByKeyName(key).orElseGet(() -> Setting.builder().keyName(key).build());
s.setValue(value);
return repository.save(s);
}


public String get(String key) {
return repository.findByKeyName(key).map(Setting::getValue).orElse(null);
}
}