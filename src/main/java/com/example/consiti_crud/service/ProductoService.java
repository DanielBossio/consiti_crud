/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.consiti_crud.service;

import com.example.consiti_crud.entity.Producto;
import com.example.consiti_crud.repository.ProductoRepository;

import java.util.List;
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
public class ProductoService {
    
    @Autowired
    ProductoRepository productoRep;
    
    public List<Producto> list(){
        return this.productoRep.findAll();
    }
    
    public Optional<Producto> getOne(int id){
        return this.productoRep.findById(id);
    }
    
    public boolean existsById(int id){
        return this.productoRep.existsById(id);
    }
    
    public Optional<Producto> getByNombre(String nombre){
        return this.productoRep.findByNombre(nombre);
    }
    
    public boolean existsByNombre(String nombre){
        return this.productoRep.existsByNombre(nombre);
    }
    
    public void save(Producto producto){
        this.productoRep.save(producto);
    }
    
    public void delete(int id){
        this.productoRep.deleteById(id);
    }
}
