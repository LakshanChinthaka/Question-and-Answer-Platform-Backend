package com.chinthaka.questionandanswerplatform.comment;

import com.chinthaka.questionandanswerplatform.childComment.ChildComment;
import com.chinthaka.questionandanswerplatform.post.Post;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@DynamicUpdate
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "user_id")
    private String userID;

    @Column(name = "timeStamp")
    private LocalDateTime timeStamp;

    @Column(name = "status")
    private Boolean isDelete = false;

    @Column(name = "reply_count")
    private Integer replyCount=0;

    @Column(name = "like_count")
    private Integer likeCount=0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id", nullable=false)
    @JsonBackReference
    private Post post;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ChildComment> childComments;

    public Comment(String comment, LocalDateTime now, Post post, String userID) {
        this.comment = comment;
        this.timeStamp = now;
        this.post = post;
        this.userID = userID;
    }

}
