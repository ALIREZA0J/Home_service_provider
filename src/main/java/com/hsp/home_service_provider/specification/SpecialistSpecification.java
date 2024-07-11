package com.hsp.home_service_provider.specification;

import com.hsp.home_service_provider.dto.specialist.SpecialistFilter;
import com.hsp.home_service_provider.model.Specialist;
import com.hsp.home_service_provider.model.SubService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpecialistSpecification {


    public static Specification<Specialist> filterSpecialist(SpecialistFilter specialistFilter) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Optional.ofNullable(specialistFilter.firstName())
                    .map(firstName -> criteriaBuilder.equal(root.get("firstName"), firstName)).ifPresent(predicates::add);
            Optional.ofNullable(specialistFilter.lastName())
                    .map(lastname -> criteriaBuilder.equal(root.get("lastName"), lastname)).ifPresent(predicates::add);
            Optional.ofNullable(specialistFilter.gmail())
                    .map(gmail -> criteriaBuilder.equal(root.get("gmail"), gmail)).ifPresent(predicates::add);
            Optional.ofNullable(specialistFilter.registrationDate())
                    .map(registrationDate -> criteriaBuilder.equal(root.get("registrationDate"), registrationDate))
                    .ifPresent(predicates::add);
            Optional.ofNullable(specialistFilter.score())
                    .map(score -> criteriaBuilder.equal(root.get("score"), score)).ifPresent(predicates::add);

            Join<Specialist, SubService> specialistSubServiceJoin = root.join("subServices", JoinType.INNER);
            Optional.ofNullable(specialistFilter.subService().name())
                    .map(name -> criteriaBuilder.equal(specialistSubServiceJoin.get("name"), name))
                    .ifPresent(predicates::add);

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }

    public static Specification<Specialist> hasSubService(String subServiceName) {
        return (root, query, criteriaBuilder) -> {
            Join<Specialist, SubService> specialistSubServiceJoin = root.join("subServices", JoinType.INNER);
            return criteriaBuilder.equal(specialistSubServiceJoin.get("name"), subServiceName);
        };
    }

}
