package com.chinthaka.questionandanswerplatform.comment.response;

import com.chinthaka.questionandanswerplatform.childComment.request.response.ChildCommentResponseDTO;
import com.chinthaka.questionandanswerplatform.like.UserLike;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDTO {
    private Long id;
    private String comment;
    private String userID;
    private String timeStamp;
    private Integer replyCount;
    private Integer likeCount;
    private List<UserLike> commentLikeList;
    private List<ChildCommentResponseDTO> childComment;
}
