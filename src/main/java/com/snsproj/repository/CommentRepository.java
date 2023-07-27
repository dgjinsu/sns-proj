package com.snsproj.repository;

import com.snsproj.model.Comment;
import com.snsproj.model.entity.CommentEntity;
import com.snsproj.model.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    Page<CommentEntity> findAllByPost(PostEntity postEntity, Pageable pageable);

    @Modifying
    @Query("update CommentEntity c set c.removedAt = now() where c.post = :post")
    void deleteAllByPost(@Param("post") PostEntity post);
}
