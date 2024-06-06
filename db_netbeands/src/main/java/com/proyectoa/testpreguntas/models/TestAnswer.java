/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.proyectoa.testpreguntas.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 *
 * @author Angela
 */
@Entity
@Table(name="test_answers")
public class TestAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable = false)
    private Long id;
    @Column(name="tests_id",nullable = false)
    private int testsId;
    @Column(name="tests_questions_id",nullable = false)
    private int testsQuestionsId;
    @Column(name="answer_id",nullable = false)
    private int answerId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTestsId() {
        return testsId;
    }

    public void setTestsId(int testsId) {
        this.testsId = testsId;
    }
    
    public int getTestsQuestionsId() {
        return testsQuestionsId;
    }

    public void setTestsQuestionsId(int testsQuestionsId) {
        this.testsQuestionsId = testsQuestionsId;
    }
    
    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }
}


