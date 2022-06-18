package com.example.mshospitalisation.API;

import com.example.mshospitalisation.DAO.*;
import com.example.mshospitalisation.entities.*;
import com.example.mshospitalisation.model.Staff;
import com.example.mshospitalisation.proxies.MedicineProxy;
import com.example.mshospitalisation.proxies.PatientProxy;
import com.example.mshospitalisation.proxies.StaffProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
@RestController
@RequestMapping("api")
@CrossOrigin("*")
public class HospitalisationController {

    @Autowired
    private ActRepository actRepository;

    @Autowired
    private BedRepository bedRepository;

    @Autowired
    private PatientProxy patientProxy;

    @Autowired
    private StaffProxy staffProxy;

    @Autowired
    private MedicineProxy medicineProxy;

    @Autowired
    private ChambreRepository roomRepository;

    @Autowired
    private HospitalisationRepository hospitalisationRepository;

    @Autowired
    private PlanedActRepository planedActRepository;

    @Autowired
    private ActMedicineRepository actMedicineRepository;
    @Autowired
    private ActConsumableRepository actConsumableRepository;

    @Autowired
    private PlanedActConsumableRepo planedActConsumableRepo;

    @Autowired
    private PlanedActMedicineRepo planedActMedicineRepo;
    @PostMapping("/hospitalization/acts")
    @PostAuthorize("hasAuthority('ADD_HOSPITALISATION_ACTS')")
    public Act AddAct(@RequestHeader("Authorization") String token, @RequestBody Act act) {
        List<ActMedicine> medicines = act.getMedicines();
        List<ActConsumable> consumables = act.getConsumables();
        Act savedAct = actRepository.save(act);
        consumables.forEach(c -> {
            medicineProxy.editConCount(token, c.getIdConsumable(), -c.getQte(), act.getIdStaff());
            actConsumableRepository.save(new ActConsumable(null, c.getIdConsumable(), c.getName(), c.getQte(),act.getDate(), savedAct));
        });
        medicines.forEach(m -> {
            medicineProxy.editMedCount(token, m.getIdMedicine(), -m.getQte(), act.getIdStaff());
            actMedicineRepository.save(new ActMedicine(null, m.getIdMedicine(), m.getName(), m.getQte(),act.getDate(), savedAct));
        });
        return savedAct;
    }


    @GetMapping("/hospitalization/acts/patient/{id}")
    @PostAuthorize("hasAuthority('GET_PATIENT')")
    public List<Act> getPatientActs(@RequestHeader("AUTHORIZATION") String token, @PathVariable("id") Long id) {
        List<Act> acts = actRepository.getAllByIdPatient(id);
        acts.forEach(a -> {
            a.setStaff(staffProxy.getStaff(token, a.getIdStaff()));
        });
        return acts;
    }

    @GetMapping("/hospitalization/acts/staff/{id}")
    @PostAuthorize("hasAuthority('GET_STAFF')")
    public List<Act> getStaffActs(@RequestHeader("AUTHORIZATION") String token, @PathVariable("id") Long id) {
        List<Act> acts = actRepository.getAllByIdStaff(id);
        acts.forEach(a -> {
            a.setPatient(patientProxy.getPatientInfo(token, a.getIdPatient()));
        });
        return acts;
    }


    @PostMapping("/beds")
    @PostAuthorize("hasAuthority('MANIPULATE_BEDS')")
    public int AddBed(@RequestBody Bed bed) {
        if (roomRepository.findById(bed.getIdRoom()).isPresent()) {
            Room room = roomRepository.findById(bed.getIdRoom()).get();
            if (room.getBedCount() > room.getBeds().size() && Objects.isNull(bedRepository.findByNumberAndRoom(bed.getNumber(), room))) {
                bed.setRoom(room);
                if(Objects.equals(room.getStatus(), "indisponible"))
                    bed.setStatus("indisponible");
                bedRepository.save(bed);
                return 1;
            } else {
                return 0;
            }
        }
        return 2;
    }

    @GetMapping("/beds/{id}")
    @PostAuthorize("hasAuthority('GET_BEDS')")
    public Bed GetLit(@PathVariable(value = "id") String idLit) {
        return bedRepository.findById(idLit).get();
    }

    @DeleteMapping("/beds/{id}")
    @PostAuthorize("hasAuthority('MANIPULATE_BEDS')")
    public void deleteBed(@PathVariable(value = "id") String idBed) {
        bedRepository.deleteById(idBed);
    }

    @PutMapping("/modifiyLit/{id}")
    @PostAuthorize("hasAuthority('MANIPULATE_BEDS')")
    public Bed ModifyLit(@PathVariable(value = "id") String idLit, @RequestBody Bed bed) {
        if (bedRepository.findById(idLit).isPresent()) {
            bed.setId(idLit);
            return bedRepository.save(bed);
        } else {
            return null;
        }
    }

    @GetMapping("/beds")
    @PostAuthorize("hasAuthority('GET_BEDS')")
    public List<Bed> GetLits(@RequestHeader("AUTHORIZATION") String token) {
        List<Bed> beds = bedRepository.findAll();
        beds.forEach(bed -> {
            if (Objects.nonNull(bed.getIdPatient()))
                bed.setPatient(patientProxy.getPatientInfo(token, bed.getIdPatient()));

        });
        return beds;
    }

    @GetMapping("/beds/count")
    @PostAuthorize("hasAuthority('GET_BEDS')")
    public int bedCount() {
        return bedRepository.findAll().size();
    }

    @PostMapping("/rooms")
    @PostAuthorize("hasAuthority('MANIPULATE_BEDS')")
    public int addChambre(@RequestBody Room room) {
        if (Objects.isNull(roomRepository.getByNumberAndAileAndBlock(room.getNumber(), room.getAile(), room.getBlock()))) {
            room.setStatus("disponible");
            roomRepository.save(room);
            return 1;
        }
        return 0;
    }

    @DeleteMapping("/rooms/{id}")
    @PostAuthorize("hasAuthority('MANIPULATE_BEDS')")
    public int deleteChambre(@PathVariable(value = "id") String id) {
        Room room = roomRepository.getById(id);
        AtomicInteger occupiedBedCount = new AtomicInteger(0);
        room.getBeds().forEach(b -> {
            if (Objects.nonNull(b.getIdPatient())) {
                occupiedBedCount.addAndGet(1);
            }
        });
        if (occupiedBedCount.get() == 0) {
            roomRepository.deleteById(id);
            return 1;
        } else {
            return 0;
        }
    }


    @GetMapping("/rooms/{id}")
    @PostAuthorize("hasAuthority('GET_BEDS')")
    public Room getChambre(@PathVariable(value = "id") String idChambre) {
        return roomRepository.findById(idChambre).get();
    }

    @PutMapping("/rooms/{id}")
    @PostAuthorize("hasAuthority('GET_BEDS')")
    public Room editChambre(@PathVariable(value = "id") String idRoom, @RequestBody Room room) {
        if (roomRepository.findById(idRoom).isPresent()) {
            room.setId(idRoom);
            return roomRepository.save(room);
        } else {
            return null;
        }
    }

    @GetMapping("/rooms")
    @PostAuthorize("hasAuthority('GET_BEDS')")
    public List<Room> getChambres() {
        return roomRepository.findAll();
    }


    @PostMapping("/assign-bed")
    @PostAuthorize("hasAuthority('ASSIGN_BED')")
    public Hospitalisation assignBed(@RequestHeader("AUTHORIZATION") String token, @RequestBody Hospitalisation hospitalisation, @RequestParam("idStaff") Long idStaff) {

        System.out.println(hospitalisation);
        if (Objects.isNull(hospitalisationRepository.getHospitalisationByIdPatient(hospitalisation.getIdPatient()))) {
            Bed bed = bedRepository.findById(hospitalisation.getIdBed()).get();
            bed.setStatus("occupe");
            bed.setIdPatient(hospitalisation.getIdPatient());
            bedRepository.save(bed);

            patientProxy.hospitalizePatient(token, hospitalisation.getIdPatient(), idStaff);
            patientProxy.reasonHospitalisationPatient(token, hospitalisation.getIdPatient(), hospitalisation.getReason());
            return hospitalisationRepository.save(hospitalisation);
        }
        return null;
    }

    @PostMapping("/free-bed/{id}")
    @PostAuthorize("hasAuthority('MANIPULATE_PATIENT')")
    public void freeBed(@PathVariable("id") String id) {
        if (bedRepository.findById(id).isPresent()) {
            Bed bed = bedRepository.getById(id);
            bed.setStatus("libre");
            bed.setIdPatient(null);
            bedRepository.save(bed);
        }
    }


    @GetMapping("/hospitalization/patient")
    @PostAuthorize("hasAuthority('GET_PATIENT')")
    public Hospitalisation getHospitalizationByPatient(@RequestHeader("AUTHORIZATION") String token, @RequestParam("idPatient") Long idPatient) {
        if (Objects.nonNull(hospitalisationRepository.getHospitalisationByIdPatient(idPatient))) {
            Hospitalisation hospitalisation = hospitalisationRepository.getHospitalisationByIdPatient(idPatient);
            if (Objects.nonNull(hospitalisation.getIdDoctor()))
                hospitalisation.setDoctor(staffProxy.getStaff(token, hospitalisation.getIdDoctor()));
            if (Objects.nonNull(hospitalisation.getIdNurse()))
                hospitalisation.setNurse(staffProxy.getStaff(token, hospitalisation.getIdNurse()));
            return hospitalisation;
        }
        return null;
    }


    @PostMapping("/hospitalization/planed-acts")
    @PostAuthorize("hasAuthority('PLAN_HOSPITALISATION_ACTS')")
    public PlanedAct AddToDoActs(@RequestHeader("AUTHORIZATION") String token,@RequestBody PlanedAct planedAct) {
        List<PlanedActMedicines> medicines = planedAct.getMedicines();
        List<PlanedActConsumable> consumables = planedAct.getConsumables();
        PlanedAct savedAct = planedActRepository.save(planedAct);
        Staff staff = staffProxy.getStaff(token,planedAct.getIdStaff());
        consumables.forEach(c -> {
            planedActConsumableRepo.save(new PlanedActConsumable(null, c.getIdConsumable(), c.getName(), c.getQte(),planedAct.getDate(), savedAct));
        });
        medicines.forEach(m -> {
            planedActMedicineRepo.save(new PlanedActMedicines(null, m.getIdMedicine(), m.getName(), m.getQte(),planedAct.getDate(), savedAct));
        });
        return savedAct;
    }

    @PutMapping("/hospitalization/planed-acts/done/{id}")
    @PostAuthorize("hasAuthority('ADD_HOSPITALISATION_ACTS')")
    public PlanedAct finishAct(@RequestHeader("Authorization") String token,@PathVariable("id") Long id) {
        PlanedAct planedAct = planedActRepository.getById(id);
        planedAct.setDone(true);
        Act act = new Act(null,planedAct.getActType(),planedAct.getIdBed(),
                planedAct.getIdStaff(), planedAct.getIdPatient(),
                planedAct.getDescription(),new Date(),null,null,null,null,null );
        Act savedAct = actRepository.save(act);
        List<ActConsumable> consumables = new ArrayList<>();
        List<ActMedicine> medicines = new ArrayList<>();

        planedAct.getConsumables().forEach(c -> {
            medicineProxy.editConCount(token, c.getIdConsumable(), -c.getQte(), act.getIdStaff());
            actConsumableRepository.save(new ActConsumable(null, c.getIdConsumable(), c.getName(), c.getQte(),act.getDate(), savedAct));
        });
        planedAct.getMedicines().forEach(m -> {
            medicineProxy.editMedCount(token, m.getIdMedicine(), -m.getQte(), act.getIdStaff());
            actMedicineRepository.save(new ActMedicine(null, m.getIdMedicine(), m.getName(), m.getQte(),act.getDate(), savedAct));
        });
        return planedActRepository.save(planedAct);
    }

    @GetMapping("/hospitalization/planed-acts")
    public List<PlanedAct> getToDoActs(@RequestHeader("AUTHORIZATION") String token, @RequestParam("idStaff") Long idStaff) {
        if (Objects.nonNull(planedActRepository.getAllByIdStaff(idStaff))) {


            List<PlanedAct> planedActs = planedActRepository.getAllByIdStaff(idStaff);

            planedActs.forEach(planedAct -> {
                if (Objects.nonNull(planedAct.getIdPatient()))
                    planedAct.setPatient(patientProxy.getPatientInfo(token, planedAct.getIdPatient()));
                if (Objects.nonNull(planedAct.getIdStaff()))
                    planedAct.setStaff(staffProxy.getStaff(token, planedAct.getIdStaff()));
            });

            return planedActRepository.getAllByIdStaff(idStaff);
        }
        return null;
    }

    @PutMapping("/hospitalization/plan-operation")
    @PostAuthorize("hasAuthority('MANIPULATE_PATIENT')")
    public void planOperation(@RequestParam("idPatient") Long idPatient, @RequestParam("planedDate") Date date) {
        if (Objects.nonNull(hospitalisationRepository.getHospitalisationByIdPatient(idPatient))) {
            Hospitalisation hospitalisation = hospitalisationRepository.getHospitalisationByIdPatient(idPatient);
            hospitalisation.setPlannedOperationDate(date);
            hospitalisationRepository.save(hospitalisation);
        }
    }

    @PutMapping("/rooms/{id}/status")
    @PostAuthorize("hasAuthority('MANIPULATE_BEDS')")
    public int setRoomStatus(@PathVariable("id") String id, @RequestParam("status") String status) {
        if (roomRepository.findById(id).isPresent()) {
            Room room = roomRepository.getById(id);
            if (Objects.equals(status, "indisponible")) {
                AtomicInteger occupiedBedCount = new AtomicInteger(0);
                room.getBeds().forEach(b -> {
                    if (Objects.nonNull(b.getIdPatient())) {
                        occupiedBedCount.addAndGet(1);
                    }
                });
                if (occupiedBedCount.get() == 0) {
                    room.getBeds().forEach(b -> {
                        b.setStatus(status);
                        bedRepository.save(b);
                    });
                    room.setStatus(status);
                    roomRepository.save(room);
                    return 1;
                } else {
                    return 0;
                }
            } else if (Objects.equals(status, "disponible")) {
                room.getBeds().forEach(b -> {
                    b.setStatus("libre");
                    bedRepository.save(b);
                });
                room.setStatus(status);
                roomRepository.save(room);
                return 1;
            }
        }
        return 0;
    }

    @GetMapping("/consumable/stats")
    @PostAuthorize("hasAuthority('GET_PHARMACY_STATS')")
    public List<Object[]> getConsumableStats(){
            return actConsumableRepository.consumableStat();
    }

    @GetMapping("/medicine/stats")
    @PostAuthorize("hasAuthority('GET_PHARMACY_STATS')")
    public List<Object[]> getMedicineStats(){
        return actMedicineRepository.medicineStats();
    }

    @GetMapping("/hospitalization/stats")
    @PostAuthorize("hasAuthority('GET_PATIENTS_STATS')")
    public List<Object[]> getHospitalisationStats(){
        return hospitalisationRepository.countHospitalisationByreson();
    }
}
