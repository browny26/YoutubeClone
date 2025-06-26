package com.youtube.stage.controller;

import com.youtube.stage.model.Comment;
import com.youtube.stage.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // Recupera commenti di un video
    @GetMapping("/video/{videoId}")
    public List<Comment> getCommentsByVideo(@PathVariable Long videoId) {
        return commentService.getCommentsByVideoId(videoId);
    }

    // Aggiungi commento (usa JWT per identificare utente)
    @PostMapping("/video/{videoId}")
    public ResponseEntity<Comment> addComment(
            @PathVariable Long videoId,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody Map<String, Object> payload) {

        String text = (String) payload.get("text");
        Long parentCommentId = payload.get("parentCommentId") != null ? Long.valueOf(payload.get("parentCommentId").toString()) : null;

        Comment comment = commentService.addComment(videoId, userDetails.getUsername(), text, parentCommentId);
        return ResponseEntity.ok(comment);
    }

    // Cancella commento (solo autore)
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetails userDetails) {

        commentService.deleteComment(commentId, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Comment> updateComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody Map<String, String> payload) {

        String newText = payload.get("text");
        Comment updatedComment = commentService.updateComment(commentId, userDetails.getUsername(), newText);
        return ResponseEntity.ok(updatedComment);
    }

}

