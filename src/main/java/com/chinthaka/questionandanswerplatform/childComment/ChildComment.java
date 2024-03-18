package com.chinthaka.questionandanswerplatform.childComment;

import com.chinthaka.questionandanswerplatform.comment.Comment;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@DynamicUpdate
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "child_comment")
public class ChildComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "user_Id")
    private String userID;

    @Column(name = "comment_Id")
    private long commentId;

    @Column(name = "timeStamp")
    private LocalDateTime timeStamp;

    @Column(name = "status")
    private Boolean isDelete = false;

    @Column(name = "like_count")
    private Integer likeCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id", nullable = false)
    @JsonBackReference
    private Comment parentComment;


    public ChildComment(long commentId, String description, String userID, LocalDateTime now,Comment comment) {
        this.commentId = commentId;
        this.userID = userID;
        this.comment = description;
        this.timeStamp = now;
        this.parentComment = comment;
    }

    public String getTimeStampFormatted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm a");
        return this.timeStamp.format(formatter);
    }
}
