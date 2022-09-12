package com.example.drone.service;

import com.example.drone.exceptions.ItemNotFoundException;
import com.example.drone.model.Delivery;
import com.example.drone.model.DeliveryLoad;
import com.example.drone.model.Drone;
import com.example.drone.model.Medicine;
import com.example.drone.repository.DeliveryRepository;
import com.example.drone.repository.DroneRepository;
import com.example.drone.repository.MedicineRepository;
import com.example.drone.service.exception.ItemAlreadyExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DroneServiceImpl implements  DroneService{
    private final DroneRepository droneRepository;
    private final DeliveryRepository deliveryRepository;
    private final MedicineRepository medicineRepository;
    @Override
    public Drone registerDrone(Drone drone) {
       Drone duplicate  = droneRepository.findTopBySerialNoAndSoftDeleteFalse(drone.getSerialNo()).orElse(null);
       if (duplicate!= null) {
           throw new ItemAlreadyExistException("Drone exists by serial number");
       }
       return  droneRepository.save(drone);
    }
    @Transactional
    @Override
    public Delivery loadDrone(long droneId, long medicineId) {
        Drone drone = droneRepository.findByIdAndSoftDeleteFalse(droneId).orElse(null);
        if (drone == null){
            throw new ItemNotFoundException("Drone not found");
        }
        //check if medicine with given id exists
        Optional<Medicine> medicine = medicineRepository.findMedicineByIdAndSoftDeleteFalse(medicineId);
        if (medicine.isEmpty()){
            throw new ItemNotFoundException("medicine not found");
        }

    }

    @Override
    public List<DeliveryLoad> checkLoadedMedication(long drone) {
        return null;
    }

    @Override
    public List<Drone> checkAvailableDrones(Pageable pageable) {
        return null;
    }

    @Override
    public int checkDronePercentage(long drone) {
        return 0;
    }
}
