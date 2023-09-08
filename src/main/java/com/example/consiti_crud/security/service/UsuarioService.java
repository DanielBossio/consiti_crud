/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.consiti_crud.security.service;

import com.example.consiti_crud.security.repository.UsuarioRepository;
import com.example.consiti_crud.security.entity.Usuario;

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
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRep;
    
    public Optional<Usuario> getByNombreUsuario(String nombre){
        return this.usuarioRep.findByNombreUsuario(nombre);
    }
    
    public boolean existsByNombreUsuario(String nombre){
        return this.usuarioRep.existsByNombreUsuario(nombre);
    }
    
    public Optional<Usuario> getByEmail(String email){
        return this.usuarioRep.findByEmail(email);
    }
    
    public boolean existsByEmail(String email){
        return this.usuarioRep.existsByEmail(email);
    }
    
    public void save(Usuario usuario){
        this.usuarioRep.save(usuario);
    }
}
