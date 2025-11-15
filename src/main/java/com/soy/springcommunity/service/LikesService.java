package com.soy.springcommunity.service;

import com.soy.springcommunity.dto.LikesSimpleResponse;

public interface LikesService {
    LikesSimpleResponse like(Long contentId, Long userId);
    LikesSimpleResponse unlike(Long contentId, Long userId);
}

