package com.chaitanya.service.impl;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import com.chaitanya.dto.ViewEnquiryFilterRequest;
import com.chaitanya.entity.Counsellor;
import com.chaitanya.entity.Enquiry;
import com.chaitanya.repo.CounsellorRepo;
import com.chaitanya.repo.EnquiryRepo;
import com.chaitanya.service.EnquiryService;

import io.micrometer.common.util.StringUtils;

@Service
public class EnquiryServiceImpl implements EnquiryService {

	private EnquiryRepo enqRepo;
	private CounsellorRepo counsellorRepo;

	public EnquiryServiceImpl(EnquiryRepo enqRepo, CounsellorRepo counsellorRepo) {
		super();
		this.enqRepo = enqRepo;
		this.counsellorRepo = counsellorRepo;
	}

	@Override
	public boolean addEnquiry(Enquiry enq, Integer counsellorId) throws Exception {

		Counsellor counsellor = counsellorRepo.findById(counsellorId).orElse(null);

		if (counsellor == null) {
			throw new Exception("No counsellor found");
		}
		// Associating counsellor to enquiry
		enq.setCounsellor(counsellor);

		Enquiry save = enqRepo.save(enq);   // UPSERT (insert + update) in DATA JPA

		if (save.getEnquiryId() != null) {
			return true;
		}

		return false;
	}

	@Override
	public List<Enquiry> getAllEnquiries(Integer counsellorId) {

		List<Enquiry> byCounsellorId =  enqRepo.findByCounsellor_CounsellorId(counsellorId);

		return byCounsellorId;
	}

	@Override
	public Enquiry getEnquiryById(Integer enquiryId) {
		return enqRepo.findById(enquiryId).orElse(null);

	}

	@Override
	public List<Enquiry> getEnquiriesWithFilter(
			ViewEnquiryFilterRequest filterReq, 
			Integer counsellorId) {

		// QBE implementation (Dynamic Query Preparation)

		Enquiry enq = new Enquiry();  //entity
		
		  if (StringUtils.isNotEmpty(filterReq.getClassMode())) {
		  enq.setClassMode(filterReq.getClassMode()); }
		  
		  if (StringUtils.isNotEmpty(filterReq.getCourseName())) {
		  enq.setCourseName(filterReq.getCourseName()); }
		  
		  if (StringUtils.isNotEmpty(filterReq.getStatus())) {
		  enq.setStatus(filterReq.getStatus()); }
		 
		
		Counsellor counsellor = counsellorRepo.findById(counsellorId).orElse(null);
		if (counsellor == null) {
	        return List.of();
	    }
		
		enq.setCounsellor(counsellor);
		
		Example<Enquiry> of = Example.of(enq);

		return enqRepo.findAll(of);

		 
	}

	@Override
	public void deleteEnquiryById(Integer enquiryId) {
		
		enqRepo.deleteById(enquiryId);
	}

}
