package com.snsproj.repository;

import com.snsproj.model.entity.AlarmEntity;
import com.snsproj.model.entity.PostEntity;
import com.snsproj.model.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<AlarmEntity, Integer> {
    Page<AlarmEntity> findAllByUser(UserEntity user, Pageable pageable);
}
