/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectoa.testpreguntas.controllers;


import com.proyectoa.testpreguntas.models.TestsQuestion;
import com.proyectoa.testpreguntas.repositories.TestsQuestionRepository;
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
 * @author Angela
 */

@RestController
@RequestMapping("/api/tests_questions")
public class TestsQuestionController {

    @Autowired
    private TestsQuestionRepository testsQuestionRepository;

    @CrossOrigin
    @GetMapping
    public List<TestsQuestion> getAllTestsQuestion() {
        //todo
        return testsQuestionRepository.findAll();
    }
    
    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<TestsQuestion> getTestsQuestionByID(@PathVariable Long id) {

        Optional<TestsQuestion> testsQuestion = testsQuestionRepository.findById(id);
        return testsQuestion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @CrossOrigin
    @PostMapping
    public ResponseEntity<TestsQuestion> createTestsQuestion(@RequestBody TestsQuestion testsQuestion) {
        TestsQuestion savedTestsQuestion = testsQuestionRepository.save(testsQuestion);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTestsQuestion);
    }
    
    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTestsQuestion(@PathVariable Long id) {
        if (!testsQuestionRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        testsQuestionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<TestsQuestion> updateTestsQuestion(@PathVariable Long id, @RequestBody TestsQuestion testsQuestion) {
        if (!testsQuestionRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        testsQuestion.setId(id);
        TestsQuestion savedTestsQuestion = testsQuestionRepository.save(testsQuestion);
        return ResponseEntity.ok(savedTestsQuestion);
    }
}

