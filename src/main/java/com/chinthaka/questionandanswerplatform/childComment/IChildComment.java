package com.chinthaka.questionandanswerplatform.childComment;

import com.chinthaka.questionandanswerplatform.childComment.request.ChildCommentRequestDTO;

public interface IChildComment {

    String addComment(ChildCommentRequestDTO childCommentRequestDTO);

    void addLike(long commentId, boolean like, String userId, String type);

    String deleteChildComment(long id);
}
