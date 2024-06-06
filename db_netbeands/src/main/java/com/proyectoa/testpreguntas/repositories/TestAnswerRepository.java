/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.proyectoa.testpreguntas.repositories;


import com.proyectoa.testpreguntas.models.TestAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Angela
 */
public interface TestAnswerRepository extends JpaRepository<TestAnswer,Long> {
    
}
