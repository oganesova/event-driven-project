package com.cqrs.event.repository.read;

import com.cqrs.event.entity.ReadProduct;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("productReadRepository")
public interface ProductReadRepository extends JpaRepository<ReadProduct,Long> {
}
