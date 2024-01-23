package com.example.productService.controllers;

import com.example.productService.dtos.requestDtos.ItemRequestDto;
import com.example.productService.models.TItem;
import com.example.productService.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("products")
public class ItemController {

   private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }
    @GetMapping("")
    public ResponseEntity<String> listProducts(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "20") int size) {
        return new ResponseEntity<>(itemService.listItems(page, size), HttpStatus.OK);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<String> getItem(@PathVariable Long itemId) {
        return new ResponseEntity<>(itemService.getItem(itemId), HttpStatus.OK);
    }

    @PostMapping("create-item")
    public ResponseEntity<String> createItem(@RequestBody ItemRequestDto request) {
        return new ResponseEntity<>(itemService.createItem(request), HttpStatus.CREATED);
    }
}
