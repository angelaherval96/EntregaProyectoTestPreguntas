/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.proyectoa.testpreguntas.repositories;

import com.proyectoa.testpreguntas.models.Test;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Jaime
 */
public interface TestRepository extends JpaRepository<Test,Long> {
    
}
