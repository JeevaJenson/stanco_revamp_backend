package com.stanco.serviceimpl;

import com.stanco.dto.request.BusinessMasterRequest;
import com.stanco.dto.response.BusinessMasterResponse;
import com.stanco.entity.BusinessMaster;
import com.stanco.repository.BusinessMasterRepository;
import com.stanco.service.BusinessMasterService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessMasterServiceImpl
        implements BusinessMasterService {


    private final BusinessMasterRepository repository;


    @Override
    public BusinessMasterResponse create(
            BusinessMasterRequest request,
            String createdBy) {



        if (request.getBuId() != null
                && !request.getBuId().trim().isEmpty()
                && repository.existsByBuId(
                        request.getBuId().trim())) {

            throw new RuntimeException(
                    "Business ID already exists: "
                            + request.getBuId()
            );
        }


        if (request.getBusinessName() == null
                || request.getBusinessName().trim().isEmpty()) {

            throw new RuntimeException(
                    "Business name is required"
            );
        }



        BusinessMaster business =
                new BusinessMaster();


        business.setBuId(
                request.getBuId() != null
                        ? request.getBuId().trim()
                        : null
        );


        business.setBusinessName(
                request.getBusinessName().trim()
        );


        business.setStatus(
                request.getStatus() != null
                        ? request.getStatus()
                        : "active"
        );


        business.setCreatedBy(
                createdBy
        );


        business.setCreatedAt(
                LocalDateTime.now()
        );


        business.setUpdatedAt(
                LocalDateTime.now()
        );


      
        BusinessMaster saved =
                repository.save(business);


        return mapToResponse(saved);
    }



    @Override
    public List<BusinessMasterResponse> getAll() {



        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }



    @Override
    public BusinessMasterResponse getById(
            Long id) {


        BusinessMaster business =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Business not found: "
                                                + id
                                )
                        );


        return mapToResponse(
                business
        );
    }

    @Override
    public BusinessMasterResponse getByBuId(
            String buId) {


        if (buId == null
                || buId.trim().isEmpty()) {

            throw new RuntimeException(
                    "Business ID is required"
            );
        }


        BusinessMaster business =
                repository.findByBuId(
                        buId.trim()
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Business not found: "
                                        + buId
                        )
                );


        return mapToResponse(
                business
        );
    }




    @Override
    public BusinessMasterResponse update(
            Long id,
            BusinessMasterRequest request,
            String updatedBy) {



        BusinessMaster business =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Business not found: "
                                                + id
                                )
                        );


        if (request.getBusinessName() == null
                || request.getBusinessName()
                    .trim()
                    .isEmpty()) {

            throw new RuntimeException(
                    "Business name is required"
            );
        }


        

        if (request.getBuId() != null
                && !request.getBuId()
                    .trim()
                    .isEmpty()) {

            String newBuId =
                    request.getBuId().trim();


           
            if (!newBuId.equals(
                    business.getBuId()
            )
                    && repository.existsByBuId(
                            newBuId
                    )) {

                throw new RuntimeException(
                        "Business ID already exists: "
                                + newBuId
                );
            }


            business.setBuId(
                    newBuId
            );

        } else {

            business.setBuId(null);
        }




        business.setBusinessName(
                request.getBusinessName()
                        .trim()
        );

        if (request.getStatus() != null) {

            business.setStatus(
                    request.getStatus()
            );


        

            if ("active".equalsIgnoreCase(
                    request.getStatus()
            )) {

                business.setDeletedAt(null);
            }


           

            if ("inactive".equalsIgnoreCase(
                    request.getStatus()
            )) {

                if (business.getDeletedAt() == null) {

                    business.setDeletedAt(
                            LocalDateTime.now()
                    );
                }
            }
        }



        business.setUpdatedBy(
                updatedBy
        );


      

        business.setUpdatedAt(
                LocalDateTime.now()
        );



        BusinessMaster updated =
                repository.save(business);


        return mapToResponse(
                updated
        );
    }



    @Override
    public void delete(
            Long id) {




        BusinessMaster business =
                repository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Business not found: "
                                                + id
                                )
                        );


        business.setStatus(
                "inactive"
        );


        business.setDeletedAt(
                LocalDateTime.now()
        );


        business.setUpdatedAt(
                LocalDateTime.now()
        );




        repository.save(
                business
        );
    }



    private BusinessMasterResponse mapToResponse(
            BusinessMaster business) {


        return new BusinessMasterResponse(

                business.getId(),

                business.getBuId(),

                business.getBusinessName(),

                business.getStatus(),

                business.getCreatedBy(),

                business.getUpdatedBy(),

                business.getCreatedAt(),

                business.getUpdatedAt(),

                business.getDeletedAt()
        );
    }
}