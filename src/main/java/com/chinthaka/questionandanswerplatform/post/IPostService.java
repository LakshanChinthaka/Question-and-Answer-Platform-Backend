package com.chinthaka.questionandanswerplatform.post;

import com.chinthaka.questionandanswerplatform.post.request.PostRequestDTO;
import com.chinthaka.questionandanswerplatform.post.response.PostResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPostService {

    String createPost(PostRequestDTO postRequestDTO);

    PostResponseDTO postGetById(long id);

    Page<PostResponseDTO> findAllPost(Pageable pageable);

    String deletePost(long id);

    void addLike(long id, boolean like, String userId, String type);
}
