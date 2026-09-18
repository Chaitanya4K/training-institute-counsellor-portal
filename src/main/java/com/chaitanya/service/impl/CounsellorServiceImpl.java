package com.chaitanya.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.chaitanya.dto.DashboardResponse;
import com.chaitanya.entity.Counsellor;
import com.chaitanya.entity.Enquiry;
import com.chaitanya.repo.CounsellorRepo;
import com.chaitanya.repo.EnquiryRepo;
import com.chaitanya.service.CounsellorService;

@Service
public class CounsellorServiceImpl implements CounsellorService {

	private CounsellorRepo counsellorRepo;

	private EnquiryRepo enqRepo;

	// constructor injection
	public CounsellorServiceImpl(CounsellorRepo counsellorRepo, EnquiryRepo enqRepo) {
		super();
		this.counsellorRepo = counsellorRepo;
		this.enqRepo = enqRepo;
	}

	@Override
	public Counsellor findByEmail(String email) {

		return counsellorRepo.findByEmail(email);
	}

	@Override
	public boolean register(Counsellor counsellor) {

		Counsellor savedCounsellor = counsellorRepo.save(counsellor);

		if (null != savedCounsellor.getCounsellorId()) {
			return true;
		}
		return false;
	}

	@Override
	public Counsellor login(String email, String password) {
		Counsellor counsellor = counsellorRepo.findByEmailAndPassword(email, password);

		return counsellor;
	}

	@Override
	public DashboardResponse getDashboardInfo(Integer counsellorId) {

		DashboardResponse response = new DashboardResponse();

		List<Enquiry> enqList = enqRepo.findByCounsellor_CounsellorId(counsellorId);

		int totalEnqs = enqList.size();

		int enrolledEnqs = enqList.stream()
									.filter(e -> e.getStatus().equals("Enrolled"))
									.collect(Collectors.toList())
									.size();
		int lostEnqs = enqList.stream()
								.filter(e -> e.getStatus().equals("Lost"))
								.collect(Collectors.toList())
								.size();

		int openEnqs = enqList.stream()
								.filter(e -> e.getStatus().equals("Open"))
								.collect(Collectors.toList())
								.size();

		response.setTotalEnquiries(totalEnqs);
		response.setEnrolledEnquiries(enrolledEnqs);
		response.setOpenEnquiries(openEnqs);
		response.setLostEnquiries(lostEnqs);

		return response;
	}

}
