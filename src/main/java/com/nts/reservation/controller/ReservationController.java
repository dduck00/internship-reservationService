package com.nts.reservation.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nts.reservation.service.DisplayService;
import com.nts.reservation.service.ReservationService;

@Controller
public class ReservationController {

	private final DisplayService displayService;

	private final ReservationService reservationService;

	@Autowired
	public ReservationController(DisplayService displayService, ReservationService reservationService) {
		this.displayService = displayService;
		this.reservationService = reservationService;
	}

	@GetMapping("/reserve")
	public String reserve(Model model,
		@CookieValue(value = "email", required = false) String cookieEmail,
		@RequestParam(defaultValue = "0") int id) {

		if (cookieEmail == null) {
			return "bookinglogin";
		}

		model.addAttribute("displayReserve", displayService.getDisplayReserve(id));
		model.addAttribute("userEmail", getEmailCookie(cookieEmail));
		return "reserve";
	}

	@GetMapping("/product-detail")
	public String productDetail(Model model,
		@CookieValue(value = "email", required = false) String cookieEmail,
		@RequestParam(defaultValue = "0") int id) {

		model.addAttribute("displayId", id);
		model.addAttribute("userEmail", getEmailCookie(cookieEmail));
		return "detail";
	}

	@GetMapping("/product-review")
	public String productReview(Model model,
		@CookieValue(value = "email", required = false) String cookieEmail,
		@RequestParam(defaultValue = "0") int id) {

		model.addAttribute("productId", id);
		model.addAttribute("userEmail", getEmailCookie(cookieEmail));
		return "review";
	}

	@GetMapping("/main-page")
	public String mainPage(Model model,
		@CookieValue(value = "email", required = false) String cookieEmail) {

		model.addAttribute("userEmail", getEmailCookie(cookieEmail));
		return "mainpage";
	}

	private String getEmailCookie(String fromCookie) {
		if (fromCookie == null) {
			return "";
		}
		return fromCookie;
	}

	@GetMapping("/myreservation")
	public String myReservation(Model model,
		HttpServletResponse response,
		@CookieValue(value = "email", required = false) String cookieEmail,
		@RequestParam(required = true) String resrv_email) {

		if (resrv_email.equals("")) {
			return "bookinglogin";
		}

		if (cookieEmail == null || resrv_email.equals(cookieEmail) == false) {
			Cookie cookie = new Cookie("email", resrv_email);
			cookie.setMaxAge(-1);
			cookie.setPath("/");
			response.addCookie(cookie);
		}

		return "myreservation";
	}

	@PostMapping("/myreservation")
	public String myReservationPost(Model model,
		HttpServletRequest request,
		@CookieValue(value = "email", required = false) String cookieEmail) {
		Map<String, String[]> requestMap = request.getParameterMap();
		for (String st : requestMap.keySet()) {
			System.out.println(st + " " + request.getParameter(st));
		}

		reservationService.addReservation(requestMap);

		return "myreservation";
	}

}