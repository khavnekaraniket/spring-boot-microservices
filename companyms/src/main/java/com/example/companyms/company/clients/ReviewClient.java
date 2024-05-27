package com.example.companyms.company.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "REVIEWMS")
public interface ReviewClient {
    @GetMapping("/reviews/avarageRating")
    Double getAvarageRatingForCompany(@RequestParam("companyId") Long companyId);
}
