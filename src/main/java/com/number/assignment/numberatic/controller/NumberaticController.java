package com.number.assignment.numberatic.controller;


import com.number.assignment.numberatic.db.model.Numberatic;
import com.number.assignment.numberatic.db.repository.NumberaticRepository;
import com.number.assignment.numberatic.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/assignment", produces = MediaType.APPLICATION_JSON_VALUE)
public class NumberaticController {

    @Autowired
    private NumberaticRepository numberaticRepository;

    @PostMapping()
    public ResponseEntity saveNumber(@RequestBody Numberatic numberatic) {
        try {
            return ResponseEntity.ok().body(numberaticRepository.save(numberatic));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(MessageUtils.NUMBER_DUPLICATE_ERROR);
        }
    }

    @GetMapping()
    public ResponseEntity getNumberList() {
        return ResponseEntity.ok().body(numberaticRepository.findAllByOrderByNumberAsc());
    }


    @GetMapping("/sort/{order}")
    public ResponseEntity getNumberList(@PathVariable("order") String order) {
        if (order.equals(MessageUtils.DESC)) {
            return ResponseEntity.ok().body(numberaticRepository.findAllByOrderByNumberDesc());
        } else if (order.equals(MessageUtils.ASC)) {
            return ResponseEntity.ok().body(numberaticRepository.findAllByOrderByNumberAsc());
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(MessageUtils.ORDER_FORMAT_ERROR);
    }


    @GetMapping("/{number}")
    public ResponseEntity getNumber(@PathVariable("number") Long number) {
        return ResponseEntity.ok().body(
                numberaticRepository.findByNumber(number));

    }


    @GetMapping("/max")
    public ResponseEntity<Numberatic> getMaxNumber() {
        return ResponseEntity.ok().body(
                numberaticRepository.findTopByOrderByNumberDesc());
    }


    @GetMapping("/min")
    public ResponseEntity<Numberatic> getMinNumber() {
        return ResponseEntity.ok().body(
                numberaticRepository.findTopByOrderByNumberAsc());
    }

    @DeleteMapping("/{number}")
    public ResponseEntity deleteByNumber(@PathVariable("number") Long number) {
        Long countOfRecord = numberaticRepository.deleteByNumber(number);
        if (countOfRecord != 0) {
            return ResponseEntity.ok().body(countOfRecord);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MessageUtils.NOT_FOUND_ERROR);
    }
    
}
