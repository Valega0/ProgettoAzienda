package it.azienda.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.springframework.stereotype.Controller;

import it.azienda.entity.Department;
import it.azienda.entity.Employee;
import it.azienda.service.EmployeeService;
import it.azienda.utility.AleardyExistEmployeeException;

@Controller
@ManagedBean(name = "controllerEmployee")
@SessionScoped

public class EmployeeController implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{employeeService}")
	EmployeeService employeeService = new EmployeeService();

	public Employee newEmployee = null;
	private List<Employee> arrEmployee;
	private Employee selectEmployee;
	private List<Department> listDepartment;

	@PostConstruct
	public void init() {
		this.newEmployee = new Employee();
		this.arrEmployee = employeeService.getAllEmployees();
		setSelectEmployee(new Employee());
		setListDepartment(employeeService.getAllDepartment());

	}

	// list
	public List<Employee> employeeList() {
		return employeeService.getAllEmployees();
	}

	// create
	public String createEmployee() throws AleardyExistEmployeeException {

		try {
			employeeService.createNewEmployee(newEmployee);
			reloadEmployee();

			return "listaEmployee.xhtml?faces-redirect=true";

		} catch (AleardyExistEmployeeException e) {
			FacesMessage currentInstance = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR",
					"Utente " + newEmployee.getNameEmp() + " già esistente");
			FacesContext.getCurrentInstance().addMessage("prova", currentInstance);
			return null;
		} catch (Exception e) {
			// TODO ritornare messaggio di errore a thymeleaf
			System.out.println(e.getMessage());
			return e.getMessage();
		}

	}

	// update
	public String updateEmployee() {
		FacesContext context = FacesContext.getCurrentInstance(); 
		if(selectEmployee!=null) {
			
			return "editEmployee.xhtml?faces-redirect=true";
			}
		context.addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR,"ATTENZIONE", "Selezionare un dipendente") );
		return null;
}


	public String saveUpdateEmp() {
		employeeService.updateEmployee(selectEmployee);
		reloadEmployee();
		return "listaEmployee.xhtml?faces-redirect=true";
	}

//	Reload 
	public void reloadEmployee() {
		setArrEmployee(employeeService.getAllEmployees());
	}

	// delete
	public void deleteEmployee() {

		if (selectEmployee == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "ATTENZIONE", "Selezionare un dipendente"));
			setSelectEmployee(new Employee());
			return;
		}
		try {
			employeeService.deleteEmployee(selectEmployee);

		} catch (Exception e) {
			e.printStackTrace();
		}
		reloadEmployee();
	}

	public Employee getEmployee() {
		return newEmployee;
	}

	public void setEmployee(Employee employee) {
		this.newEmployee = employee;
	}

	public Employee getSelectEmployee() {
		return selectEmployee;
	}

	public void setSelectEmployee(Employee selectEmployee) {
		this.selectEmployee = selectEmployee;
	}

	public EmployeeService getEmployeeService() {
		return employeeService;
	}

	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public Employee getNewEmployee() {
		return newEmployee;
	}

	public void setNewEmployee(Employee newEmployee) {
		this.newEmployee = newEmployee;
	}

	public List<Employee> getArrEmployee() {
		return arrEmployee;
	}

	public void setArrEmployee(List<Employee> arrEmployee) {
		this.arrEmployee = arrEmployee;
	}

	public List<Department> getListDepartment() {
		return listDepartment;
	}

	public void setListDepartment(List<Department> listDepartment) {
		this.listDepartment = listDepartment;
	}

}
