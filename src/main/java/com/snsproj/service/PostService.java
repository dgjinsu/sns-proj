package com.snsproj.service;

import com.snsproj.exception.ErrorCode;
import com.snsproj.exception.SimpleSnsApplicationException;
import com.snsproj.model.Comment;
import com.snsproj.model.Post;
import com.snsproj.model.entity.CommentEntity;
import com.snsproj.model.entity.LikeEntity;
import com.snsproj.model.entity.PostEntity;
import com.snsproj.model.entity.UserEntity;
import com.snsproj.repository.CommentRepository;
import com.snsproj.repository.LikeRepository;
import com.snsproj.repository.PostRepository;
import com.snsproj.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;

    public void create(String title, String body, String userName) {
        UserEntity userEntity = getUserOrException(userName);


        PostEntity saved = postRepository.save(PostEntity.of(title, body, userEntity));
    }

    public Post modify(String title, String body, String userName, Integer postId) {
        UserEntity userEntity = getUserOrException(userName);


        PostEntity postEntity = getPostOrException(postId);


        if (postEntity.getUser() != userEntity) {
            throw new SimpleSnsApplicationException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission with %s", userName, postId));
        }

        postEntity.setTitle(title);
        postEntity.setBody(body);
        return Post.fromEntity(postRepository.saveAndFlush(postEntity));
    }

    public void delete(String userName, Integer postId) {
        UserEntity userEntity = getUserOrException(userName);


        PostEntity postEntity = getPostOrException(postId);


        if (postEntity.getUser() != userEntity) {
            throw new SimpleSnsApplicationException(ErrorCode.INVALID_PERMISSION, String.format("%s has no permission with %s", userName, postId));
        }

        postRepository.delete(postEntity);
    }

    // entity mapping
    public Page<Post> list(Pageable pageable) {
        return postRepository.findAll(pageable).map(Post::fromEntity);
    }

    public Page<Post> my(String userName, Pageable pageable) {
        UserEntity userEntity = getUserOrException(userName);

        return postRepository.findAllByUser(userEntity, pageable).map(Post::fromEntity);
    }

    public void like(Integer postId, String userName) {
        UserEntity userEntity = getUserOrException(userName);


        PostEntity postEntity = getPostOrException(postId);


        //check like -> throw
        likeRepository.findByUserAndPost(userEntity, postEntity).ifPresent(it -> {
            throw new SimpleSnsApplicationException(ErrorCode.ALREADY_LIKED, String.format("userName %s already like post %d", userName, postId));
        });

        //저장
        likeRepository.save(LikeEntity.of(userEntity, postEntity));
    }

    public int likeCount(Integer postId) {
        PostEntity postEntity = getPostOrException(postId);


        return likeRepository.findAllByPost(postEntity);
    }

    public void comment(Integer postId, String userName, String comment) {
        UserEntity userEntity = getUserOrException(userName);
        PostEntity postEntity = getPostOrException(postId);

        //comment save
        commentRepository.save(CommentEntity.of(comment, postEntity, userEntity));
    }

    public Page<Comment> getComments(Integer postId, Pageable pageable) {
        PostEntity postEntity = getPostOrException(postId);
        return commentRepository.findAllByPost(postEntity, pageable).map(Comment::fromEntity);
    }


    private PostEntity getPostOrException(Integer postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new SimpleSnsApplicationException(ErrorCode.POST_NOT_FOUND, String.format("%s not founded", postId)));
    }
    private UserEntity getUserOrException(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new SimpleSnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not founded", userName)));
    }
}
