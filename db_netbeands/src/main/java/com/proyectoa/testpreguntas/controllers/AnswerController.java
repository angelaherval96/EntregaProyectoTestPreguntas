/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectoa.testpreguntas.controllers;

import com.proyectoa.testpreguntas.models.Answer;
import com.proyectoa.testpreguntas.repositories.AnswerRepository;
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
@RequestMapping("/api/answers")
public class AnswerController {
    
    @Autowired
    private AnswerRepository answerRepository;
    
    @CrossOrigin
    @GetMapping
    public List<Answer> getAllAnswers(){
        //todo
        return answerRepository.findAll();
    }
   
    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<Answer> getAnswersByID(@PathVariable Long id){
        
       Optional<Answer> answer = answerRepository.findById(id);
       return answer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @CrossOrigin
    @PostMapping
    public ResponseEntity<Answer> createAnswer(@RequestBody Answer answer){
        Answer savedAnswer = answerRepository.save(answer);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAnswer);
    }
    
    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable Long id){
       if (!answerRepository.existsById(id)) {
        return ResponseEntity.notFound().build();
        }
       answerRepository.deleteById(id);
       return ResponseEntity.noContent().build();
    }
    
    @CrossOrigin
    @PutMapping ("/{id}")
    public ResponseEntity<Answer> updateAnswer(@PathVariable Long id, @RequestBody Answer answer) {
        if (!answerRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        answer.setId(id);
        Answer savedAnswer = answerRepository.save(answer);
        return ResponseEntity.ok(savedAnswer);
    } 
}