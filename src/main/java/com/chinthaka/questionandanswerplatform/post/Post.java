package com.chinthaka.questionandanswerplatform.post;

import com.chinthaka.questionandanswerplatform.PostType.PostType;
import com.chinthaka.questionandanswerplatform.comment.Comment;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Data
@DynamicUpdate
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_Id")
    private String userID;

    @Column(name = "description", length = 5000)
    private String description;

    @Column(name = "duration")
    private LocalDateTime timeStamp;

    @Column(name = "status")
    private Boolean isDelete = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private PostType type;

    @Column(name = "reply_count")
    private Integer replyCount = 0;

    @Column(name = "like_count")
    private Integer likeCount=0;

    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Comment> comments;


    public Post(String userID, String description, PostType type, LocalDateTime timeStamp) {
        this.userID = userID;
        this.description = description;
        this.type = type;
        this.timeStamp = timeStamp;
    }

}
