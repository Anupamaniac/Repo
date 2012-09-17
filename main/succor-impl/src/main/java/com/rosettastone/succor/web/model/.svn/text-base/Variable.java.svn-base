package com.rosettastone.succor.web.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The {@code Variable} represents variable entity from db.
 */
@Entity
@Table(name = "variable")
public class Variable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    public Variable() {
        super();
    }

    public Variable(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
