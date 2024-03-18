package com.chinthaka.questionandanswerplatform.childComment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface ChildCommentRepo extends JpaRepository<ChildComment,Long> {
}
