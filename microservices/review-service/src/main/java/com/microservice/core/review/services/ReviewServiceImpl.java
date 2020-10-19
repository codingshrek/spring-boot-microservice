package com.microservice.core.review.services;

import com.microservice.api.core.review.Review;
import com.microservice.api.core.review.ReviewService;
import com.microservice.core.review.persistence.ReviewEntity;
import com.microservice.core.review.persistence.ReviewRepository;
import com.microservice.util.exceptions.InvalidInputException;
import com.microservice.util.http.ServiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ReviewServiceImpl implements ReviewService {

    private static final Logger LOG = LoggerFactory.getLogger(ReviewServiceImpl.class);

    private final ReviewRepository repository;

    private final ReviewMapper mapper;

    private final ServiceUtil serviceUtil;

    @Autowired
    public ReviewServiceImpl(ReviewRepository repository, ReviewMapper mapper, ServiceUtil serviceUtil) {
        this.repository = repository;
        this.mapper = mapper;
        this.serviceUtil = serviceUtil;
    }

    @Override
    public Review createReview(Review body) {
       try{
           ReviewEntity entity= mapper.apiToEntity(body);
           entity=repository.save(entity);
           LOG.debug("createReview: created a review entity: {}/{}", body.getProductId(), body.getReviewId());
           return mapper.entityToApi(entity);
       }
        catch (DataIntegrityViolationException e){
            throw new InvalidInputException("Duplicate key, Product Id: " + body.getProductId() + ", Review Id:" + body.getReviewId());
        }
    }

    @Override
    public List<Review> getReviews(int productId) {

        if (productId < 1) throw new InvalidInputException("Invalid productId: " + productId);

        List<ReviewEntity> entityList = repository.findByProductId(productId);
        List<Review> list = mapper.entityListToApiList(entityList);
        list.forEach(e -> e.setServiceAddress(serviceUtil.getServiceAddress()));
        LOG.debug("getReviews: response size: {}", list.size());
        return list;

    }

    @Override
    public void deleteReviews(int productId) {
        LOG.debug("/reviews delete for", productId);
        repository.deleteAll(repository.findByProductId(productId));

    }
}
