package com.youtube.stage.service;

import com.youtube.stage.model.Comment;
import com.youtube.stage.model.User;
import com.youtube.stage.model.Video;
import com.youtube.stage.repository.CommentRepository;
import com.youtube.stage.repository.UserRepository;
import com.youtube.stage.repository.VideoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, VideoRepository videoRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.videoRepository = videoRepository;
    }

    public List<Comment> getCommentsByVideoId(Long videoId) {
        return commentRepository.findByVideoId(videoId);
    }

    public Comment addComment(Long videoId, String username, String text, Long parentCommentId) {
        Video video = videoRepository.findById(videoId).orElseThrow(() -> new RuntimeException("Video non trovato"));
        User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("Utente non trovato"));

        Comment comment = new Comment();
        comment.setVideo(video);
        comment.setAuthor(user);
        comment.setContent(text);

        if (parentCommentId != null) {
            Comment parent = commentRepository.findById(parentCommentId).orElseThrow(() -> new RuntimeException("Commento genitore non trovato"));
            comment.setParentComment(parent);
        }

        return commentRepository.save(comment);
    }

    public void deleteComment(Long commentId, String username) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("Commento non trovato"));
        if (!comment.getAuthor().getEmail().equals(username)) {
            throw new RuntimeException("Non autorizzato a cancellare questo commento");
        }
        commentRepository.delete(comment);
    }

    public Comment updateComment(Long commentId, String username, String newText) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Commento non trovato"));

        if (!comment.getAuthor().getEmail().equals(username)) {
            throw new RuntimeException("Non autorizzato a modificare questo commento");
        }

        comment.setContent(newText);
        comment.setUpdatedAt(LocalDateTime.now());

        return commentRepository.save(comment);
    }

}

