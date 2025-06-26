package com.youtube.stage.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    private String content;
    private LocalDateTime postedAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @JoinColumn(name = "video_id")
    @ManyToOne
    private Video video;

    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
    private List<Comment> replies;

    public Comment(Long id, String content, LocalDateTime postedAt, LocalDateTime updatedAt, User author, Video video, Comment parentComment, List<Comment> replies) {
        this.id = id;
        this.content = content;
        this.postedAt = postedAt;
        this.updatedAt = updatedAt;
        this.author = author;
        this.video = video;
        this.parentComment = parentComment;
        this.replies = replies;
    }

    public Comment() {

    }

    @PrePersist
    protected void onCreate() {
        this.postedAt = LocalDateTime.now();
        this.updatedAt = this.postedAt;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(LocalDateTime postedAt) {
        this.postedAt = postedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public List<Comment> getReplies() {
        return replies;
    }

    public void setReplies(List<Comment> replies) {
        this.replies = replies;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id) && Objects.equals(content, comment.content) && Objects.equals(postedAt, comment.postedAt) && Objects.equals(updatedAt, comment.updatedAt) && Objects.equals(author, comment.author) && Objects.equals(video, comment.video) && Objects.equals(parentComment, comment.parentComment) && Objects.equals(replies, comment.replies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, postedAt, updatedAt, author, video, parentComment, replies);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", postedAt=" + postedAt +
                ", updatedAt=" + updatedAt +
                ", author=" + author +
                ", video=" + video +
                ", parentComment=" + parentComment +
                ", replies=" + replies +
                '}';
    }
}
