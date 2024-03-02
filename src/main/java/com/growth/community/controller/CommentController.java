package com.growth.community.controller;

import com.growth.community.domain.comment.dto.CommentDto;
import com.growth.community.domain.comment.dto.RequestCommentDto;
import com.growth.community.domain.user.dto.Principal;
import com.growth.community.domain.validation.ByteLength;
import com.growth.community.domain.validation.ValidationMessage;
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
        commentService.createComment(dto, principal.getId());
        return ResponseEntity.created(URI.create("/articles/id/" + dto.articleId())).build();
    }

    @PutMapping("/id/{commentId}")
    public ResponseEntity<Void> putComment(@PathVariable Long commentId,
                                           @RequestBody @ByteLength(min = 1, max = 1500, message = ValidationMessage.COMMENT_CONTENT_LENGTH) String content,
                                           @AuthenticationPrincipal Principal principal){
        commentService.updateComment(content, commentId, principal.getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/id/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId,
                                              @AuthenticationPrincipal Principal principal){
        commentService.deleteComment(commentId, principal.getId());
        return ResponseEntity.ok().build();
    }


}
