package com.Fabrica.TelcoNova.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Fabrica.TelcoNova.model.UserGroupModel;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroupModel, Long> {
    Long deleteByGroupIdAndUserId(Long groupId, Long userId);
}