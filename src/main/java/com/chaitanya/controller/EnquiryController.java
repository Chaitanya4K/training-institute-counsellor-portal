package com.chaitanya.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.chaitanya.dto.ViewEnquiryFilterRequest;
import com.chaitanya.entity.Enquiry;
import com.chaitanya.service.EnquiryService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController {

	private EnquiryService enqService;

	public EnquiryController(EnquiryService enqService) {
		super();
		this.enqService = enqService;
	}

	@GetMapping("/enquiry") // to save new Enquiry
	public String addEnquiryPage(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession(false);

		if (session == null || session.getAttribute("counsellorId") == null) {
			return "redirect:/";
		}

		model.addAttribute("enquiry", new Enquiry());

		return "enquiryForm";
	}

	@PostMapping("/addEnq")
	public String handleAddEnquiry(Enquiry enquiry, HttpServletRequest req, Model model) throws Exception {

		// get existing session obj
		HttpSession session = req.getSession(false);
		// If session expired → NullPointerException to fix hidden bug
		if (session == null || session.getAttribute("counsellorId") == null) {
			return "redirect:/";
		}

		Integer counsellorId = (Integer) session.getAttribute("counsellorId");

		boolean isSaved = enqService.addEnquiry(enquiry, counsellorId);

		if (isSaved) {
			model.addAttribute("smsg", "Enquiry Added");
			model.addAttribute("enquiry", new Enquiry()); // reset form
		} else {
			model.addAttribute("emsg", "Failed to Add Enquiry");
			model.addAttribute("enquiry", enquiry); // keep data
		}

		return "enquiryForm";
	}

	@GetMapping("/view-enquiries")
	public String getEnquiries(HttpServletRequest request, Model model) {
		// get existing session obj
		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("counsellorId") == null) {
			return "redirect:/";
		}

		Integer counsellorId = (Integer) session.getAttribute("counsellorId");

		List<Enquiry> enqsList = enqService.getAllEnquiries(counsellorId);
		model.addAttribute("enquiries", enqsList);

		// search form binding object
		ViewEnquiryFilterRequest filterReq = new ViewEnquiryFilterRequest();
		model.addAttribute("viewEnqsFilterRequest", filterReq);

		return "viewEnqsPage";
	}

	@PostMapping("/filter-enqs")
	public String filterEnquiries(
			@ModelAttribute("viewEnqsFilterRequest") ViewEnquiryFilterRequest viewEnqsFilterRequest,
			HttpServletRequest req, Model model) {

		// get existing session obj
		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("counsellorId") == null) {
			return "redirect:/";
		}

		Integer counsellorId = (Integer) session.getAttribute("counsellorId");

		List<Enquiry> enqsList = enqService.getEnquiriesWithFilter(viewEnqsFilterRequest, counsellorId);

		model.addAttribute("enquiries", enqsList);
		model.addAttribute("viewEnqsFilterRequest", viewEnqsFilterRequest);

		return "viewEnqsPage";
	}

	@GetMapping("/editEnq")
	public String editEnquiry(@RequestParam("enquiryId") Integer enquiryId, Model model) {

		Enquiry enquiry = enqService.getEnquiryById(enquiryId);
		model.addAttribute("enquiry", enquiry);

		return "enquiryForm";
	}

	@GetMapping("/deleteEnq")
	public String deleteEnquiry(@RequestParam("enquiryId") Integer enquiryId, HttpServletRequest request, Model model) {

		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("counsellorId") == null) {
			return "redirect:/";
		}

		Integer counsellorId = (Integer) session.getAttribute("counsellorId");

		// delete logic
		enqService.deleteEnquiryById(enquiryId);

		// reload list
		List<Enquiry> enqsList = enqService.getAllEnquiries(counsellorId);
		model.addAttribute("enquiries", enqsList);

		// important for filter form
		model.addAttribute("viewEnqsFilterRequest", new ViewEnquiryFilterRequest());

		return "viewEnqsPage";
	}

}
