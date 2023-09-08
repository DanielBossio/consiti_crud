/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.example.consiti_crud.security.repository;

import com.example.consiti_crud.security.entity.Rol;
import com.example.consiti_crud.security.enums.RolNombre;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author USER
 */
@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    
    Optional<Rol> findByRolNombre (RolNombre rolNombre);
    
    boolean existsByRolNombre (RolNombre rolNombre);
}
