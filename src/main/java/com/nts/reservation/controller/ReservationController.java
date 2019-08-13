package com.nts.reservation.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nts.reservation.dto.ReservationInfo;
import com.nts.reservation.service.DisplayService;
import com.nts.reservation.service.ReservationService;

@Controller
public class ReservationController {

	private final DisplayService displayService;
	private final ReservationService reservationService;
	private static final DateTimeFormatter DATE_PATTERN_DATA = DateTimeFormatter.ofPattern("yyyy.MM.dd");

	@Autowired
	public ReservationController(DisplayService displayService, ReservationService reservationService) {
		this.displayService = displayService;
		this.reservationService = reservationService;
	}

	@GetMapping("/reserve")
	public String reserve(Model model,
		@CookieValue(value = "email", required = false) String cookieEmail,
		@RequestParam(defaultValue = "0") int id) {

		if (StringUtils.isNotBlank(cookieEmail) == false) {
			return "bookinglogin";
		}

		model.addAttribute("displayReserve", displayService.getDisplayReserve(id));
		model.addAttribute("userEmail", getEmailCookie(cookieEmail));
		model.addAttribute("reserveDate", getReserveDate().format(DATE_PATTERN_DATA));
		return "reserve";
	}

	private LocalDate getReserveDate() {
		return LocalDate.now().plusDays(ThreadLocalRandom.current().nextLong(1, 5));
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
		return StringUtils.defaultString(fromCookie);
	}

	@GetMapping("/myreservation")
	public String myReservation(Model model,
		HttpServletResponse response,
		@CookieValue(value = "email", required = false) String cookieEmail,
		@RequestParam(name = "resrv_email", required = true) String email) {

		if (StringUtils.isNotBlank(email) == false) {
			return "bookinglogin";
		}

		if (StringUtils.isNotBlank(cookieEmail) == false || email.equals(cookieEmail) == false) {
			Cookie cookie = new Cookie("email", email);
			cookie.setMaxAge(-1);
			cookie.setPath("/");
			response.addCookie(cookie);
		}

		return "myreservation";
	}

	@PostMapping("/myreservation")
	public String myReservationPost(Model model,
		ReservationInfo reservationInfo,
		@CookieValue(value = "email", required = false) String cookieEmail) {

		reservationService.addReservation(reservationInfo);

		return "myreservation";
	}

}