package com.example.demo;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Nasty {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private LocalDateTime votedAt;

    private String tastyOrNasty;

    @ManyToOne()
    private Food theDish;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getVotedAt() {
        return votedAt;
    }

    public void setVotedAt() {
        this.votedAt = LocalDateTime.now();
    }

    public String getTastyOrNasty() {
        return tastyOrNasty;
    }

    public void setTastyOrNasty(String tastyOrNasty) {
        this.tastyOrNasty = tastyOrNasty;
    }

    public Food getTheDish() {
        return theDish;
    }

    public void setTheDish(Food theDish) {
        this.theDish = theDish;
    }
}
