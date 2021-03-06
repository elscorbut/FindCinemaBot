package com.bvg.repository;

import com.bvg.domain.Directory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DirectoryRepository extends JpaRepository<Directory, Long> {

    Optional<Directory> findByName(String name);
}
