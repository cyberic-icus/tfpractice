package com.example.Shop.services;

import com.example.Shop.db.entities.UserRelatedEntities.RoleEntity;
import com.example.Shop.db.repos.RoleEntityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleEntityService {

    final private RoleEntityRepository roleEntityRepository;

    public RoleEntityService(RoleEntityRepository roleEntityRepository) {
        this.roleEntityRepository = roleEntityRepository;
    }

    public void saveRole(RoleEntity roleEntity) {
        if (roleEntity != null) {
            roleEntityRepository.save(roleEntity);
        }
    }

    public void deleteRoleById(Long roleId) {
        Optional<RoleEntity> roleEntity = roleEntityRepository.findById(roleId);
        if (roleEntity.isPresent()) {
            roleEntityRepository.deleteById(roleId);
        }
    }

    public void updateRole(RoleEntity oldRole, RoleEntity newRole) {
        oldRole.setName(newRole.getName());
    }

    public List<RoleEntity> getRoleEntityAll() {
        return (List<RoleEntity>) roleEntityRepository.findAll();
    }

    public Optional<RoleEntity> getRoleEntityById(Long roleId) {
        return roleEntityRepository.findById(roleId);
    }
}
