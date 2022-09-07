package com.example.RetailSystem.controller;

import java.lang.Iterable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.RetailSystem.model.Item;
import com.example.RetailSystem.repository.ItemRepository;

@RestController
public class InventoryController {

    private final ItemRepository itemRepository;

    public InventoryController(final ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping("/items")
    public Iterable<Item> getAllItems() {
        return this.itemRepository.findAll();
    }

    @GetMapping("/items/{dpci}")
    public Optional<Item> getItemByDpci(@PathVariable("dpci") Long dpci) {
        return this.itemRepository.findByDpci(dpci);
    }

    @PostMapping("/items")
    public Item addItem(@RequestBody Item item) {
        Item newItem = this.itemRepository.save(item);
        return newItem;
    }


}
