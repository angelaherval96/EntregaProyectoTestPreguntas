/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectoa.testpreguntas.controllers;


import com.proyectoa.testpreguntas.models.Grade;
import com.proyectoa.testpreguntas.repositories.GradeRepository;
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
@RequestMapping("/api/grades")
public class GradeController {
    
    
    @Autowired
    private GradeRepository gradeRepository;
    
    @CrossOrigin
    @GetMapping
    public List<Grade> getAllGrades(){
        //todo
        return gradeRepository.findAll();
    }
   
    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<Grade> getGradesByID(@PathVariable Long id){
        
       Optional<Grade> grade = gradeRepository.findById(id);
       return grade.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    } 
    
    @CrossOrigin
    @PostMapping
    public ResponseEntity<Grade> createGrade(@RequestBody Grade grade){
        
        Grade savedGrade = gradeRepository.save(grade);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGrade);
    }
    
    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrade(@PathVariable Long id){
       if (!gradeRepository.existsById(id)) {
        return ResponseEntity.notFound().build();
        }
       gradeRepository.deleteById(id);
       return ResponseEntity.noContent().build();
    } 
    
    @CrossOrigin
    @PutMapping ("/{id}")
    public ResponseEntity<Grade> updateGrade(@PathVariable Long id, @RequestBody Grade grade) {
        
        if (!gradeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        grade.setId(id);
        Grade savedGrade = gradeRepository.save(grade);
        return ResponseEntity.ok(savedGrade);
    }
}