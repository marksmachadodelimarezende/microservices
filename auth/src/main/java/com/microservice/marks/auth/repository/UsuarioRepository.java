package com.microservice.marks.auth.repository;

import com.microservice.marks.auth.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("Select u from Usuario u where u.loggin = :userName")
    Usuario findByLoggin(@Param("userName") String userName);
}
