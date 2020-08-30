package com.microservice.core.product.services;

import com.microservice.api.core.product.Product;
import com.microservice.api.core.product.ProductService;
import com.microservice.util.exceptions.InvalidInputException;
import com.microservice.util.exceptions.NotFoundException;
import com.microservice.util.http.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import sun.rmi.runtime.Log;

@RestController
public class ProductServiceImpl implements ProductService {

    private static final Logger LOG= LoggerFactory.getLogger(ProductServiceImpl.class);

    private ServiceUtil util;

    @Autowired
    public ProductServiceImpl(ServiceUtil util) {
        this.util = util;
    }

    @Override
    public Product getProduct(int productId) {
        LOG.debug("/Return found product for prodictId={}",productId);
        if (productId < 1) throw new InvalidInputException("Invalid productId: " + productId);

        if (productId == 13) throw new NotFoundException("No product found for productId: " + productId);

        return new Product(productId, "name-" + productId, 123, util.getServiceAddress());
    }
}
