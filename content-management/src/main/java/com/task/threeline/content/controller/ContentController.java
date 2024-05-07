package com.task.threeline.content.controller;

import com.task.threeline.content.constant.ApiRoute;
import com.task.threeline.content.dto.BuyContentRequest;
import com.task.threeline.content.dto.ContentRequest;
import com.task.threeline.content.dto.GeneralResponse;
import com.task.threeline.content.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/content")
public class ContentController {
    private final ContentService service;

    @PostMapping(ApiRoute.POST_CONTENT)
    public GeneralResponse postContent(@RequestBody @Valid ContentRequest request){
        return service.postContent(request);
    }

    @PostMapping(ApiRoute.BUY_CONTENT)
    public GeneralResponse buyContent(@RequestBody @Valid BuyContentRequest request){
        return service.buyContent(request);
    }
}
