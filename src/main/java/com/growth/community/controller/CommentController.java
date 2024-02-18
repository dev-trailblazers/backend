package com.growth.community.controller;

import com.growth.community.domain.comment.dto.CommentDto;
import com.growth.community.domain.comment.dto.RequestCommentDto;
import com.growth.community.domain.user.dto.Principal;
import com.growth.community.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/comments")
@RestController
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> postComment(@RequestBody @Valid RequestCommentDto dto,
                                            @AuthenticationPrincipal Principal principal){
        commentService.createComment(dto, principal.id());
        return ResponseEntity.created(URI.create("/articles/id/" + dto.articleId())).build();
    }

    @PutMapping("/")
    public ResponseEntity<Void> putComment(@RequestBody CommentDto dto,
                                           @AuthenticationPrincipal Principal principal){
        commentService.updateComment(dto, principal.id());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/id/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId,
                                              @AuthenticationPrincipal Principal principal){
        commentService.deleteComment(commentId, principal.id());
        return ResponseEntity.ok().build();
    }


}
