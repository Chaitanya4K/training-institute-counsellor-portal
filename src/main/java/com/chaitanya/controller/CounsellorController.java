package com.chaitanya.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.chaitanya.dto.DashboardResponse;
import com.chaitanya.entity.Counsellor;
import com.chaitanya.service.CounsellorService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CounsellorController {

	private CounsellorService counsellorService;

	public CounsellorController(CounsellorService counsellorService) {
		this.counsellorService = counsellorService;
	}

	@GetMapping("/")
	public String index(Model model) {
		Counsellor cobj = new Counsellor();

		// sending data from controller to UI
		model.addAttribute("counsellor", cobj);

		// returning view name
		return "index";
	}

	@PostMapping("/login")
	public String login(Counsellor counsellor, HttpServletRequest request, Model model) {

		Counsellor c = counsellorService.login(counsellor.getEmail(), counsellor.getPassword());
		
		if (c == null) {

			model.addAttribute("emsg", "Invalid Credentials");

			return "index";

		} else {

			// valid Login, store counsellorId in Session for future purpose

			HttpSession session = request.getSession(true); // for login create new session everytime

			session.setAttribute("counsellorId", c.getCounsellorId());
	       
			session.setAttribute("counsellorName", c.getName());

			return "redirect:/dashboard"; // to avoid same code logic we redirected to dashboard

		}

	}

	// for Dashboard display

	@GetMapping("/dashboard")
	public String displayDashboard(HttpServletRequest req, Model model) {

		// get existing session obj
		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("counsellorId") == null) {
			return "redirect:/";
		}
		Integer counsellorId = (Integer) session.getAttribute("counsellorId");
		
		// GET NAME FROM SESSION
		String name = (String) session.getAttribute("counsellorName");
		
		//dashboard data
		DashboardResponse dashboardObj = counsellorService.getDashboardInfo(counsellorId);
		model.addAttribute("dashboardInfo", dashboardObj);
		
		//SEND NAME TO UI
		model.addAttribute("name", name);
		
		return "dashboard";
	}

	@GetMapping("/register")
	public String registerPage(Model model) {

		Counsellor cobj = new Counsellor();

		// sending data to controller to UI
		model.addAttribute("counsellor", cobj);

		return "register";
	}

	@PostMapping("/register")
	public String handleRegistration(Counsellor counsellor, Model model) {

		Counsellor byEmail = counsellorService.findByEmail(counsellor.getEmail());

		if (byEmail != null) {
			model.addAttribute("emsg", "Duplicate Email id");
			model.addAttribute("counsellor", counsellor);
			return "register";
		}

		boolean isRegistered = counsellorService.register(counsellor);

		if (isRegistered) {
			// success
			model.addAttribute("smsg", "Registration Suceccess...!!");
		} else {
			// failure
			model.addAttribute("emsg", "Invalid Registration");
		}

		return "register";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest req) {

		// get existing session and invalidate it
		HttpSession session = req.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		// redirected to login page
		return "redirect:/";
	}

}
