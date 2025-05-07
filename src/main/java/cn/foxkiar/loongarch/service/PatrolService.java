package cn.foxkiar.loongarch.service;

import cn.foxkiar.loongarch.mapper.PatrolMapper;
import org.springframework.stereotype.Service;

@Service
public class PatrolService {
    final PatrolMapper patrolMapper;

    public PatrolService(PatrolMapper patrolMapper) {
        this.patrolMapper = patrolMapper;
    }
}
