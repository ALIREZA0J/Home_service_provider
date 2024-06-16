package com.hsp.home_service_provider.repository.comment;


import com.hsp.home_service_provider.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
