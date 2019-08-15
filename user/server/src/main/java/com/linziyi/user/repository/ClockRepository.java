package com.linziyi.user.repository;

import com.linziyi.user.dataobject.Clock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClockRepository extends JpaRepository<Clock,String> {
    List<Clock> findAll();
}
