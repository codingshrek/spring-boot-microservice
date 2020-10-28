package com.microservice.core.product.services;

import com.microservice.api.core.product.Product;
import com.microservice.api.core.product.ProductService;
import com.microservice.core.product.persistence.ProductEntity;
import com.microservice.core.product.persistence.ProductRepository;
import com.microservice.util.exceptions.InvalidInputException;
import com.microservice.util.exceptions.NotFoundException;
import com.microservice.util.http.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductServiceImpl implements ProductService {

    private static final Logger LOG= LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ServiceUtil util;
    private final ProductMapper mapper;
    private final ProductRepository repository;

    @Autowired
    public ProductServiceImpl(ServiceUtil util, ProductMapper mapper, ProductRepository repository) {
        this.util = util;
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public Product createProduct(Product body) {
        try{

            ProductEntity entity = mapper.apiToEntity(body);
            entity=repository.save(entity);
            LOG.debug("createProduct: entity created for productId: {}", body.getProductId());
            return mapper.entityToApi(entity);
        }
        catch (DuplicateKeyException dex){
            throw new InvalidInputException("Duplicate key, Product Id: " + body.getProductId());
        }
    }

    @Override
    public Product getProduct(int productId) {
        LOG.debug("/Return found product for prodictId={}",productId);
        if (productId < 1) throw new InvalidInputException("Invalid productId: " + productId);

        ProductEntity entity=repository.findByProductId(productId).orElseThrow(()-> new NotFoundException("No product found for productId: "+productId));
        Product response= mapper.entityToApi(entity);
        response.setServiceAddress(util.getServiceAddress());
        return response;
    }

    @Override
    public void deleteProduct(int productId) {
        LOG.debug("deleteProduct: tries to delete an entity with productId: {}", productId);
        repository.findByProductId(productId).ifPresent(e -> repository.delete(e));
    }
}
