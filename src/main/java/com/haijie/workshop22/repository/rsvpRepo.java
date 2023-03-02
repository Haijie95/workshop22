package com.haijie.workshop22.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.haijie.workshop22.model.RSVP;

@Repository
public class rsvpRepo {
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    String selectSQL="select * from rsvp";
    // String selectByNameSQL="select * from rsvp where name = ?";
    String selectByNameSQL="select * from rsvp where name like '%' ? '%'";
    String findByEmailSQL = "select * from rsvp where email = ?";
    // how to do %%
    String insertSQL="insert into rsvp (name,email,phone,confirmation_date,comments) values (?,?,?,?,?)";
    String updateSQL="update rsvp set name = ?, email = ?, phone = ?, confirmation_date = ?, comments = ? where id = ?";
    String countSQL="select count(*) from rsvp";

    public List<RSVP> listAll(){
        return jdbcTemplate.query(selectSQL,BeanPropertyRowMapper.newInstance(RSVP.class));
    }

    public List<RSVP> listAllByName(String name){
        return jdbcTemplate.query(selectByNameSQL,BeanPropertyRowMapper.newInstance(RSVP.class),name);
    }

    public Boolean updateRSVP(RSVP rsvp){
        Integer result = jdbcTemplate.update(updateSQL, rsvp.getName(), rsvp.getEmail(), rsvp.getPhone(), 
                                            rsvp.getConfirmationDate(), rsvp.getComments(), rsvp.getId());
        return result > 0?true:false;
    }

    public RSVP findByEmail(String email){
        RSVP r = jdbcTemplate.queryForObject(findByEmailSQL, BeanPropertyRowMapper.newInstance(RSVP.class), email);
        return r;
    }

    public Boolean save(RSVP rsvp) {
        Boolean saved=false;
        //as prompt by the system we need the preparestatement callback
        //and since we getting boolean therefore change the t to boolean
        saved = jdbcTemplate.execute(insertSQL, new PreparedStatementCallback<Boolean>() {

            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setString(1, rsvp.getName());
                ps.setString(2, rsvp.getEmail());
                ps.setString(3, rsvp.getPhone());
                ps.setDate(4, rsvp.getConfirmationDate());
                ps.setString(5, rsvp.getComments());
                Boolean result=ps.execute();
                return result;
            }            
        });
        return saved;
    }

    public int update(RSVP rsvp) {
        int updated=0;
        updated=jdbcTemplate.update(updateSQL, new PreparedStatementSetter(){

            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, rsvp.getName());
                ps.setString(2, rsvp.getEmail());
                ps.setString(3, rsvp.getPhone());
                ps.setDate(4, rsvp.getConfirmationDate());
                ps.setString(5, rsvp.getComments());
            }
        });
        return updated;
    }

    public int count() {
        Integer result=0;
        //the integer.class is to tell what type of result to return
        result=jdbcTemplate.queryForObject(countSQL, Integer.class);
        return result;
    }




}
