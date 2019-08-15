package com.linziyi.user.service.impl;

import com.linziyi.user.dataobject.Clock;
import com.linziyi.user.repository.ClockRepository;
import com.linziyi.user.service.ClockService;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClockServiceImpl implements ClockService {
    @Autowired
    private ClockRepository clockRepository;
    @Override
    public List<Clock> findAll() {
        return this.clockRepository.findAll();
    }
}
