package com.chinthaka.questionandanswerplatform.post.response;

import com.chinthaka.questionandanswerplatform.PostType.PostType;
import com.chinthaka.questionandanswerplatform.comment.response.CommentResponseDTO;
import com.chinthaka.questionandanswerplatform.like.UserLike;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDTO {
    private long postId;
    private String userID;
    private String description;
    private PostType type;
    private String postDate;
    private Integer replyCount;
    private Integer likeCount;
    private List<UserLike> userLikeList;
    private List<CommentResponseDTO> comments;
}


