package com.example.mshospitalisation;

import com.example.mshospitalisation.DAO.ActConsumableRepository;
import com.example.mshospitalisation.DAO.ActMedicineRepository;
import com.example.mshospitalisation.DAO.ActRepository;
import com.example.mshospitalisation.entities.ActConsumable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.xml.crypto.Data;
import java.sql.Date;

@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
@SpringBootApplication
@EnableFeignClients
public class MsHospitalisationApplication implements CommandLineRunner {

    @Autowired
    ActConsumableRepository actConsumableRepository;

    @Autowired
    ActMedicineRepository actMedicineRepository;

    @Autowired
    ActRepository actRepository;
    public static void main(String[] args) {
        SpringApplication.run(MsHospitalisationApplication.class, args);
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void run(String... args) throws Exception {

        //actConsumableRepository.save(new ActConsumable(null,1L,"Con2",2, Date.valueOf("2022-06-12"),null));
       /* actConsumableRepository.deleteAll();
        actMedicineRepository.deleteAll();
        actRepository.deleteAll();*/

    }
}

