package org.ms.tx.springtxjta.service;

import java.util.List;
import org.ms.tx.springtxjta.dao.CustomerRepository;
import org.ms.tx.springtxjta.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description:
 * @author: Zhenglai Zhang
 * @create: 2019-03-30 15:45
 **/
@Service
public class CustomerServiceTxAnnotation {

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional(rollbackFor = Exception.class)
    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    private Customer simulateError() {
        throw new RuntimeException("error");
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

}
