package com.ghassen.spring.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.ghassen.spring.web.dto.Decision;
import java.io.File;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;


@Entity
@Table(name="candidature")
public class Candidature {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String surname; 

    @Column(unique = true)
    private String email;        

    private boolean enabled;
              
//    private File diplome;
//        
//    private File attesReu;
//    
//    private File diplBac;
//    
//    private File relNotBac;
//    
//    private File bultin1;
//            
//    private File bultin2;
//    
//    private File bultin3;
//    
//    private File rapport1;
//    
//    private File rapport2;
//    
//    private File lettreRecom; 
    
    private String[] filesPath;
    
    @Transient
    private MultipartFile[] files;
    
    
    
    
    private Decision decision;
    
     @JsonBackReference
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "users_candidatures",
            joinColumns = {@JoinColumn(name ="candidature_id" )},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<User> users = new ArrayList<>();

    public Candidature() {
    }

    public Candidature(Long id, String name, String surname, String email, boolean enabled, File diplome, File attesReu, File diplBac, File relNotBac, File bultin1, File bultin2, File bultin3, File rapport1, File rapport2, File lettreRecom) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.enabled = enabled;
//        this.diplome = diplome;
//        this.attesReu = attesReu;
//        this.diplBac = diplBac;
//        this.relNotBac = relNotBac;
//        this.bultin1 = bultin1;
//        this.bultin2 = bultin2;
//        this.bultin3 = bultin3;
//        this.rapport1 = rapport1;
//        this.rapport2 = rapport2;
//        this.lettreRecom = lettreRecom;        
    }
    
        public Candidature(Long id, String name, String surname, String email, boolean enabled, File diplome, File attesReu, File diplBac, File relNotBac, File bultin1, File bultin2, File bultin3, File rapport1, File rapport2, File lettreRecom,List<User> users) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.enabled = enabled;
//        this.diplome = diplome;
//        this.attesReu = attesReu;
//        this.diplBac = diplBac;
//        this.relNotBac = relNotBac;
//        this.bultin1 = bultin1;
//        this.bultin2 = bultin2;
//        this.bultin3 = bultin3;
//        this.rapport1 = rapport1;
//        this.rapport2 = rapport2;
//        this.lettreRecom = lettreRecom;        
        this.users = users;
    }

    public Candidature(Long id, String name, String surname, String email, boolean enabled,String[]filesPath) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.enabled = enabled;
        filesPath = new String[10];
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }
                
    
    
    public String[] getFilesPath() {
        return filesPath;
    }

    public void setFiles(String[] filesPath) {
        this.filesPath = filesPath;
    }
        

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

//    public File getDiplome() {
//        return diplome;
//    }
//
//    public void setDiplome(File diplome) {
//        this.diplome = diplome;
//    }
//
//    public File getAttesReu() {
//        return attesReu;
//    }
//
//    public void setAttesReu(File attesReu) {
//        this.attesReu = attesReu;
//    }
//
//    public File getDiplBac() {
//        return diplBac;
//    }
//
//    public void setDiplBac(File diplBac) {
//        this.diplBac = diplBac;
//    }
//
//    public File getRelNotBac() {
//        return relNotBac;
//    }
//
//    public void setRelNotBac(File relNotBac) {
//        this.relNotBac = relNotBac;
//    }
//
//    public File getBultin1() {
//        return bultin1;
//    }
//
//    public void setBultin1(File bultin1) {
//        this.bultin1 = bultin1;
//    }
//
//    public File getBultin2() {
//        return bultin2;
//    }
//
//    public void setBultin2(File bultin2) {
//        this.bultin2 = bultin2;
//    }
//
//    public File getBultin3() {
//        return bultin3;
//    }
//
//    public void setBultin3(File bultin3) {
//        this.bultin3 = bultin3;
//    }
//
//    public File getRapport1() {
//        return rapport1;
//    }
//
//    public void setRapport1(File rapport1) {
//        this.rapport1 = rapport1;
//    }
//
//    public File getRapport2() {
//        return rapport2;
//    }
//
//    public void setRapport2(File rapport2) {
//        this.rapport2 = rapport2;
//    }
//
//    public File getLettreRecom() {
//        return lettreRecom;
//    }
//
//    public void setLettreRecom(File lettreRecom) {
//        this.lettreRecom = lettreRecom;
//    }

    public Decision getDecision() {
        return decision;
    }

    public void setDecision(Decision decision) {
        this.decision = decision;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
        
        

    
    
    

    
    

  
}
