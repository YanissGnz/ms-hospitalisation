package com.example.mshospitalisation.proxies;

import com.example.mshospitalisation.model.Patient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ms-patient")
@LoadBalancerClient(name = "ms-patient")
public interface PatientProxy {
    @GetMapping("api/patients/info/{id}")
    Patient getPatientInfo(@RequestHeader("Authorization") String token, @PathVariable("id") Long id);

    @PutMapping ("api/patients/hospitalze/{id}")
    void hospitalizePatient(@RequestHeader("Authorization") String token, @PathVariable("id") Long id,@RequestParam("idStaff") Long idStaff);


}
