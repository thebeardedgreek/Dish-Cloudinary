package com.example.demo;

import org.springframework.data.repository.CrudRepository;

public interface FoodRepository extends CrudRepository<Food, Long>{
    Iterable<Food>findAllByTastyVotesInAndNastyVotesIn(Iterable<Tasty> tasties, Iterable<Nasty> nasties);
}
