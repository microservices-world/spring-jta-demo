package org.ms.tx.springtxjta.service;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.ms.tx.springtxjta.dao.CustomerRepository;
import org.ms.tx.springtxjta.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * @description:
 * @author: Zhenglai Zhang
 * @create: 2019-03-30 15:45
 **/
@Service
@Slf4j
public class CustomerServiceTxInCode {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    public Customer create(Customer customer) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setIsolationLevel(TransactionDefinition.ISOLATION_DEFAULT);
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        def.setTimeout(10);
        var ts = platformTransactionManager.getTransaction(def);
        try {
            var cust = customerRepository.save(customer);
            // simulate error, triggering rollback
            // simulateError();
            platformTransactionManager.commit(ts);
            return cust;
        } catch (Exception ex) {
            if (!ts.isCompleted()) {
                platformTransactionManager.rollback(ts);
            }
            log.error("save customer error", ex);
            throw ex;
        }
    }

    private Customer simulateError() {
        throw new RuntimeException("error");
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

}
