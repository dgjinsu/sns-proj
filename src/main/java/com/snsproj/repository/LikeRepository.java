package com.snsproj.repository;

import com.snsproj.model.entity.LikeEntity;
import com.snsproj.model.entity.PostEntity;
import com.snsproj.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<LikeEntity, Integer> {
    Optional<LikeEntity> findByUserAndPost(UserEntity user, PostEntity post);

    @Query("select count(l) from LikeEntity l where l.post = :post")
    Integer findAllByPost(@Param("post") PostEntity postEntity);

    long countByPost(PostEntity post);

    @Modifying
    @Query("update LikeEntity l set l.removedAt = now() where l.post = :post")
    void deleteAllByPost(@Param("post") PostEntity post);
}
