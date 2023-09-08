/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.consiti_crud.security.service;

import com.example.consiti_crud.security.repository.RolRepository;
import com.example.consiti_crud.security.entity.Rol;
import com.example.consiti_crud.security.enums.RolNombre;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author USER
 */
@Service
@Transactional
public class RolService {
    
    @Autowired
    private RolRepository rolRep;
    
    public Optional<Rol> getByRolNombre(RolNombre rolNombre){
        return this.rolRep.findByRolNombre(rolNombre);
    }
    
    public boolean existsByRolNombre(RolNombre rolNombre){
        return this.rolRep.existsByRolNombre(rolNombre);
    }
    
    public void save(Rol rol){
        this.rolRep.save(rol);
    }
}
