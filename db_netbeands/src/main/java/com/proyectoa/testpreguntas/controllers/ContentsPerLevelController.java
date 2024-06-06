/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectoa.testpreguntas.controllers;


import com.proyectoa.testpreguntas.models.ContentsPerLevel;
import com.proyectoa.testpreguntas.repositories.ContentsPerLevelRepository;
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
 * @author HeyBr
 */
@RestController
@RequestMapping("/api/contents_per_level")
public class ContentsPerLevelController {
    @Autowired
    private ContentsPerLevelRepository contentsPerLevelRepository;

    @CrossOrigin
    @GetMapping
    public List<ContentsPerLevel> getAllContentsPerLevel() {
        //todo
        return contentsPerLevelRepository.findAll();
    }
    
    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<ContentsPerLevel> getContentsPerLevelByID(@PathVariable Long id) {

        Optional<ContentsPerLevel> contentsPerLevel = contentsPerLevelRepository.findById(id);
        return contentsPerLevel.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @CrossOrigin
    @PostMapping
    public ResponseEntity<ContentsPerLevel> createContentsPerLevel(@RequestBody ContentsPerLevel contentsPerLevel) {
        ContentsPerLevel savedContentsPerLevel = contentsPerLevelRepository.save(contentsPerLevel);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedContentsPerLevel);
    }
    
    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContentsPerLevel(@PathVariable Long id) {
        if (!contentsPerLevelRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        contentsPerLevelRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<ContentsPerLevel> updateContentsPerLevel(@PathVariable Long id, @RequestBody ContentsPerLevel contentsPerLevel) {
        if (!contentsPerLevelRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        contentsPerLevel.setId(id);
        ContentsPerLevel savedContentsPerLevel = contentsPerLevelRepository.save(contentsPerLevel);
        return ResponseEntity.ok(savedContentsPerLevel);
    }
}
