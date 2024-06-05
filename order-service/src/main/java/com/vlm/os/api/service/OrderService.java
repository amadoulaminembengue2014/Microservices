package com.vlm.os.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vlm.os.api.common.Payment;
import com.vlm.os.api.common.TransactionRequest;
import com.vlm.os.api.common.TransactionResponse;
import com.vlm.os.api.entity.Order;
import com.vlm.os.api.repository.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private RestTemplate template;
	
	private Logger log=LoggerFactory.getLogger(OrderService.class);
	
	public TransactionResponse saveOrder(TransactionRequest request) throws JsonProcessingException {
		String response = "";
		Order order = request.getOrder();
		Payment payment = request.getPayment();
		payment.setOrderId(order.getId());
		payment.setAmount(order.getPrice());
		
		log.info("OrderService request : {}",new ObjectMapper().writeValueAsString(request));
		//rest call
		Payment paymentResponse = template.postForObject("http://PAYMENT-SERVICE/payment/doPayment", payment, Payment.class);
		
		log.info("Payment-service response from OrderService Rest call : {}",new ObjectMapper().writeValueAsString(paymentResponse));
		response = paymentResponse.getPaymentStatus().equals("success")?"payment processing successful and order placed":"there is a failure in payment api , order added to card";
		
		repository.save(order);
		return new TransactionResponse(order, paymentResponse.getAmount(), paymentResponse.getTransactionId(), response);
	}

}
