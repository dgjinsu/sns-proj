package com.snsproj.service;

import com.snsproj.exception.ErrorCode;
import com.snsproj.exception.SimpleSnsApplicationException;
import com.snsproj.fixture.PostEntityFixture;
import com.snsproj.model.entity.PostEntity;
import com.snsproj.model.entity.UserEntity;
import com.snsproj.repository.PostRepository;
import com.snsproj.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @MockBean
    private PostRepository postRepository;
    @MockBean
    private UserRepository userRepository;

    @Test
    void 포스트작성_성공() {
        String title = "title";
        String body = "body";
        String userName = "userName";

        //mocking
        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(mock(UserEntity.class)));
        when(postRepository.save(any())).thenReturn(mock(PostEntity.class));

        Assertions.assertDoesNotThrow(() -> postService.create(title, body, userName));

    }

    @Test
    void 포스트작성_요청유저_존재하지않을때() {
        String title = "title";
        String body = "body";
        String userName = "userName";

        //mocking
        when(userRepository.findByUserName(userName)).thenReturn(Optional.empty());
        when(postRepository.save(any())).thenReturn(mock(PostEntity.class));

        SimpleSnsApplicationException e = Assertions.assertThrows(SimpleSnsApplicationException.class, () -> postService.create(title, body, userName));
        Assertions.assertEquals(ErrorCode.USER_NOT_FOUND, e.getErrorCode());
    }

    @Test
    void 포스트수정_성공() {
        String title = "title";
        String body = "body";
        String userName = "userName";
        Integer postId = 1;


        //mocking
        PostEntity postEntity = PostEntityFixture.get(userName, postId, 1);
        UserEntity userEntity = postEntity.getUser();

        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(userEntity));
        when(postRepository.findById(postId)).thenReturn(Optional.of(postEntity));
        when(postRepository.saveAndFlush(any())).thenReturn(postEntity);

        Assertions.assertDoesNotThrow(() -> postService.modify(title, body, userName, postId));

    }


    @Test
    void 포스트수정_존재하지_않을때() {
        String title = "title";
        String body = "body";
        String userName = "userName";
        Integer postId = 1;


        //mocking
        PostEntity postEntity = PostEntityFixture.get(userName, postId, 1);
        UserEntity userEntity = postEntity.getUser();

        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(userEntity));
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        SimpleSnsApplicationException e = Assertions.assertThrows(SimpleSnsApplicationException.class, () -> postService.modify(title, body, userName, postId));
        Assertions.assertEquals(ErrorCode.POST_NOT_FOUND, e.getErrorCode());
    }

    @Test
    void 포스트수정_권한이_없을때() {
        String title = "title";
        String body = "body";
        String userName = "userName";
        Integer postId = 1;


        //mocking
        PostEntity postEntity = PostEntityFixture.get(userName, postId, 1);
        UserEntity writer = PostEntityFixture.get("userName1", 1, 2).getUser();

        when(userRepository.findByUserName(userName)).thenReturn(Optional.of(writer));
        when(postRepository.findById(postId)).thenReturn(Optional.of(postEntity));

        SimpleSnsApplicationException e = Assertions.assertThrows(SimpleSnsApplicationException.class, () -> postService.modify(title, body, userName, postId));
        Assertions.assertEquals(ErrorCode.INVALID_PERMISSION, e.getErrorCode());
    }
}
