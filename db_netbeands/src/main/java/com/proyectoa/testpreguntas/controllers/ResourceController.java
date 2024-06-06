/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectoa.testpreguntas.controllers;


import com.proyectoa.testpreguntas.models.Resource;
import com.proyectoa.testpreguntas.repositories.ResourceRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
 * @author Jaime
 */

@RestController
@RequestMapping("/api/resources")
public class ResourceController {
    
    
    @Autowired
    private ResourceRepository resourceRepository;

    @CrossOrigin
    @GetMapping
    public List<Resource> getAllResources(){
        //todo
        return resourceRepository.findAll();
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<Resource> getResourcesByID(@PathVariable Long id){

       Optional<Resource> resource = resourceRepository.findById(id);
       return resource.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    } 

    @CrossOrigin
    @PostMapping
    public ResponseEntity<Resource> createResource(@RequestBody Resource resource){

        Resource savedResource = resourceRepository.save(resource);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedResource);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResource(@PathVariable Long id){
       if (!resourceRepository.existsById(id)) {
        return ResponseEntity.notFound().build();
        }
       resourceRepository.deleteById(id);
       return ResponseEntity.noContent().build();
    } 

    @CrossOrigin
    @PutMapping ("/{id}")
    public ResponseEntity<Resource> updateResource(@PathVariable Long id, @RequestBody Resource resource) {

        if ( !resourceRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        resource.setId(id);
        Resource savedResource = resourceRepository.save(resource);
        return ResponseEntity.ok(savedResource);
    } 
}