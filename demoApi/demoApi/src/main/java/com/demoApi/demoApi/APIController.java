package com.demoApi.demoApi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class APIController {

	private Database db = new Database();
	
	@RequestMapping("/students")
	ResponseEntity<List<Student>> getStudents() {
//		return this.db.getStudents();
		ResultSet res = this.db.getStudents();
		List<Student> students =  new ArrayList<Student>();
		try {
			while(res.next()) {
				Student s = new Student();
				s.setName(res.getString("name"));
				s.setGpa(Float.toString(res.getFloat("gpa")));
				students.add(s);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(students);
	}
	
	
	@RequestMapping("/students/subjects")
	ResponseEntity<List<UserRes>> getStudentsWithSubject() {

		ResultSet res = this.db.getStudents();
		List<Student> students =  new ArrayList<Student>();
		try {
			while(res.next()) {
				Student s = new Student();
				s.setName(res.getString("name"));
				s.setId(res.getInt("id"));
				s.setGpa(Float.toString(res.getFloat("gpa")));
				students.add(s);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<UserRes> userres = new ArrayList<UserRes>();
		students.forEach(s -> {
			UserRes ures = new UserRes();
			ures.setName(s.getName());
			ures.setTotalsubjects(this.db.totalSubjects(s.getId()));
			userres.add(ures);
		});
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(userres);
	}
	
	@RequestMapping(value="/add-students", method = RequestMethod.POST)
			
	ResponseEntity<?> addStudent(@RequestParam("gpa") String gpa,
			@RequestParam("name") String name,
			@RequestParam("courses") String courses) {
		
		// tell db to add a new entry
		this.db.addStudent(gpa, name, courses);
		
		//return response
		return ResponseEntity.status(HttpStatus.ACCEPTED).body("{status: okay}");
	}
	
	@RequestMapping(value="/full-name/{name}")
	ResponseEntity<Student> getByFullName(@PathVariable(value="name") String name) {
		System.out.println(name);
		
		// ask database for result
		Student s = this.db.getByFullName(name);
		
		// return response
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(s);
	}
	
	
}
//helper class
// models the object to be returned
// in part 4
class UserRes {
	private String name;
	private int totalsubjects;
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setTotalsubjects(int totalsubjects) {
		this.totalsubjects = totalsubjects;
	}
	
	public int getTotalsubjects() {
		return totalsubjects;
	}
	
	
	
}