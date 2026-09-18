package com.chaitanya.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.chaitanya.entity.Enquiry;

public interface EnquiryRepo extends JpaRepository<Enquiry, Integer>{

	List<Enquiry> findByCounsellor_CounsellorId(Integer counsellorId);
	
	
}
 