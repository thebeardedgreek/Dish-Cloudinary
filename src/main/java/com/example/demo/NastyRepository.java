package com.example.demo;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;

public interface NastyRepository extends CrudRepository<Nasty, Long>{
    Iterable<Nasty> findAllByTheDish_IdAndVotedAtAfter(Long id, LocalDateTime theTime);
}
