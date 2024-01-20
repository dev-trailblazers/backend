package com.growth.community.controller;

import com.growth.community.domain.comment.dto.CommentDto;
import com.growth.community.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
