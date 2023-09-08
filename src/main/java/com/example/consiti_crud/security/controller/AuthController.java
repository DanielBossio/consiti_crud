/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.consiti_crud.security.controller;

import com.example.consiti_crud.dto.Mensaje;
import com.example.consiti_crud.security.dto.JwtDto;
import com.example.consiti_crud.security.dto.LoginUsuario;
import com.example.consiti_crud.security.dto.NuevoUsuario;
import com.example.consiti_crud.security.entity.Rol;
import com.example.consiti_crud.security.entity.Usuario;
import com.example.consiti_crud.security.enums.RolNombre;
import com.example.consiti_crud.security.jwt.JwtProvider;
import com.example.consiti_crud.security.service.RolService;
import com.example.consiti_crud.security.service.UsuarioService;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author USER
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private AuthenticationManager authManager;
    
    @Autowired
    private UsuarioService uService;
    
    @Autowired
    private RolService rService;
    
    @Autowired
    private JwtProvider jwtProvider;
    
    @PostMapping("")
    public ResponseEntity<Mensaje> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingRes){
        if (bindingRes.hasErrors())
            return new ResponseEntity<> (new Mensaje ("Verifique los datos introducidos"), HttpStatus.BAD_REQUEST);
        if (this.uService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario()))
            return new ResponseEntity<> (new Mensaje ("El nombre "+nuevoUsuario.getNombreUsuario()+" ya se encuentra registrado"), HttpStatus.BAD_REQUEST);
        if (this.uService.existsByEmail(nuevoUsuario.getEmail()))
            return new ResponseEntity<> (new Mensaje ("El email "+nuevoUsuario.getEmail()+" ya se encuentra registrado"), HttpStatus.BAD_REQUEST);
        Usuario u = new Usuario (nuevoUsuario.getNombre(), nuevoUsuario.getNombreUsuario(), nuevoUsuario.getEmail(), this.passwordEncoder.encode(nuevoUsuario.getPassword()));
        Set<Rol> roles = new HashSet<>();
        roles.add(this.rService.getByRolNombre(RolNombre.ROLE_USER).get());
        if (nuevoUsuario.getRoles().contains("admin"))
            roles.add(this.rService.getByRolNombre(RolNombre.ROLE_ADMIN).get());
        u.setRoles(roles);
        this.uService.save(u);
        return new ResponseEntity<> (new Mensaje("Usuario registrado con éxito"), HttpStatus.CREATED);
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginUsuario loginUsr, BindingResult bindingRes){
        if (bindingRes.hasErrors())
            return new ResponseEntity<> (new Mensaje ("Usuario inválido"), HttpStatus.UNAUTHORIZED);
        Authentication auth = this.authManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsr.getNombreUsuario(), loginUsr.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = this.jwtProvider.generateToken(auth);
        JwtDto dto = new JwtDto(jwt);
        return new ResponseEntity<> (dto, HttpStatus.ACCEPTED);
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<JwtDto> refresh(@RequestBody JwtDto dto) throws ParseException {
        String token = this.jwtProvider.refreshToken(dto);
        return new ResponseEntity<> (new JwtDto(token), HttpStatus.OK);
    }
}
