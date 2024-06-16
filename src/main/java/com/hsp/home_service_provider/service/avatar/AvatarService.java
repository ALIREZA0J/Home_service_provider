package com.hsp.home_service_provider.service.avatar;

import com.hsp.home_service_provider.repository.avatar.AvatarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvatarService {

    private final AvatarRepository avatarRepository;
}
