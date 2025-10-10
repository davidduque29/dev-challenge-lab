package com.example.hospitalapi.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "hospitals")
public class Hospital {
/*No se usa @Entity, porque no hay tablas ni ORM.
 En su lugar, se usa @Document para decirle a Spring Data MongoDB que esta clase
 se guarda como documentos JSON dentro de una colección.*/
    @Id
    private String id;
    private String name;
    private String city;
    private double rating;

    public Hospital() {
    }

    public Hospital(String name, String city, double rating) {
        this.name = name;
        this.city = city;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
