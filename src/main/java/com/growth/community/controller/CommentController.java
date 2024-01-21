package com.growth.community.controller;

import com.growth.community.domain.comment.dto.CommentDto;
import com.growth.community.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/comments")
@RestController
public class CommentController {
    private final CommentService commentService;


    // TODO: dto 객체를 요청 시 필요한 필드만 가진 requestDto로 변경
    @PostMapping
    public ResponseEntity<Void> saveComment(@RequestBody CommentDto dto){
        commentService.saveComment(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<Void> updateComment(@RequestBody CommentDto dto){
        commentService.updateComment(dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id){
        commentService.deleteCommentById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
