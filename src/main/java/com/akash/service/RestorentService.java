package com.akash.service;

import com.akash.model.Restorent;
import com.akash.model.RestorentDto;
import com.akash.model.Users;
import com.akash.request.CreateRestorentRequest;

import java.util.List;
import java.util.Optional;

public interface RestorentService {

    Restorent createRestorent(CreateRestorentRequest createRestorentRequest, Users users);
    Restorent updateRestorent(Long id, CreateRestorentRequest updatedRestorentRequest) throws Exception;

    Long deleteRestorent(Long restorentId) throws Exception;

    List<Restorent> getAllRestorent();
    List<Restorent> searchRestorent(String keyword);
    Optional<Restorent> findRestorentById(Long id) throws Exception;
    Restorent getRestorentyUserid(Long userId) throws Exception;
    RestorentDto addToFavorites(Long restoId, Users user) throws Exception;
    Restorent updateRestorentStatus(Long id) throws Exception;


}
