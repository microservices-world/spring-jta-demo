package org.ms.tx.springtxjta.dao;


import org.ms.tx.springtxjta.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @description:
 * @author: Zhenglai Zhang
 * @create: 2019-03-30 15:44
 **/
public interface CustomerRepository  extends JpaRepository<Customer, Long> {

    Customer findOneByUserName(String userName);
}
