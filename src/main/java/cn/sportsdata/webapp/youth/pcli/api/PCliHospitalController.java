package cn.sportsdata.webapp.youth.pcli.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.sportsdata.webapp.youth.common.vo.Response;
import cn.sportsdata.webapp.youth.pcli.service.PCliHospitalService;
import cn.sportsdata.webapp.youth.pcli.vo.HospitalVO;

@RestController
@RequestMapping("/api/v1/pcli")
public class PCliHospitalController {

	@Autowired
	PCliHospitalService hospitalService;
	
	@RequestMapping(value = "/hospital/{id}", method = RequestMethod.GET)
	public ResponseEntity<Response> getHospitalInfo(HttpServletRequest request, HttpServletResponse resp, 
			@PathVariable String id) {

		if (StringUtils.isEmpty(id)) {
			return new ResponseEntity<Response>(Response.toFailure(-1, "invalide hospital id"), HttpStatus.OK);
		}
		HospitalVO hospitalVO = hospitalService.getHospitalInfo(id);
		
		return new ResponseEntity<Response>(Response.toSussess(hospitalVO), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/hospital/{id}/departments", method = RequestMethod.GET)
	public ResponseEntity<Response> getHospitalDepartments(HttpServletRequest request, HttpServletResponse resp, 
			@PathVariable String id) {

		return null;
		
	}
	
	@RequestMapping(value = "/hospital/department/{id}", method = RequestMethod.GET)
	public ResponseEntity<Response> getHospitalDepartment(HttpServletRequest request, HttpServletResponse resp, 
			@PathVariable String id) {

		return null;
		
	}
	
	@RequestMapping(value = "/hospital/{id}/doctors", method = RequestMethod.GET)
	public ResponseEntity<Response> getHospitalDoctors(HttpServletRequest request, HttpServletResponse resp, 
			@PathVariable String id) {
		// get doctors by department and title
		return null;
		
	}
	
	@RequestMapping(value = "/hospital/doctor/{id}", method = RequestMethod.GET)
	public ResponseEntity<Response> getHospitalDoctor(HttpServletRequest request, HttpServletResponse resp, 
			@PathVariable String id) {

		return null;
		
	}
}
