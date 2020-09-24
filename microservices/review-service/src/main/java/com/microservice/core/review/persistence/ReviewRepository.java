package com.microservice.core.review.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReviewRepository extends CrudRepository<ReviewEntity,String> {
    List<ReviewEntity> findByProductId(int productId);
}
