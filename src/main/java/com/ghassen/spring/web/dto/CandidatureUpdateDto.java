package com.ghassen.spring.web.dto;

import com.ghassen.spring.domain.User;
import java.util.ArrayList;
import java.util.List;

public class CandidatureUpdateDto {

     private Long id;        
     private List<User> users = new ArrayList<>();
       
    public CandidatureUpdateDto() {
    }      
    
    public CandidatureUpdateDto(Long id,List<User> users) {
        this.id = id;
        this.users=users;
    }
       
        
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }      
  
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }  
    
}