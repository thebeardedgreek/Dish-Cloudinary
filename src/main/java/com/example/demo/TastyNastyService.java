package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class TastyNastyService {

    @Autowired
    TastyRepository tasties;

    @Autowired
    NastyRepository nasties;

    public String last5MinuteResult (Long id){
        String tastyOrNasty = "";
        ArrayList<Tasty> dishTastyVotes = (ArrayList)tasties.findAllByTheDish_IdAndVotedAtAfter(id, LocalDateTime.now().minusMinutes(5));
        System.out.println("Tasty Count: " +dishTastyVotes.size());
        ArrayList <Nasty> dishNastyVotes = (ArrayList<Nasty>)nasties.findAllByTheDish_IdAndVotedAtAfter(id, LocalDateTime.now().minusMinutes(5));
        System.out.println("Nasty Count: " +dishNastyVotes.size());
        if(dishTastyVotes.size()>dishNastyVotes.size()){
            tastyOrNasty = "tasty";
        }
        else if(dishTastyVotes.size()==dishNastyVotes.size())
        {
            tastyOrNasty = "undefined";
        }
        else
            tastyOrNasty = "nasty";
        System.out.println(tastyOrNasty);
        return tastyOrNasty;
    }

}
