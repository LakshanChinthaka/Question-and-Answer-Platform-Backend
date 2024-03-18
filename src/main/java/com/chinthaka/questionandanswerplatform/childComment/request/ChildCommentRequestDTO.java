package com.chinthaka.questionandanswerplatform.childComment.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChildCommentRequestDTO {
    private String userID;
    private String description;
    private long commentId;
}
