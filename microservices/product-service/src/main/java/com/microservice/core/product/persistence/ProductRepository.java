package com.microservice.core.product.persistence;

import com.microservice.api.core.product.Product;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.Optional;

public interface ProductRepository extends PagingAndSortingRepository<ProductEntity,String> {


    Optional<ProductEntity> findByProductId(int productId);



}
