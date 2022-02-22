package it.azienda.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the department database table.
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name = "Department.findAll", query = "SELECT d FROM Department d ORDER BY d.nameDep"),
		@NamedQuery(name = "Department.findByName", query = "SELECT d FROM Department d WHERE d.nameDep=:nameDep") })
public class Department implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID_DEP")
	private Integer idDep;

	@Column(name = "NAME_DEP")
	private String nameDep;

	// bi-directional many-to-one association to Employee
	@OneToMany(mappedBy = "department", fetch = FetchType.EAGER)
	private List<Employee> employees;

	public Department() {
	}

	public Employee addEmployee(Employee employee) {
		getEmployees().add(employee);
		employee.setDepartment(this);

		return employee;
	}

	public Employee removeEmployee(Employee employee) {
		getEmployees().remove(employee);
		employee.setDepartment(null);

		return employee;
	}

	public Integer getIdDep() {
		return idDep;
	}

	public void setIdDep(Integer idDep) {
		this.idDep = idDep;
	}

	public String getNameDep() {
		return nameDep;
	}

	public void setNameDep(String nameDep) {
		this.nameDep = nameDep;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

}