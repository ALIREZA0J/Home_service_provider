package com.hsp.home_service_provider.service.comment;

import com.hsp.home_service_provider.exception.CommentException;
import com.hsp.home_service_provider.model.Comment;
import com.hsp.home_service_provider.repository.comment.CommentRepository;
import com.hsp.home_service_provider.service.specialist.SpecialistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final SpecialistService specialistService;

    public Comment register(Comment comment){
        if (comment.getScore() > 5 || comment.getScore() < 1)
            throw new CommentException("Score is out of range score should be between (1 - 5)");
        specialistService.applyCommentScore(comment.getOffer().getSpecialist(), comment.getScore());
        return commentRepository.save(comment);
    }
}
