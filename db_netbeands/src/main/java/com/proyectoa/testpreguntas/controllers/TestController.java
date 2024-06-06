/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectoa.testpreguntas.controllers;

import com.proyectoa.testpreguntas.models.Test;
import com.proyectoa.testpreguntas.repositories.TestRepository;
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
@RequestMapping("/api/tests")
public class TestController {
    
    
    @Autowired
    private TestRepository testRepository;
    
    @GetMapping
    public List<Test> getAllTests(){
        //todo
        return testRepository.findAll();
    }
    
    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<Test> getTestsByID(@PathVariable Long id){
        
       Optional<Test> test = testRepository.findById(id);
       return test.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    } 
    
    @CrossOrigin
    @PostMapping
    public ResponseEntity<Test> createTest(@RequestBody Test test){
        
        Test savedTest = testRepository.save(test);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTest);
    }
    
    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTest(@PathVariable Long id){
       if (!testRepository.existsById(id)) {
        return ResponseEntity.notFound().build();
        } 
       
       testRepository.deleteById(id);
       return ResponseEntity.noContent().build();
    } 
    
    @CrossOrigin
    @PutMapping ("/{id}")
    public ResponseEntity<Test> updateTest(@PathVariable Long id, @RequestBody Test test) {
        
        if ( !testRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        test.setId(id);
        Test savedTest = testRepository.save(test);
        return ResponseEntity.ok(savedTest);
    } 
} 