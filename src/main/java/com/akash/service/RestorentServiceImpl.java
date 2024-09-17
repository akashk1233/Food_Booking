package com.akash.service;

import com.akash.model.Address;
import com.akash.model.Restorent;
import com.akash.model.RestorentDto;
import com.akash.model.Users;
import com.akash.repository.AddressRepository;
import com.akash.repository.RestorentRepository;
import com.akash.repository.UserRepository;
import com.akash.request.CreateRestorentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestorentServiceImpl implements RestorentService{
    @Autowired
    private RestorentRepository restorentRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Restorent createRestorent(CreateRestorentRequest createRestorentRequest, Users users) {
        Address address = addressRepository.save(createRestorentRequest.getAddress());

        Restorent restorent = new Restorent();

        restorent.setCuisineType(createRestorentRequest.getCuisineType());
        restorent.setAddress(address);
        restorent.setContactInformation(createRestorentRequest.getContactInformation());
        restorent.setName(createRestorentRequest.getName());
        restorent.setDescription(createRestorentRequest.getDescription());
        restorent.setOwner(users);
        restorent.setRegistrationdate(LocalDateTime.now());
        restorent.setOpeningHours(createRestorentRequest.getOpeningHours());
        restorent.setImages(createRestorentRequest.getImages());
        return restorentRepository.save(restorent);
    }

    public Restorent updateRestorent(Long restoId, CreateRestorentRequest updateResto) throws Exception {
        Optional<Restorent> restorent = findRestorentById(restoId);

        if(restorent.get().getCuisineType()!=null){
            restorent.get().setCuisineType(updateResto.getCuisineType());
        }
        if(restorent.get().getDescription()!=null){
            restorent.get().setDescription(updateResto.getDescription());
        }
        if(restorent.get().getName()!=null){
            restorent.get().setName(updateResto.getName());
        }
        return restorentRepository.save(restorent.get());
    }

    @Override
    public Long deleteRestorent(Long restoId) throws Exception {
         Optional<Restorent> restorent=findRestorentById(restoId);
         restorentRepository.delete(restorent.get());
        return restoId;

    }

    @Override
    public List<Restorent> getAllRestorent() {
        return restorentRepository.findAll();
    }

    @Override
    public List<Restorent> searchRestorent(String keyword) {
        return restorentRepository.findBySearchQuery(keyword);
    }

    public Optional<Restorent> findRestorentById(Long id) throws Exception{
//        Optional<Restorent> restorent = restorentRepository.findById(id);
//        if(restorent == null){
//            throw new Exception("rsetorent not found");
//        }
//
//        return restorent;

        Restorent restorent = restorentRepository.findById(id)
                .orElseThrow(()-> new Exception("not found"));

        return  Optional.of(restorent);

    }

    @Override
    public Restorent getRestorentyUserid(Long userId) throws Exception {

        Optional<Restorent> restorent = restorentRepository.findByOwnerId(userId);
        if(restorent.isEmpty()){
            throw new Exception("Restorent not found with the user_id: "+userId);
        }
        return restorent.get();
    }

    @Override
    public RestorentDto addToFavorites(Long restoId, Users user) throws Exception {

        Optional<Restorent> restorent = findRestorentById(restoId);
        RestorentDto dto = RestorentDto.builder()
                .images(restorent.get().getImages())
                .description(restorent.get().getDescription())
                .name(restorent.get().getName())
                .build();
        if(user.getFavorites().contains(dto)){
            user.getFavorites().remove(dto);
        }else {
            user.getFavorites().add(dto);
        }
        userRepository.save(user);
        return dto;
    }

    @Override
    public Restorent updateRestorentStatus(Long id) throws Exception {
        Optional<Restorent> restorent = findRestorentById(id);
        restorent.get().setOpen(!restorent.get().isOpen());
        return restorentRepository.save(restorent.get());
    }
}
