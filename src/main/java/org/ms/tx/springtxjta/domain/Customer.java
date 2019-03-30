package org.ms.tx.springtxjta.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:
 * @author: Zhenglai Zhang
 * @create: 2019-03-30 15:41
 **/
@Entity(name = "customer")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String userName;

    private String password;

    private String role;
}
