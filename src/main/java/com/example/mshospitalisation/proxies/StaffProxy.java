package com.example.mshospitalisation.proxies;


import com.example.mshospitalisation.model.Staff;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;


@FeignClient(name = "ms-staff")
@LoadBalancerClient(name = "ms-staff")
public interface StaffProxy {
    @GetMapping("auth/staffs/{id}")
    Staff getStaff(@RequestHeader("Authorization") String token, @PathVariable("id") Long id);
}