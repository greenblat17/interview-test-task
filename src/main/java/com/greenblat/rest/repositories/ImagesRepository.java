package com.greenblat.rest.repositories;

import com.greenblat.rest.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImagesRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByUri(String uri);
}
