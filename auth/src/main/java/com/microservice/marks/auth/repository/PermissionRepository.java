package com.microservice.marks.auth.repository;

import com.microservice.marks.auth.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    @Query("Select p from Permission p where p.description = :description")
    Permission findByDescription(@Param("description") String description);

}
