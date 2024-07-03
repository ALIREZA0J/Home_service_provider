package com.hsp.home_service_provider.repository.offer;


import com.hsp.home_service_provider.model.Offer;
import com.hsp.home_service_provider.model.Order;
import com.hsp.home_service_provider.model.enums.OfferStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer,Long> {


    List<Offer> findOffersByOrderAndOfferStatus(Order order, OfferStatus offerStatus);

    Offer findOfferByOrderAndOfferStatus(Order order, OfferStatus offerStatus);

}
