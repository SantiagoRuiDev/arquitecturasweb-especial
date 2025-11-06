package com.arqui.accountservice.feignClients;


import com.arqui.accountservice.dto.request.RechargeRequestDTO;
import com.arqui.accountservice.dto.response.RechargeResultDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// Aqui se comunica con el servicio de Pagos (deberia crear metodos de pagos y buscar)
@FeignClient(name="payment-service", url="http://localhost:8003/payment-method")
public interface PaymentFeignClient {
    @PostMapping("/charge/{id}")
    RechargeResultDTO charge(@PathVariable Integer id, @RequestBody RechargeRequestDTO req);
}
