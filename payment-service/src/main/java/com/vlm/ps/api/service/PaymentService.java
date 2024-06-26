package com.vlm.ps.api.service;

import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vlm.ps.api.entity.Payment;
import com.vlm.ps.api.repository.PaymentRepository;

@Service
public class PaymentService {
	
	@Autowired
	private PaymentRepository repository;
	
	private Logger log=LoggerFactory.getLogger(PaymentService.class);
	
	public Payment doPayment(Payment payment) throws JsonProcessingException {
		payment.setPaymentStatus(paymentProcessing());
		payment.setTransactionId(UUID.randomUUID().toString());
		log.info("PaymentService request : {}",new ObjectMapper().writeValueAsString(payment));
		return repository.save(payment);
	}
	
	public String paymentProcessing() {
		//api should be 3rd party payment gateway (paypal,paytm...)
		return new Random().nextBoolean()?"success":"false";
	}

	public Payment findPaymentHistoryByOrderId(int orderId) throws JsonProcessingException {
		Payment payment = repository.findByOrderId(orderId);
		log.info("OrderService findPaymentHistoryByOrderId : {}",new ObjectMapper().writeValueAsString(payment));
		return payment;
	}

}

