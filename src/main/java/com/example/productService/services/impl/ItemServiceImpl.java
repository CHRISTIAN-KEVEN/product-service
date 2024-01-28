package com.example.productService.services.impl;

import com.example.productService.dtos.requestDtos.ItemRequestDto;
import com.example.productService.dtos.responseDtos.ItemResponseDto;
import com.example.productService.models.TItem;
import com.example.productService.repositories.ItemRepository;
import com.example.productService.services.ItemService;
import com.example.productService.utilities.Utilities;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepo;
    private final ObjectMapper objectMapper;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepo,
                           ObjectMapper objectMapper) {
        this.itemRepo = itemRepo;
        this.objectMapper = objectMapper;
    }

    public String listItems(int page, int size) {
        Page<TItem> list = itemRepo.findAll(PageRequest.of(page, size, Sort.Direction.ASC));
        List<ItemResponseDto> response = list.getContent().stream().map(item -> buildItemResponseObject(item)).collect(Collectors.toList());
        return Utilities.dataReturn(response);
    }

    @Override
    public String createItem(ItemRequestDto request) {

        log.info("Creating new item {}", request);
        TItem tItem = new TItem();
        tItem.setDbPrice(request.getPrice());
        tItem.setQuantity(request.getStock());
        tItem.setStrName(request.getName());
        tItem.setLgBusinessId(request.getBusinessId());

        itemRepo.save(tItem);
        log.info("Item created successfully {}", tItem.getLgId());

        return "Item created successfully";

    }

    @Override
    public String getItem(Long itemId) {

        try {
            Optional<TItem> optItem = itemRepo.findById(itemId);
            if(optItem.isEmpty()) {
                log.info("No item with ID {} found !", itemId);
                return Utilities.errorMessageReturn("No item with ID " + itemId + " found !");
            }

            return Utilities.dataReturn(objectMapper.writeValueAsString(buildItemResponseObject(optItem.get())));
        } catch (Exception ex) {
            log.error("Failed to get item details ", ex);
            return Utilities.errorMessageReturn("Failed to get item details");
        }
    }

    @Override
    public String updateItemStock(String request) {

        try {
            log.info("Updating item {}'s stock by ");
            JSONObject requestPayload = new JSONObject(request);
            long itemId = requestPayload.getLong("itemId");
            int qtyToUpdateBy = requestPayload.getInt("qtyToUpdateBy");
            log.info("Updating item {}'s stock by ", qtyToUpdateBy);

            Optional<TItem> optItem = itemRepo.findById(itemId);
            if(optItem.isEmpty()) {
                log.info("Item with ID {} not found ! ", itemId);
                return Utilities.errorMessageReturn("Item with ID " + itemId + " not found ! ");
            }
            TItem tItem = optItem.get();
            int newQty = tItem.getQuantity() + qtyToUpdateBy;
            tItem.setQuantity(newQty < 0 ? 0 : newQty);
            itemRepo.save(tItem);
            log.info("Item quantity updated successfully !");
            return Utilities.successMessageReturn("Item quantity updated successfully !");

        }catch (Exception ex) {
            log.error("Failed to update item {}'s stock", ex);
            return Utilities.errorMessageReturn("Failed to update item {}'s stock");
        }
    }


    public ItemResponseDto buildItemResponseObject(TItem item) {

        ItemResponseDto itemResponse = new ItemResponseDto();
        itemResponse.setId(item.getLgId());
        itemResponse.setBusiness(item.getLgBusinessId());
        itemResponse.setName(item.getStrName());
        itemResponse.setPrice(item.getDbPrice());
        itemResponse.setQty(item.getQuantity());

        return itemResponse;
    }
}
