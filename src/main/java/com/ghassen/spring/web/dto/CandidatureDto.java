package com.ghassen.spring.web.dto;
import com.ghassen.spring.customAnnotations.ValidEmail;
import java.io.File;
import java.util.ArrayList;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;


public class CandidatureDto {

    private Long id;

    @NotBlank (message = "Name is required")
    private String name;

    @NotBlank (message = "Surname is required")
    private String surname;    

    @ValidEmail
    @NotBlank (message = "Email is required")
    private String email;      

    private boolean enabled;
    
    
    @NotNull(message = "Files are required")
    private MultipartFile[] files;
    
    
    private ArrayList<String> filesPath;
    
    /**@NotNull(message = "Diplome is required")
    private File diplome;
    
    @NotNull(message = "Attestation de réussite is required")
    private File attesReu;

    @NotNull(message = "diplome baccalauréat  is required")
    private File dipBac;
    
    @NotNull(message = "relevé de notes baccalauréat  is required")
    private File relNotBac;
    
    @NotNull(message = "bulletin de notes L1  is required")
    private File bultin1;
    
    @NotNull(message = "bulletin de notes L2  is required")
    private File bultin2;
    
    @NotNull(message = "bulletin de notes L3  is required")
    private File bultin3;
    
    @NotNull(message = "Rapport du stage ouvrier  is required")
    private File rapport1;
    
    @NotNull(message = "Rapport du stage technicien  is required")
    private File rapport2;
    
    @NotNull(message = "Lettre de recommandation  is required")
    private File lettreRecom;  
    * 
    * */
    
    
     public CandidatureDto(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public CandidatureDto(String name, String surname, String email, boolean enabled, MultipartFile[] files, ArrayList<String> filesPath) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.enabled = enabled;
        this.files = files;
        this.filesPath = filesPath;
    }

    public CandidatureDto(String name, String surname, String email, boolean enabled, MultipartFile[] files) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.enabled = enabled;
        this.files = files;
    }          

    public CandidatureDto() {
    }

//    public CandidatureDto(Long id, String name, String surname, String email, boolean enabled, File diplome, File attesReu,File dipBac, File relNotBac, File bultin1, File bultin2, File bultin3, File rapport1, File rapport2, File lettreRecom) {
//        this.id = id;
//        this.name = name;
//        this.surname = surname;
//        this.email = email;
//        this.enabled = enabled;
//        this.diplome = diplome;
//        this.attesReu = attesReu;
//        this.dipBac = dipBac;
//        this.relNotBac = relNotBac;
//        this.bultin1 = bultin1;
//        this.bultin2 = bultin2;
//        this.bultin3 = bultin3;
//        this.rapport1 = rapport1;
//        this.rapport2 = rapport2;
//        this.lettreRecom = lettreRecom;
//    }  

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
//    public File getDipBac() {
//        return dipBac;
//    }
//
//    public void setDipBac(File dipBac) {
//        this.dipBac = dipBac;
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

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }

    public ArrayList<String> getFilesPath() {
        return filesPath;
    }

    public void setFilesPath(ArrayList<String> filesPath) {
        this.filesPath = filesPath;
    }
    
    
    
    
    
    
    
    
  
}
