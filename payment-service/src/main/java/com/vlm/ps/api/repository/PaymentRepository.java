package com.vlm.ps.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vlm.ps.api.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

	Payment findByOrderId(int orderId);

}
