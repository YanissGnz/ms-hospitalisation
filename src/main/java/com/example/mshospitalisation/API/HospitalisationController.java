package com.example.mshospitalisation.API;

import com.example.mshospitalisation.DAO.*;
import com.example.mshospitalisation.entities.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api")
@CrossOrigin("*")
public class HospitalisationController {

    @Autowired
    private ActRepository actRepository;

    @Autowired
    private ActTypeRepository actTypeRepository;

    @Autowired
    private LitRepository litRepository;

//    @Autowired
//    private PatientProxy patientProxy;

//    @Autowired
//    private StaffProxy staffProxy;

    @Autowired
    private ChambreRepository chambreRepository;

    @Autowired
    private HospitalisationRepository hospitalisationRepository;


    @PostMapping("/addAct")
    @PostAuthorize("hasAuthority('MANIPULATE_ACT')")
    public Act AddAct(@RequestBody Act act){
        act.setActType(actTypeRepository.findById(act.getIdActType()).get());
//        act.setStaff(staffProxy.getStaff(act.getIdStaff()));
//        act.setPatient(patientProxy.getPatient(act.getIdPatient()));
        act.setLit(litRepository.findById(act.getIdLit()).get());
        return actRepository.save(act);
    }

    @GetMapping("/getAct/{ID}")
    @PostAuthorize("hasAuthority('GET_ACT')")
    public Act  getAct(@PathVariable (value = "id") Long idAct){
        return actRepository.findById(idAct).get();
    }

    @GetMapping("/getActs")
    @PostAuthorize("hasAuthority('GET_ACT')")
    public List<Act> getActs(){
        return actRepository.findAll();
    }


    @PostMapping("/addLit")
    @PostAuthorize("hasAuthority('GET_MEDICINES')")
    public Lit AddLit (@RequestBody Lit lit){
        Chambre chambre = chambreRepository.findById(lit.getIdChambre()).get();
        lit.setChambre(chambre);
        return litRepository.save(lit);
    }

    @GetMapping("/getLit/{id}")
    @PostAuthorize("hasAuthority('GET_MEDICINES')")
    public Lit GetLit (@PathVariable (value = "id") Long idLit){
        return litRepository.findById(idLit).get();
    }

    @PutMapping("/modifiyLit/{id}")
    @PostAuthorize("hasAuthority('MANIPULATE_LIT')")
    public Lit ModifyLit(@PathVariable (value = "id") Long idLit , @RequestBody Lit lit){
        if (litRepository.findById(idLit).isPresent()) {
            lit.setId(idLit);
            return litRepository.save(lit);
        }else {
            return null;
        }
    }
    @GetMapping("/getLits")
    @PostAuthorize("hasAuthority('GET_MEDICINES')")
    public List<Lit> GetLits (){
        return litRepository.findAll();
    }


    @PostMapping("/addChambre")
    @PostAuthorize("hasAuthority('GET_MEDICINES')")
    public Chambre  addChambre(@RequestBody Chambre chambre){
        return  chambreRepository.save(chambre);
    }

    @GetMapping("/getChambre/{id}")
    @PostAuthorize("hasAuthority('GET_MEDICINES')")
    public Chambre  getChambre(@PathVariable(value = "id") Long idChambre ){
        return  chambreRepository.findById(idChambre).get();
    }

    @GetMapping("/getChambres")
    @PostAuthorize("hasAuthority('GET_MEDICINES')")
    public List<Chambre> getChambres(){
        return  chambreRepository.findAll();
    }

    @PostMapping("/addActType")
    @PostAuthorize("hasAuthority('GET_MEDICINES')")
    public ActType AddActType(@RequestBody ActType actType){
        return actTypeRepository.save(actType);
    }

    @GetMapping("/getActType/{id}")
    @PostAuthorize("hasAuthority('GET_ACT')")
    public ActType  getActType(@PathVariable (value = "id") Long idActType){
        return actTypeRepository.findById(idActType).get();
    }

    @GetMapping("/getActTypes")
    @PostAuthorize("hasAuthority('GET_ACT')")
    public List<ActType> getActsTypes(){
        return actTypeRepository.findAll();
    }


    @DeleteMapping("/deleteActType/{id}")
    @PostAuthorize("hasAuthority('MANIPULATE_ACT')")
    public void   deleteActeType(@PathVariable (value = "id") Long idActType){
        actTypeRepository.deleteById(idActType);
    }

    @PostMapping("/hospitaliser")
    @PostAuthorize("hasAuthority('GET_MEDICINES')")
    public Hospitalisation hospitaliser(@RequestBody Hospitalisation hospitalisation){
        hospitalisation.setLit(litRepository.findById(hospitalisation.getIdLit()).get());
//        hospitalisation.setPatient(patientProxy.getPatient(hospitalisation.getIdPatient()));
//        hospitalisation.setInfermier(staffProxy.getStaff(hospitalisation.getIdInfermier()));
//        hospitalisation.setMedecin(staffProxy.getStaff(hospitalisation.getIdMedcin()));

        Lit lit = litRepository.findById(hospitalisation.getIdLit()).get();
//        lit.setPatient(patientProxy.getPatient(hospitalisation.getIdPatient()));
        litRepository.save(lit);

        return  hospitalisationRepository.save(hospitalisation);
    }
}
