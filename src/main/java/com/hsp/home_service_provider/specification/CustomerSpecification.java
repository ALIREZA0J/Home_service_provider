package com.hsp.home_service_provider.specification;

import com.hsp.home_service_provider.dto.customer.CustomerFilter;
import com.hsp.home_service_provider.model.Customer;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerSpecification {
    public static Specification<Customer> filterSpecialist(CustomerFilter customerFilter) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Optional.ofNullable(customerFilter.firstName())
                    .map(firstName -> criteriaBuilder.equal(root.get("firstName"), firstName)).ifPresent(predicates::add);
            Optional.ofNullable(customerFilter.lastName())
                    .map(lastname -> criteriaBuilder.equal(root.get("lastName"), lastname)).ifPresent(predicates::add);
            Optional.ofNullable(customerFilter.gmail())
                    .map(gmail -> criteriaBuilder.equal(root.get("gmail"), gmail)).ifPresent(predicates::add);
            Optional.ofNullable(customerFilter.registrationDate())
                    .map(registrationDate -> criteriaBuilder.equal(root.get("registrationDate"), registrationDate))
                    .ifPresent(predicates::add);
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
