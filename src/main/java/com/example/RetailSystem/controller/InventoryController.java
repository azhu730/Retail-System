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

    @GetMapping("/items/tcin/{tcin}")
    public Optional<Item> getItemByTcin(@PathVariable("tcin") Integer tcin) {
        return this.itemRepository.findByTcin(tcin);
    }

    @GetMapping("/items/dpci/{dpci}")
    public Optional<Item> getItemByDpci(@PathVariable("dpci") String dpci) {
        return this.itemRepository.findByDpci(dpci);
    }

    @PostMapping("/items")
    public Item addItem(@RequestBody Item item) {
        Item newItem = this.itemRepository.save(item);
        return newItem;
    }

    @PutMapping("/items/tcin/{tcin}")
    public Item updateItem(@PathVariable("tcin") Integer tcin, @RequestBody Item i) {
        Optional<Item> checkItem = this.itemRepository.findByTcin(tcin);

        if (!checkItem.isPresent()) {
            return null;
        }

        Item itemToUpdate = checkItem.get();

        if (i.getDpci() != null) {
            itemToUpdate.setDpci(i.getDpci());
        }

        if (i.getDepartmentNo() != null) {
            itemToUpdate.setDepartmentNo(i.getDepartmentNo());
        }

        if (i.getClassNo() != null) {
            itemToUpdate.setClassNo(i.getClassNo());
        }

        if (i.getItemNo() != null) {
            itemToUpdate.setItemNo(i.getItemNo());
        }

        if (i.getUpc() != null) {
            itemToUpdate.setUpc(i.getUpc());
        }

        if (i.getName() != null) {
            itemToUpdate.setName(i.getName());
        }

        if (i.getPrice() != null) {
            itemToUpdate.setPrice(i.getPrice());
        }

        if (i.getOnHand() != null) {
            itemToUpdate.setOnHand(i.getOnHand());
        }

        if (i.getOnSale() != null) {
            itemToUpdate.setOnSale(i.getOnSale());
        }

        if (i.getFloorLocation() != null) {
            itemToUpdate.setFloorLocation(i.getFloorLocation());
        }

        Item updatedItem = this.itemRepository.save(itemToUpdate);
        return updatedItem;
    }

    @DeleteMapping("items/dpci/{dpci}")
    public Item deleteItem(@PathVariable("dpci") String dpci) {
        Optional<Item> checkItem = this.itemRepository.findByDpci(dpci);

        if (!checkItem.isPresent()) {
            return null;
        }

        Item itemToDelete = checkItem.get();
        this.itemRepository.delete(itemToDelete);
        return itemToDelete;
    }
}
