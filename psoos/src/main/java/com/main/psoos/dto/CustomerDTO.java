package com.main.psoos.dto;

import com.main.psoos.model.Customer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerDTO {
    private Integer customerId;
    private String customerName;
    private String customerEmail;
    private String customerHomeAddress;
    private String customerPhoneNumber;

    public CustomerDTO(Customer customer) {
        this.customerName = customer.getCustomerName();
        this.customerHomeAddress = customer.getCustomerAddress();
        this.customerEmail = customer.getCustomerEmail();
        this.customerPhoneNumber = customer.getCustomerPhoneNumber();
    }

}

