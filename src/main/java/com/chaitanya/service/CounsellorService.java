package com.chaitanya.service;

import com.chaitanya.dto.DashboardResponse;
import com.chaitanya.entity.Counsellor;

public interface CounsellorService {
	
	public Counsellor findByEmail(String email);
	
	public boolean register (Counsellor counsellor);
	
	public Counsellor login(String email ,String password);
	
	public DashboardResponse getDashboardInfo(Integer counsellorId );
}
