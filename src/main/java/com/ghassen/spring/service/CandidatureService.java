package com.ghassen.spring.service;
import com.ghassen.spring.domain.Candidature;
import com.ghassen.spring.domain.CandidatureRepository;
import com.ghassen.spring.domain.User;
import com.ghassen.spring.web.dto.CandidatureDto;
import com.ghassen.spring.web.dto.CandidatureUpdateDto;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by Keno&Kemo on 18.10.2017..
 */

@Service
public class CandidatureService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private CandidatureRepository candidatureRepository;
    private UserService userService;
    private CacheManager cacheManager;
    private static final Logger logger = LoggerFactory.getLogger(CandidatureService.class);

    public CandidatureService(BCryptPasswordEncoder bCryptPasswordEncoder, CandidatureRepository candidatureRepository, UserService
            userService, CacheManager cacheManager) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.candidatureRepository = candidatureRepository;
        this.userService = userService;
        this.cacheManager = cacheManager;
    }

    //region find methods
    //==============================================================================================
    @Cacheable(value = "cache.allCandidatures")
    public List<Candidature> findAll() {
        return candidatureRepository.findAll();
    }

    @Cacheable(value = "cache.allCandidaturesPageable")
    public Page<Candidature> findAllPageable(Pageable pageable) {
        return candidatureRepository.findAll(pageable);
    }
    
    @Cacheable (value = "cache.candidatureByEmail", key = "#email", unless="#result == null")
    public Candidature findByEmail(String email) {
        return candidatureRepository.findByEmailEagerly(email);
    }
    

    @Cacheable (value = "cache.candidatureById", key = "#id", unless="#result == null")
    public Optional<Candidature> findById(Long id) {
        return candidatureRepository.findById(id);
    }

    public Page<Candidature> findByIdPageable(Long id, Pageable pageRequest){
        Optional<Candidature> candidature = candidatureRepository.findById(id);
        List<Candidature> candidatures = candidature.isPresent() ? Collections.singletonList(candidature.get()) : Collections.emptyList();
        return new PageImpl<>(candidatures, pageRequest, candidatures.size());
    }

    public Candidature findByEmailAndIdNot (String email, Long id){
        return candidatureRepository.findByEmailAndIdNot(email, id);
    }  
    
    public Candidature findByIdEagerly (Long id){
        return candidatureRepository.findByIdEagerly(id);
    }

    @Cacheable(value = "cache.allCandidaturesEagerly")
    public List<Candidature> findAllEagerly() {
        return candidatureRepository.findAllEagerly();
    }    
  

    //region Find by containing
    @Cacheable (value = "cache.byNameContaining")
    public Page<Candidature> findByNameContaining (String name, Pageable pageable){
        return candidatureRepository.findByNameContainingOrderByIdAsc(name, pageable);
    }

    @Cacheable (value = "cache.bySurnameContaining")
    public Page<Candidature> findBySurnameContaining(String surname, Pageable pageable) {
        return candidatureRepository.findBySurnameContainingOrderByIdAsc(surname, pageable);
    }
   

    @Cacheable (value = "cache.byEmailContaining")
    public Page<Candidature> findByEmailContaining(String email, Pageable pageable) {
        return candidatureRepository.findByEmailContainingOrderByIdAsc(email, pageable);
    }
    //endregion

    //==============================================================================================
    //endregion


    @Transactional
    @CacheEvict(value = {"cache.allCandidatures","cache.allCandidaturesPageable","cache.candidatureByEmail","cache.candidatureById","cache.byNameContaining","cache.bySurnameContaining","cache.byEmailContaining"}, allEntries = true)
    public void save(Candidature candidature) {
        candidatureRepository.save(candidature);
    }

    @CacheEvict(value = {"cache.allCandidatures","cache.allCandidaturesPageable","cache.candidatureByEmail","cache.candidatureById","cache.byNameContaining","cache.bySurnameContaining","cache.byEmailContaining"}, allEntries = true)
    public void deleteById(Long id) {
        candidatureRepository.deleteById(id);
    }

    public Candidature createNewCandidature(CandidatureDto candDto) {
              
                
        Candidature cand = new Candidature();
        cand.setName(candDto.getName());
        cand.setSurname(candDto.getSurname());        
        cand.setEmail(candDto.getEmail());   
//        cand.setDiplBac(candDto.getDipBac());
//        cand.setRelNotBac(candDto.getRelNotBac());
//        cand.setBultin1(candDto.getBultin1());
//        cand.setBultin2(candDto.getBultin2());
//        cand.setBultin3(candDto.getBultin3());
//        cand.setRapport1(candDto.getRapport1());
//        cand.setRapport2(candDto.getRapport2());        
//        cand.setLettreRecom(candDto.getLettreRecom());           
          MultipartFile[] files = candDto.getFiles();
          //String[] filesRoot = new String[];
          String[] filesRoot = new String[10];
	 //String message = "";
		for(int i = 0; i < files.length; i++) {
			MultipartFile file = files[i];
			//String name = names[i];
			try {
				byte[] bytes = file.getBytes();
				// Creating the directory to store file
				//String rootPath = System.getProperty("/candidaturesFolder"); 
                                
                                //Resource resource = new ClassPathResource("android.png");
                                //Resource resource = new ClassPathResource("candidatures");                                  
                                String fileRoot = "C:/Users/lenovo/Documents/NetBeansProjects/ProjetWebServices/src/main/resources/static/candidaturesFolder/candidature"+candDto.getEmail();
				File dir = new File(fileRoot);                                
				if (!dir.exists())
					dir.mkdirs();
				// Create the file on server
				File serverFile = new File(fileRoot+ "/"+ files[i].getOriginalFilename());
                                filesRoot[i]="http://localhost:8080/candidaturesFolder/candidature"+candDto.getEmail()+"/"+ files[i].getOriginalFilename();
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				logger.info("Server File Location="+serverFile.getAbsolutePath());
				//message = message + "You successfully uploaded file=" + name
				//		+ "<br />";
			} catch (Exception e) {
				logger.error("You failed to upload " + files[i].getOriginalFilename() + " => " + e.getMessage());
			}
                }
        cand.setFiles(filesRoot);
        cand.setUsers(Collections.singletonList(userService.findByUsername("admin")));
        return cand;
    }
    public Candidature getUpdatedCandidature(Candidature persistedCandidature, CandidatureUpdateDto candidatureUpdateDto) {      
        persistedCandidature.setUsers(getAssignedUsersList(candidatureUpdateDto));      
        return persistedCandidature;
    }    
    public List<User> getAssignedUsersList(CandidatureUpdateDto candidatureUpdateDto) {        
    Map<Long, User> assignedUserMap = new HashMap<>();
        List<User> users = candidatureUpdateDto.getUsers();
        users.forEach((user) -> {
            assignedUserMap.put(user.getId(), user);
        });
        List<User> candidatureUsers = new ArrayList<>();
        List<User> allUsers = userService.findAll();
        allUsers.forEach((user) -> {
            if (assignedUserMap.containsKey(user.getId())) {
                candidatureUsers.add(user);
            } else {
                candidatureUsers.add(null);
            }
        });
        return candidatureUsers;
    }
        
    
}
