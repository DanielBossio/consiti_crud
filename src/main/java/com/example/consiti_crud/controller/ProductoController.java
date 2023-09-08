/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.consiti_crud.controller;

import com.example.consiti_crud.dto.Mensaje;
import com.example.consiti_crud.dto.ProductoDto;
import com.example.consiti_crud.entity.Producto;
import com.example.consiti_crud.service.ProductoService;

import java.util.List;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author USER
 */
@RestController
@RequestMapping("/producto")
@CrossOrigin(origins = "*")
public class ProductoController {
    
    @Autowired
    private ProductoService productoService;
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("")
    public ResponseEntity<List<Producto>> findAll(){
        List<Producto> list = this.productoService.list();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int id){
        if (!this.productoService.existsById(id))
            return new ResponseEntity<>(new Mensaje("El producto solicitado no existe"), HttpStatus.NOT_FOUND);
        Producto p = this.productoService.getOne(id).get();
        return new ResponseEntity<>(p, HttpStatus.OK);
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/detail-name/{name}")
    public ResponseEntity<?> getByNombre(@PathVariable("nombre") String nombre){
        if (!this.productoService.existsByNombre(nombre))
            return new ResponseEntity<>(new Mensaje("El producto con nombre "+nombre+" no existe"), HttpStatus.NOT_FOUND);
        Producto p = this.productoService.getByNombre(nombre).get();
        return new ResponseEntity<>(p, HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("")
    public ResponseEntity<Mensaje> create(@RequestBody ProductoDto productoDto){
        String nombre = productoDto.getNombre();
        Float precio = productoDto.getPrecio();
        if (StringUtils.isBlank(nombre))
            return new ResponseEntity<>(new Mensaje("El nombre del producto es obligatorio"), HttpStatus.BAD_REQUEST);
        if (precio == null || precio < 0)
            return new ResponseEntity<>(new Mensaje("El precio debe ser mayor que 0"), HttpStatus.BAD_REQUEST);
        if (this.productoService.existsByNombre(nombre))
            return new ResponseEntity<>(new Mensaje("El nombre "+nombre+" ya se encuentra registrado"), HttpStatus.BAD_REQUEST);
        Producto p = new Producto(nombre, precio);
        this.productoService.save(p);
        return new ResponseEntity<>(new Mensaje("Producto creado con éxito"), HttpStatus.CREATED);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Mensaje> update(@PathVariable("id") int id,@RequestBody ProductoDto productoDto){
        String nombre = productoDto.getNombre();
        Float precio = productoDto.getPrecio();
        if (!this.productoService.existsById(id))
            return new ResponseEntity<>(new Mensaje("El producto no existe"), HttpStatus.NOT_FOUND);
        if (StringUtils.isBlank(nombre))
            return new ResponseEntity<>(new Mensaje("El nombre del producto es obligatorio"), HttpStatus.BAD_REQUEST);
        if (precio == null || precio < 0)
            return new ResponseEntity<>(new Mensaje("El precio debe ser mayor que 0"), HttpStatus.BAD_REQUEST);
        if (this.productoService.existsByNombre(nombre) && this.productoService.getByNombre(nombre).get().getId() != id)
            return new ResponseEntity<>(new Mensaje("El nombre "+nombre+" ya se encuentra registrado"), HttpStatus.BAD_REQUEST);
        Producto p = this.productoService.getOne(id).get();
        p.setNombre(nombre);
        p.setPrecio(precio);
        this.productoService.save(p);
        return new ResponseEntity<>(new Mensaje("Producto actualizado con éxito"), HttpStatus.OK);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Mensaje> delete(@PathVariable("id") int id){
        if (!this.productoService.existsById(id))
            return new ResponseEntity<>(new Mensaje("El producto no existe"), HttpStatus.NOT_FOUND);
        this.productoService.delete(id);
        return new ResponseEntity<>(new Mensaje("Producto eliminado"), HttpStatus.OK);
    }
}
