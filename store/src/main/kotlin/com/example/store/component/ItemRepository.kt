package com.example.store.component

import com.example.store.model.Item
import org.springframework.data.jpa.repository.JpaRepository

interface ItemRepository: JpaRepository<Item, Long> {

}