package com.stanco.serviceimpl;

import com.stanco.dto.request.RfhRequest;
import com.stanco.dto.response.RfhResponse;
import com.stanco.entity.Rfh;
import com.stanco.repository.RfhRepository;
import com.stanco.service.RfhService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RfhServiceImpl implements RfhService {

        private final RfhRepository repository;

        @Override
        public RfhResponse create(RfhRequest request, String requestBy) {

                Rfh rfh = new Rfh();

                rfh.setTicketNumber(request.getTicketNumber());

                rfh.setRollsOption(request.getRollsOption());
                rfh.setName(request.getName());
                rfh.setMobile(request.getMobile());
                rfh.setEmail(request.getEmail());

                rfh.setPositionReports(request.getPositionReports());
                rfh.setReportEmail(request.getReportEmail());

                rfh.setCostCenter(request.getCostCenter());
                rfh.setApprovedBy(request.getApprovedBy());

                rfh.setRequestType(request.getRequestType());
                rfh.setReplacementOf(request.getReplacementOf());
                rfh.setApprovalHire(request.getApprovalHire());

                rfh.setPositionTitle(request.getPositionTitle());
                rfh.setLocation(request.getLocation());
                rfh.setLocationPreferred(request.getLocationPreferred());

                rfh.setBusiness(request.getBusiness());
                rfh.setBand(request.getBand());
                rfh.setDivision(request.getDivision());
                rfh.setFunction(request.getFunction());

                rfh.setNoOfPositions(request.getNoOfPositions());

                rfh.setJdRoles(request.getJdRoles());
                rfh.setQualification(request.getQualification());
                rfh.setEssentialSkill(request.getEssentialSkill());
                rfh.setGoodSkill(request.getGoodSkill());

                rfh.setExperience(request.getExperience());

                rfh.setSalaryRange(request.getSalaryRange());
                rfh.setSalaryRangeAnnual(request.getSalaryRangeAnnual());

                rfh.setAnySpecific(request.getAnySpecific());

                rfh.setApprovalHirePath(
                                request.getApprovalHirePath() == null
                                                ? 0
                                                : request.getApprovalHirePath());

                rfh.setRequestDate(request.getRequestDate());
                rfh.setApproveDate(request.getApproveDate());

                rfh.setDepartment(request.getDepartment());
                rfh.setDesignation(request.getDesignation());
                rfh.setVertical(request.getVertical());

                rfh.setTenDoj(request.getTenDoj());
                rfh.setEmpCategory(request.getEmpCategory());
                rfh.setType(request.getType());

                rfh.setAttendanceFormat(request.getAttendanceFormat());
                rfh.setWeekOff(request.getWeekOff());

                rfh.setCkSupervisior(request.getCkSupervisior());
                rfh.setCkMail(request.getCkMail());

                rfh.setApproverId(request.getApproverId());
                rfh.setReporterId(request.getReporterId());

                rfh.setClientName(request.getClientName());

                rfh.setRequestBy(requestBy);

                rfh.setCreatedDate(LocalDateTime.now());
                rfh.setDeleteStatus(0);
                rfh.setDeleteRemark("");

                Rfh saved = repository.save(rfh);

                String rfhNumber = String.format(
                                "RFH%06d",
                                saved.getId());

                saved.setResId(rfhNumber);

                saved = repository.save(saved);

                return mapToResponse(saved);
        }

        @Override
        public List<RfhResponse> getAll() {

                return repository.findAll()
                                .stream()
                                .map(this::mapToResponse)
                                .collect(Collectors.toList());
        }

        @Override
        public RfhResponse getById(Long id) {

                Rfh rfh = repository.findById(id)
                                .orElseThrow(
                                                () -> new RuntimeException(
                                                                "RFH not found with id: " + id));

                return mapToResponse(rfh);
        }

        @Override
        public RfhResponse getByResId(
                        String resId) {

                Rfh rfh = repository.findByResId(resId)
                                .orElseThrow(
                                                () -> new RuntimeException(
                                                                "RFH not found with resId: "
                                                                                + resId));

                return mapToResponse(rfh);
        }

        @Override
        public List<RfhResponse> getMyRfh(
                        String requestBy) {

                return repository.findByRequestBy(requestBy)
                                .stream()
                                .map(this::mapToResponse)
                                .collect(Collectors.toList());
        }

        @Override
        public RfhResponse update(
                        Long id,
                        RfhRequest request) {

                Rfh existing = repository.findById(id)
                                .orElseThrow(
                                                () -> new RuntimeException(
                                                                "RFH not found with id: " + id));

                if (request.getTicketNumber() != null
                                && !request.getTicketNumber().trim().isEmpty()) {

                        String newTicketNumber = request.getTicketNumber().trim();

                        if (!newTicketNumber.equals(
                                        existing.getTicketNumber())) {

                                if (repository.existsByTicketNumber(
                                                newTicketNumber)) {

                                        throw new RuntimeException(
                                                        "Ticket number already exists: "
                                                                        + newTicketNumber);
                                }
                        }

                        existing.setTicketNumber(
                                        newTicketNumber);
                }

                mapRequestToEntity(
                                request,
                                existing);

                existing.setResId(
                                existing.getResId());

                if (request.getTicketNumber() != null
                                && !request.getTicketNumber().trim().isEmpty()) {

                        existing.setTicketNumber(
                                        request.getTicketNumber().trim());
                }

                existing = repository.save(existing);

                return mapToResponse(existing);
        }

        @Override
        public void delete(
                        Long id,
                        String remark) {

                Rfh rfh = repository.findById(id)
                                .orElseThrow(
                                                () -> new RuntimeException(
                                                                "RFH not found with id: " + id));

                rfh.setDeleteStatus(1);

                rfh.setDeleteRemark(
                                remark != null
                                                ? remark
                                                : "");

                repository.save(rfh);
        }

        private void mapRequestToEntity(
                        RfhRequest request,
                        Rfh entity) {

                entity.setRollsOption(
                                request.getRollsOption());

                entity.setName(
                                request.getName());

                entity.setMobile(
                                request.getMobile());

                entity.setEmail(
                                request.getEmail());

                entity.setPositionReports(
                                request.getPositionReports());

                entity.setReportEmail(
                                request.getReportEmail());

                entity.setCostCenter(
                                request.getCostCenter());

                entity.setApprovedBy(
                                request.getApprovedBy());

                entity.setRequestType(
                                request.getRequestType());

                entity.setReplacementOf(
                                request.getReplacementOf());

                entity.setApprovalHire(
                                request.getApprovalHire());

                entity.setTicketNumber(
                                request.getTicketNumber());

                entity.setPositionTitle(
                                request.getPositionTitle());

                entity.setLocation(
                                request.getLocation());

                entity.setLocationPreferred(
                                request.getLocationPreferred());

                entity.setBusiness(
                                request.getBusiness());

                entity.setBand(
                                request.getBand());

                entity.setDivision(
                                request.getDivision());

                entity.setFunction(
                                request.getFunction());

                entity.setNoOfPositions(
                                request.getNoOfPositions());

                entity.setJdRoles(
                                request.getJdRoles());

                entity.setQualification(
                                request.getQualification());

                entity.setEssentialSkill(
                                request.getEssentialSkill());

                entity.setGoodSkill(
                                request.getGoodSkill());

                entity.setExperience(
                                request.getExperience());

                entity.setSalaryRange(
                                request.getSalaryRange());

                entity.setSalaryRangeAnnual(
                                request.getSalaryRangeAnnual());

                entity.setAnySpecific(
                                request.getAnySpecific());

                entity.setDeleteRemark(
                                request.getDeleteRemark());

                entity.setApprovalHirePath(
                                request.getApprovalHirePath());

                entity.setRequestDate(
                                request.getRequestDate());

                entity.setApproveDate(
                                request.getApproveDate());

                entity.setDepartment(
                                request.getDepartment());

                entity.setDesignation(
                                request.getDesignation());

                entity.setVertical(
                                request.getVertical());

                entity.setTenDoj(
                                request.getTenDoj());

                entity.setEmpCategory(
                                request.getEmpCategory());

                entity.setType(
                                request.getType());

                entity.setAttendanceFormat(
                                request.getAttendanceFormat());

                entity.setWeekOff(
                                request.getWeekOff());

                entity.setCkSupervisior(
                                request.getCkSupervisior());

                entity.setCkMail(
                                request.getCkMail());

                entity.setApproverId(
                                request.getApproverId());

                entity.setReporterId(
                                request.getReporterId());

                entity.setClientName(
                                request.getClientName());
        }

        private RfhResponse mapToResponse(
                        Rfh rfh) {

                return new RfhResponse(

                                rfh.getId(),

                                rfh.getResId(),

                                rfh.getRollsOption(),

                                rfh.getName(),

                                rfh.getMobile(),

                                rfh.getEmail(),

                                rfh.getPositionReports(),

                                rfh.getReportEmail(),

                                rfh.getCostCenter(),

                                rfh.getApprovedBy(),

                                rfh.getRequestType(),

                                rfh.getReplacementOf(),

                                rfh.getApprovalHire(),

                                rfh.getTicketNumber(),

                                rfh.getPositionTitle(),

                                rfh.getLocation(),

                                rfh.getLocationPreferred(),

                                rfh.getBusiness(),

                                rfh.getBand(),

                                rfh.getDivision(),

                                rfh.getFunction(),

                                rfh.getNoOfPositions(),

                                rfh.getJdRoles(),

                                rfh.getQualification(),

                                rfh.getEssentialSkill(),

                                rfh.getGoodSkill(),

                                rfh.getExperience(),

                                rfh.getSalaryRange(),

                                rfh.getSalaryRangeAnnual(),

                                rfh.getAnySpecific(),

                                rfh.getCreatedDate(),

                                rfh.getDeleteStatus(),

                                rfh.getDeleteRemark(),

                                rfh.getApprovalHirePath(),

                                rfh.getRequestDate(),

                                rfh.getRequestBy(),

                                rfh.getApproveDate(),

                                rfh.getDepartment(),

                                rfh.getDesignation(),

                                rfh.getVertical(),

                                rfh.getTenDoj(),

                                rfh.getEmpCategory(),

                                rfh.getType(),

                                rfh.getAttendanceFormat(),

                                rfh.getWeekOff(),

                                rfh.getCkSupervisior(),

                                rfh.getCkMail(),

                                rfh.getApproverId(),

                                rfh.getReporterId(),

                                rfh.getClientName());
        }
}