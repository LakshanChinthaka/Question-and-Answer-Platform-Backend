package com.chinthaka.questionandanswerplatform.comment.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDTO {
    private long postId;
    private String userID;
    private String comment;

}
