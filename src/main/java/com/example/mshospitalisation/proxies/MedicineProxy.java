package com.example.mshospitalisation.proxies;

import com.example.mshospitalisation.model.Medicine;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ms-pharmacy")
@LoadBalancerClient(name = "ms-pharmacy")
public interface MedicineProxy {
    @GetMapping("api/medicines/{id}")
    Medicine getMedicine(@RequestHeader("Authorization") String token, @PathVariable("id") Long id);

    @GetMapping("api/consommables/{id}")
    Medicine getConsumable(@RequestHeader("Authorization") String token, @PathVariable("id") Long id);

    @PutMapping("api/medicines/{id}/change-qte")
    void editMedCount(@RequestHeader("Authorization") String token,@PathVariable("id") Long idMed, @RequestParam("qte") int qte, @RequestParam("idStaff") Long idStaff);

    @PutMapping("api/consommables/{id}/change-qte")
    void editConCount(@RequestHeader("Authorization") String token,@PathVariable("id") Long idMed, @RequestParam("qte") int qte, @RequestParam("idStaff") Long idStaff);

}
