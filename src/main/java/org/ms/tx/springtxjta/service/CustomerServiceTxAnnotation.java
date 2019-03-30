package org.ms.tx.springtxjta.service;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.ms.tx.springtxjta.dao.CustomerRepository;
import org.ms.tx.springtxjta.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description:
 * @author: Zhenglai Zhang
 * @create: 2019-03-30 15:45
 **/
@Service
@Slf4j
public class CustomerServiceTxAnnotation {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private JmsTemplate jmsTemplate;

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

    @JmsListener(destination = "customer:msg1:new")
    public Customer createAsync(String name) {
        log.info("meow: get new customer, name={}", name);
        Customer customer = Customer.builder()
                .userName("Annotation" + name)
                .password("P@ssw0rd")
                .role("User").build();
        var cust = customerRepository.save(customer);
        jmsTemplate.convertAndSend("customer:msg:reply", cust.toString());
        return cust;
    }

}
