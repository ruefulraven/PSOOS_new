
package com.main.psoos.model;

import com.main.psoos.dto.CustomerDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity( name = "customer")
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name = "customer_id")
    private Integer customerId;
    @Column( name = "customer_name")
    private String customerName;
    @Column( name = "customer_email" )
    private String customerEmail;
    @Column(name = "customer_address" )
    private String customerAddress;
    @Column(name = "customer_phone_number")
    private String customerPhoneNumber;

    public Customer(CustomerDTO customerDTO) {
        this.customerAddress = customerDTO.getCustomerHomeAddress();
        this.customerEmail = customerDTO.getCustomerEmail();
        this.customerPhoneNumber = customerDTO.getCustomerPhoneNumber();
        this.customerName = customerDTO.getCustomerName();
    }

}