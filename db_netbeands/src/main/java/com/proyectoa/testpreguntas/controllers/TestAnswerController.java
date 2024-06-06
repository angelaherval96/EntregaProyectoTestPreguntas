/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectoa.testpreguntas.controllers;



import com.proyectoa.testpreguntas.models.TestAnswer;
import com.proyectoa.testpreguntas.repositories.TestAnswerRepository;
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
@RequestMapping("/api/test_answers")
public class TestAnswerController {

    @Autowired
    private TestAnswerRepository testAnswerRepository;

    @CrossOrigin
    @GetMapping
    public List<TestAnswer> getAllTestAnswer() {
        //todo
        return testAnswerRepository.findAll();
    }
    
    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<TestAnswer> getTestAnswerByID(@PathVariable Long id) {

        Optional<TestAnswer> testAnswer = testAnswerRepository.findById(id);
        return testAnswer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @CrossOrigin
    @PostMapping
    public ResponseEntity<TestAnswer> createTestAnswer(@RequestBody TestAnswer testAnswer) {
        TestAnswer savedTestAnswer = testAnswerRepository.save(testAnswer);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTestAnswer);
    }
    
    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTestAnswer(@PathVariable Long id) {
        if (!testAnswerRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        testAnswerRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<TestAnswer> updateTestAnswer(@PathVariable Long id, @RequestBody TestAnswer testAnswer) {
        if (!testAnswerRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        testAnswer.setId(id);
        TestAnswer savedTestAnswer = testAnswerRepository.save(testAnswer);
        return ResponseEntity.ok(savedTestAnswer);
    }
}

