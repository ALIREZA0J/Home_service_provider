package com.hsp.home_service_provider.service.specialist;

import com.hsp.home_service_provider.repository.specialist.SpecialistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpecialistService {

    private final SpecialistRepository specialistRepository;
}
