package com.example.finalproject.service;

import com.example.finalproject.domain.dto.post.PostGetResponse;
import com.example.finalproject.domain.dto.post.PostWriteAndUpdateRequest;
import com.example.finalproject.domain.entity.Post;
import com.example.finalproject.domain.entity.User;
import com.example.finalproject.exception.AppException;
import com.example.finalproject.exception.ErrorCode;
import com.example.finalproject.respository.LikeRepository;
import com.example.finalproject.respository.PostRepository;
import com.example.finalproject.respository.UserRepository;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostServiceTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final PostRepository postRepository = mock(PostRepository.class);
    private final LikeRepository likeRepository = mock(LikeRepository.class);

    Post mockPost;
    User mockUser;
    PostService postService;

    @BeforeEach()
    void setUp() {
        postService = new PostService(postRepository, userRepository);
        mockPost = mock(Post.class);
        mockUser = mock(User.class);
    }

    @Nested
    @DisplayName("포스트 등록")
    class CreatePostTest {
        @Test
        @DisplayName("포스트 등록 성공")
        void createPost_success() {
            when(userRepository.findByUsername(any())).thenReturn(Optional.of(mockUser));
            when(postRepository.save(any())).thenReturn(mockPost);

            PostWriteAndUpdateRequest postRequest = new PostWriteAndUpdateRequest("title", "body");
            Assertions.assertDoesNotThrow(() -> postService.create(postRequest, "username"));
        }

        @Test
        @DisplayName("포스트 등록 실패: 로그인 하지 않은 경우")
        void CreatePost_fail_1() {
            when(userRepository.findByUsername(any())).thenThrow(new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage()));

            AppException exception = assertThrows(AppException.class, () -> postService.create(any(), "username"));
            assertEquals(ErrorCode.INVALID_PERMISSION, exception.getErrorCode());
        }


        @Test
        @DisplayName("포스트 등록 실패: 해당 아이디가 존재하지 않는 경우")
        void createPost_fail_2() {

            PostWriteAndUpdateRequest postRequest = new PostWriteAndUpdateRequest("title", "body");

            when(userRepository.findByUsername(any())).thenThrow(new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));
            AppException exception = assertThrows(AppException.class, () -> postService.create(any(), "anotherUsername"));
            assertEquals(ErrorCode.INVALID_PERMISSION, exception.getErrorCode());
        }
    }

    @Test
    @DisplayName("포스트 상세 조회 성공")
    void find_post_success() {
        Long testPostId = 1L;

        when(postRepository.findById(testPostId)).thenReturn(Optional.of(mockPost));

        //PostGetResponse post = postService.getPostDetail(1L);
        //assertEquals(mockPost, post);
    }


    @Nested
    @DisplayName("포스트 수정")
    class UpdatePostTest {
        @Test
        @DisplayName("포스트 수정 실패 - 해당 포스트가 존재하지 않는 경우")
        void update_post_fail_1() {
            when(userRepository.findByUsername(any()))
                    .thenReturn(Optional.of(mockUser));

            when(postRepository.findById(any()))
                    .thenThrow(new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));

            PostWriteAndUpdateRequest postRequest = new PostWriteAndUpdateRequest("title", "body");
            AppException exception = Assertions.assertThrows(AppException.class, () -> postService.update(mockPost.getPostId(), postRequest, "username"));
            assertEquals(ErrorCode.POST_NOT_FOUND, exception.getErrorCode());
        }

        @Test
        @DisplayName("포스트 수정 실패 - 해당 유저가 존재하지 않는 경우")
        void updatePost_fail_2() {
            when(userRepository.findByUsername(any()))
                    .thenReturn(Optional.empty());

            when(postRepository.findById(any()))
                    .thenThrow(new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));

            PostWriteAndUpdateRequest postRequest = new PostWriteAndUpdateRequest("title", "body");
            AppException exception = Assertions.assertThrows(AppException.class, () -> postService.update(mockPost.getPostId(), postRequest, "username"));
            assertEquals(ErrorCode.USERNAME_NOT_FOUND, exception.getErrorCode());
        }


        @Test
        @DisplayName("포스트 수정 실패 - 작성자 불일치")
        void updatePost_fail_3() {
            when(userRepository.findByUsername(any()))
                    .thenReturn(Optional.of(mockUser));

            when(postRepository.findById(any())).thenReturn(Optional.of(mockPost));

            User anotherMockUser = mock(User.class);
            when(userRepository.findByUsername(any())).thenReturn(Optional.of(anotherMockUser));

            AppException exception = Assertions.assertThrows(
                    AppException.class,
                    () -> postService.update(mockPost.getPostId(), new PostWriteAndUpdateRequest("title", "body"), anotherMockUser.getUsername()));
            assertEquals(ErrorCode.INVALID_PERMISSION, exception.getErrorCode());
        }
    }

    @Nested
    @DisplayName("포스트 삭제 테스트")
    class DeletePostTest {
        @Test
        @DisplayName("Failed to delete post: User does not exist")
        public void deleteFail_1() {

        }

        @Test
        @DisplayName("Failed to delete post: Post does not exist")
        public void deleteFail_2() {
            when(postRepository.findById(mockUser.getUserId())).thenReturn(Optional.empty());
            AppException exception = Assertions.assertThrows(AppException.class, () -> postService.delete(mockPost.getPostId(), mockUser.getUsername()));
            assertEquals(ErrorCode.POST_NOT_FOUND, exception.getErrorCode());
        }
    }

    @Test
    @DisplayName("Failed to delete post: Current user is not the author of the post ")
    public void deleteFail_3() {
    }

}


