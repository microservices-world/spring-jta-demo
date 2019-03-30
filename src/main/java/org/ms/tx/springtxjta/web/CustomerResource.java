package org.ms.tx.springtxjta.web;

import java.util.List;
import org.ms.tx.springtxjta.domain.Customer;
import org.ms.tx.springtxjta.service.CustomerServiceTxAnnotation;
import org.ms.tx.springtxjta.service.CustomerServiceTxInCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: Zhenglai Zhang
 * @create: 2019-03-30 16:06
 **/
@RestController
@RequestMapping("/api/customers")
public class CustomerResource {

    @Autowired
    private CustomerServiceTxInCode customerService;

    @Autowired
    private CustomerServiceTxAnnotation customerServiceTxAnnotation;

    @PostMapping("/code")
    public Customer createWithCode(@RequestBody Customer customer) {
        return customerService.create(customer);
    }

    @PostMapping("/annotation")
    public Customer createWithAnnotation(@RequestBody Customer customer) {
        return customerServiceTxAnnotation.create(customer);
    }

    @GetMapping
    public List<Customer> getAll() {
        return customerService.findAll();
    }
}
