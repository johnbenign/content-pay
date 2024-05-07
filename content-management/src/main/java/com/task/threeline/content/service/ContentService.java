package com.task.threeline.content.service;

import com.task.threeline.content.dto.BuyContentRequest;
import com.task.threeline.content.dto.ContentRequest;
import com.task.threeline.content.dto.GeneralResponse;

public interface ContentService {
    GeneralResponse postContent(ContentRequest request);

    GeneralResponse buyContent(BuyContentRequest request);
}
