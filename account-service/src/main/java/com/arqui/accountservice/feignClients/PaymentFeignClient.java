package com.arqui.accountservice.feignClients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

// Aqui se comunica con el servicio de Pagos (deberia crear metodos de pagos y buscar)
@FeignClient(name="service-payment", url="http://localhost:8003/payment-methods")
public interface PaymentFeignClient {

    @PostMapping
    Boolean pay(@PathVariable Integer id);
}
