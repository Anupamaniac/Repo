package com.rosettastone.succor.web.model;

import javax.persistence.*;

/**
 * The {@code Level} represents level entity from db.
 */
@Entity
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

   @Column(name="level")
    private String level;


    public Level() {
        super();
    }

    public Level(String level) {
        this.level = level;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

}
