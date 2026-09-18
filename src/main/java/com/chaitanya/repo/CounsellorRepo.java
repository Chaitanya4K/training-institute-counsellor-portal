package com.chaitanya.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chaitanya.entity.Counsellor;

public interface CounsellorRepo extends JpaRepository<Counsellor, Integer> {
	//select * from counsellor-tbl where email=:email
	public Counsellor findByEmail(String email);
	
// It will create query like this [ select* from counsellor_tbl where email=:email and pwd=:password ]
   public Counsellor findByEmailAndPassword(String email, String password);

}
