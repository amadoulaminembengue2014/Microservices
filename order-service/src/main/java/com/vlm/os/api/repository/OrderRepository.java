package com.vlm.os.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vlm.os.api.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}

