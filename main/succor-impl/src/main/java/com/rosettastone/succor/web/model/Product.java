package com.rosettastone.succor.web.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * The {@code Product} represents product entity from db.
 */
@Entity
@Table(name = "product")
public class Product implements Serializable {

    @Id
    @Column(name = "name" )
    private String name;

    @Column(name="long_name")
    private String longName;

    public Product() {
        super();
    }

    public Product(String name, String longName) {
        this.name = name;
        this.longName = longName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }
}
