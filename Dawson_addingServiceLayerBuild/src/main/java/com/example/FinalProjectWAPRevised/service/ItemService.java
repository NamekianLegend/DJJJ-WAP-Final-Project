package com.example.FinalProjectWAPRevised.service;

import com.example.FinalProjectWAPRevised.model.Item;
import com.example.FinalProjectWAPRevised.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }


    //-----------------------------ITEMS-----------------------------

    // load available items
    public List<Item> findAvailableItems(){
        return itemRepository.findAll().stream().filter(item -> !item.isBought()).toList();
    }

    // buy item
    @Transactional
    public void buyItem(Long itemId){
        Item item = itemRepository.findById(itemId).orElse(null);
        if (item != null){
            item.setBought(true);
            itemRepository.save(item);
        }
    }

    // return item
    @Transactional
    public void returnItem(Long itemId){
        Item item = itemRepository.findById(itemId).orElse(null);
        if(item != null){
            item.setBought(false); // error here

            // update item with new isBought value
            itemRepository.save(item);
        }
    }

}
