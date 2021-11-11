package com.mercadolivre.proxy.repository;

import com.mercadolivre.proxy.entity.Request;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RequestRepository extends CrudRepository<Request, String> {
}
