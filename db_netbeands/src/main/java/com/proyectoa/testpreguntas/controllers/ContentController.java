/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectoa.testpreguntas.controllers;


import com.proyectoa.testpreguntas.models.Content;
import com.proyectoa.testpreguntas.repositories.ContentRepository;
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
 * ruperto envia ayuda \o/
 */

@RestController
@RequestMapping("/api/contents")
public class ContentController {
    
    
    @Autowired
    private ContentRepository contentRepository;
    
    @CrossOrigin
    @GetMapping
    public List<Content> getAllContents(){
        //todo
        return contentRepository.findAll(); 
    }
   
    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<Content> getContentsByID(@PathVariable Long id){
        
       Optional<Content> content = contentRepository.findById(id);
        
       return content.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @CrossOrigin
    @PostMapping    
    public ResponseEntity<Content> createContent(@RequestBody Content content){
        
        Content savedContent = contentRepository.save(content);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedContent);
    }
    
    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContent(@PathVariable Long id){
       if (!contentRepository.existsById(id)) {
       
        return ResponseEntity.notFound().build();
        }
       contentRepository.deleteById(id);
       return ResponseEntity.noContent().build();
    }
    
    @CrossOrigin
    @PutMapping ("/{id}")
    public ResponseEntity<Content> updateContent(@PathVariable Long id, @RequestBody Content content) {       
        if ( !contentRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }       
        content.setId(id);
        Content savedContent = contentRepository.save(content);
        return ResponseEntity.ok(savedContent);       
    } 
}