package com.example.demo.entity;
//추상클래스, 구현역할
import lombok.Data;

@Data
public abstract class Entity implements PrimaryKey{

    private Long id;
}
