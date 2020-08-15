package com.ghassen.spring.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Keno&Kemo on 30.09.2017..
 */

@Repository
public interface CandidatureRepository extends JpaRepository<Candidature, Long> {

    //User findByUsername(String username);

    Candidature findByEmailAndIdNot(String email, Long id);

    //User findByUsernameAndIdNot(String username, Long id);

    //Page<User> findByUsernameContainingOrderByIdAsc(String username, Pageable pageable);

    Page<Candidature> findByEmailContainingOrderByIdAsc(String email, Pageable pageable);

    Page<Candidature> findByNameContainingOrderByIdAsc(String name, Pageable pageable);

    Page<Candidature> findBySurnameContainingOrderByIdAsc(String surname, Pageable pageable);

    //region Find eagerly
    //==========================================================================
    @Query("SELECT c FROM Candidature c JOIN FETCH c.users")
    List<Candidature> findAllEagerly();

    @Query("SELECT c FROM Candidature c JOIN FETCH c.users WHERE c.email = (:email)")
    Candidature findByEmailEagerly(@Param("email") String email);

    @Query("SELECT c FROM Candidature c JOIN FETCH c.users WHERE c.id = (:id)")
    Candidature findByIdEagerly(@Param("id") Long id);
    //==========================================================================
    //endregion

    
}
