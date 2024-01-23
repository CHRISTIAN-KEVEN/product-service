package com.example.productService.services;

import com.example.productService.dtos.requestDtos.ItemRequestDto;
import org.springframework.http.ResponseEntity;

public interface ItemService {

    public String listItems(int page, int size);

    String createItem(ItemRequestDto request);

    String getItem(Long itemId);
}
