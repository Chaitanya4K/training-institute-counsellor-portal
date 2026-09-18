package com.chaitanya.service;

import java.util.List;

import com.chaitanya.dto.ViewEnquiryFilterRequest;
import com.chaitanya.entity.Enquiry;

public interface EnquiryService {
	
	public boolean addEnquiry(Enquiry enq,Integer counsellorId) throws Exception;
	
	public List<Enquiry> getAllEnquiries(Integer counsellorId);
	
	public List<Enquiry> getEnquiriesWithFilter(ViewEnquiryFilterRequest filterReq, Integer counsellorId);

	public Enquiry getEnquiryById(Integer enquiryId);

	public void deleteEnquiryById(Integer enquiryId);
}
  