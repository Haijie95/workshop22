package com.haijie.workshop22.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.haijie.workshop22.repository.rsvpRepo;

import java.util.List;

import com.haijie.workshop22.model.RSVP;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("")
public class rsvpRestController {

    @Autowired
    rsvpRepo rsvpRepo;

    //task 2
    //get all the rsvp
    @GetMapping(path="api/rsvps")
    public ResponseEntity<List<RSVP>> getRSVP(){
        List<RSVP> rList = rsvpRepo.listAll();

        if (rList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(rList, HttpStatus.OK);
        }
    }

    //search rsvp by name
    //GET /api/rsvp?q=fred
    @GetMapping(path="api/rsvp")
    public ResponseEntity<List<RSVP>> getRSVPByName(@RequestParam("name") String name){
        List<RSVP> rList = rsvpRepo.listAllByName(name);

        if (rList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(rList, HttpStatus.OK);
        }
    }

    // add new rsvp
    @PostMapping(path="api/rsvp")
    public ResponseEntity<Boolean> insertRSVP(@RequestBody RSVP rsvp){
        RSVP rs = rsvp;
        Boolean result = rsvpRepo.save(rs);

        if(result){
            return new ResponseEntity<Boolean>(result,HttpStatus.CREATED);
        }
        else{
        return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    //update existing rsvp by email
    @PutMapping(path="api/rsvp/{email}")
    public ResponseEntity<String> updateByEmail(@PathVariable("email") String email, @RequestBody RSVP rsvp ){

        RSVP r =rsvpRepo.findByEmail(email);

        Boolean result = false;

        if (r != null){
            result = rsvpRepo.updateRSVP(rsvp);
        }

        if (result) {
            return new ResponseEntity<>("Updated",HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Not Updated",HttpStatus.NOT_FOUND);
        }

    }

    //get the rsvp count
    @GetMapping(path="api/rsvps/count")
    public Integer getRSVPCount(){
        Integer rsvpCount=rsvpRepo.count();
        return rsvpCount;
    }



}
