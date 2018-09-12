package com.number.assignment.numberatic.db.repository;

import com.number.assignment.numberatic.db.model.Numberatic;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NumberaticRepository extends MongoRepository<Numberatic, String> {

    List<Numberatic> findAllByOrderByNumberAsc();

    List<Numberatic> findAllByOrderByNumberDesc();

    Numberatic findByNumber(Long number);

    Numberatic findTopByOrderByNumberDesc();

    Numberatic findTopByOrderByNumberAsc();

    Long deleteByNumber(Long number);

}
