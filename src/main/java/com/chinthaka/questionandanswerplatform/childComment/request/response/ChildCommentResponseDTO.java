package com.chinthaka.questionandanswerplatform.childComment.request.response;

import com.chinthaka.questionandanswerplatform.like.UserLike;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChildCommentResponseDTO {
    private Long id;
    private String comment;
    private String userID;
    private long commentId;
    private String timeStamp;
    private Integer likeCount;
    private List<UserLike> childLikeList;
}
