package org.ms.tx.springtxjta.web;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.ms.tx.springtxjta.domain.Customer;
import org.ms.tx.springtxjta.service.CustomerServiceTxAnnotation;
import org.ms.tx.springtxjta.service.CustomerServiceTxInCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: Zhenglai Zhang
 * @create: 2019-03-30 16:06
 **/
@RestController
@RequestMapping("/api/customers")
@Slf4j
public class CustomerResource {

    @Autowired
    private CustomerServiceTxInCode customerService;

    @Autowired
    private CustomerServiceTxAnnotation customerServiceTxAnnotation;

    @Autowired
    private JmsTemplate jmsTemplate;

    @PostMapping("/code")
    public Customer createWithCode(@RequestBody Customer customer) {
        return customerService.create(customer);
    }

    @PostMapping("/annotation")
    public Customer createWithAnnotation(@RequestBody Customer customer) {
        return customerServiceTxAnnotation.create(customer);
    }

    @Transactional
    @PostMapping("/codeAsync")
    public Customer createWithCodeAsync(@RequestParam String name) {
        log.info("meow: codeAsync request param={}", name);
        return customerService.createByListner(name);
    }

    @Transactional
    @PostMapping("/annotationAsync")
    public Customer createWithAnnotationAsync(@RequestParam String name) {
        log.info("meow: annotationAsync request param={}", name);
        return customerServiceTxAnnotation.createAsync(name);
    }


    @GetMapping
    public List<Customer> getAll() {
        return customerService.findAll();
    }
}
