package com.example.demo;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String imageurl;
    private String description;

    @Transient
    private String last5minutes;

    @OneToMany(mappedBy = "theDish")
    private Set<Tasty> tastyVotes;

    @OneToMany(mappedBy = "theDish")
    private Set<Nasty> nastyVotes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLast5minutes() {
        return last5minutes;
    }

    public void setLast5minutes(String last5minutes) {
        this.last5minutes = last5minutes;
    }

    public Set<Tasty> getTastyVotes() {
        return tastyVotes;
    }

    public void setTastyVotes(Set<Tasty> tastyVotes) {
        this.tastyVotes = tastyVotes;
    }

    public Set<Nasty> getNastyVotes() {
        return nastyVotes;
    }

    public void setNastyVotes(Set<Nasty> nastyVotes) {
        this.nastyVotes = nastyVotes;
    }
}
