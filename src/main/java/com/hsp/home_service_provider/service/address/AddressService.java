package com.hsp.home_service_provider.service.address;

import com.hsp.home_service_provider.repository.address.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
}
