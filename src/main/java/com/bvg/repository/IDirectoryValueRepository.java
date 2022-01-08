package com.bvg.repository;

import com.bvg.domain.DirectoryValue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IDirectoryValueRepository extends JpaRepository<DirectoryValue, Long> {

    @Query(value = "select d from DirectoryValue d where d.directory.id = :directoryId")
    List<DirectoryValue> findAllByDirectoryId(@Param("directoryId") Long directoryId);

    @Query(value = "select d from DirectoryValue d where d.key = :key")
    DirectoryValue findByKey(@Param("key") String key);

    @Query(value = "select d from DirectoryValue d where lower(d.value) = lower(:value) and lower(d.directory.name) = lower(:directory)")
    Optional<DirectoryValue> findByValueAndDirectory(@Param("value") String value, @Param("directory") String directory);

    @Query(value = "select d from DirectoryValue d where lower(d.shortValue) = lower(:shortValue) and lower(d.directory.name) = lower(:directory)")
    Optional<DirectoryValue> findByShortValueAndDirectory(@Param("shortValue") String shortValue, @Param("directory") String directory);
}
