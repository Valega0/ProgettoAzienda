package it.azienda.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the employee database table.
 * 
 */
@Entity
@NamedQueries({
@NamedQuery(name="Employee.findAll", query="SELECT e FROM Employee e ORDER BY e.nameEmp"),
@NamedQuery(name="Employee.findByName", query="SELECT e FROM Employee e WHERE e.nameEmp=:nameEmp")
})
public class Employee implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_EMP")
	private int idEmp;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATE_EMP")
	private Date dateEmp;

	@Column(name="NAME_EMP")
	private String nameEmp;

	@Column(name="SALARY_EMP")
	private BigDecimal salaryEmp;
	
	@Column(name="ID_DEP")
	private Integer idDep;

	//bi-directional many-to-one association to Department
	@ManyToOne
	@JoinColumn( insertable=false, updatable=false, name="ID_DEP" )
	private Department department;

	public Employee() {
	}

	public int getIdEmp() {
		return this.idEmp;
	}

	public void setIdEmp(int idEmp) {
		this.idEmp = idEmp;
	}

	public Date getDateEmp() {
		return this.dateEmp;
	}

	public void setDateEmp(Date dateEmp) {
		this.dateEmp = dateEmp;
	}

	public String getNameEmp() {
		return this.nameEmp;
	}

	public void setNameEmp(String nameEmp) {
		this.nameEmp = nameEmp;
	}

	public BigDecimal getSalaryEmp() {
		return this.salaryEmp;
	}

	public void setSalaryEmp(BigDecimal salaryEmp) {
		this.salaryEmp = salaryEmp;
	}

	public Department getDepartment() {
		return this.department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Integer getIdDep() {
		return idDep;
	}

	public void setIdDep(Integer idDep) {
		this.idDep = idDep;
	}

}