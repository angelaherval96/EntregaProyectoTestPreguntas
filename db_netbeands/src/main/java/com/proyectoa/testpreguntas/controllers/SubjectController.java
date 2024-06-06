/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectoa.testpreguntas.controllers;


import com.proyectoa.testpreguntas.models.Subject;
import com.proyectoa.testpreguntas.repositories.SubjectRepository;
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
@RequestMapping("/api/subjects")
public class SubjectController {
    
    
    @Autowired
    
    private SubjectRepository subjectRepository;
    
    @CrossOrigin
    @GetMapping
    public List<Subject> getAllSubjects(){
        //todo
        return subjectRepository.findAll();
        
    }
    
    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<Subject> getSubjectsByID(@PathVariable Long id){
        
       Optional<Subject> subject = subjectRepository.findById(id);
        
       return subject.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @CrossOrigin
    @PostMapping
    public ResponseEntity<Subject> createSubject(@RequestBody Subject subject){
        
        Subject savedSubject = subjectRepository.save(subject);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSubject);
    }
    
    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id){
       if (  !subjectRepository.existsById(id)) {
        return ResponseEntity.notFound().build();
        }
       subjectRepository.deleteById(id);
       return ResponseEntity.noContent().build(); 
    } 
    
    @CrossOrigin
    @PutMapping ("/{id}")
    public ResponseEntity<Subject> updateSubject(@PathVariable Long id, @RequestBody Subject subject) {
        
        if (!subjectRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        subject.setId(id);
        Subject savedSubject = subjectRepository.save(subject);
        return ResponseEntity.ok(savedSubject);  
    } 
}