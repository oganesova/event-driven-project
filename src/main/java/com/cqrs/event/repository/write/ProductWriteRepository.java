package com.cqrs.event.repository.write;

import com.cqrs.event.entity.WriteProduct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("productWriteRepository")
public interface ProductWriteRepository extends JpaRepository<WriteProduct,Long> {

}