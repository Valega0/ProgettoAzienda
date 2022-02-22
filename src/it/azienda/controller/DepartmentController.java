package it.azienda.controller;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.springframework.stereotype.Controller;
import it.azienda.entity.Department;
import it.azienda.service.DepartmentService;
import it.azienda.utility.AleardyExistEmployeeException;
import it.azienda.utility.AlredyExistDepartmentExeption;

@Controller
@ManagedBean(name = "controllerDepartment")
@SessionScoped
public class DepartmentController implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{departmentService}")
	DepartmentService departmentService = new DepartmentService();

	private Department newDepartment = null;
	@ManagedProperty(value = "#{arrDepartment}")
	private List<Department> arrDepartment;
	private Department selectDepartment;

	@PostConstruct
	public void init() {
		this.newDepartment = new Department();
		this.arrDepartment = departmentService.getAllDepartment();
		setSelectDepartment(new Department());

	}

	// list
	public List<Department> departamentList() {
		return departmentService.getAllDepartment();
	}

	// create
	public String createDepartment() throws AlredyExistDepartmentExeption{
		try {
			departmentService.createDepartment(newDepartment);
			reloadDepartment();
			return "listaDepartment.xhtml?faces-redirect=true";

			} catch (AlredyExistDepartmentExeption e) {
				FacesMessage currentInstance = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR",
						"Dipartimento " + newDepartment.getNameDep() + " già esistente");
				FacesContext.getCurrentInstance().addMessage("prova", currentInstance);
				return null;
			} catch (Exception e) {
				// TODO ritornare messaggio di errore a thymeleaf
				System.out.println(e.getMessage());
				return e.getMessage();
			}
	
	}

	// delete
	public void deleteDepartment() {
		FacesContext context = FacesContext.getCurrentInstance(); 
		if(selectDepartment == null) {
			context.addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR,"ATTENZIONE", "Selezionare un dipartimento") );
			setSelectDepartment(new Department());
			return;
		}
		try {
			departmentService.deleteDepartment(selectDepartment);
		} catch (Exception e) {
			context.addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR,"ATTENZIONE", "Impossibile eliminare il dipartimento") );
			e.printStackTrace();
		}
		reloadDepartment();
	}

	// update
	public String updateDepartment() {
	
			FacesContext context = FacesContext.getCurrentInstance(); 
				if(selectDepartment!=null) {
					
					return "editDepartment.xhtml?faces-redirect=true";
					}
				context.addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR,"ATTENZIONE", "Selezionare un dipartimento") );
				return null;
		}

	public String saveUpdate() {
		departmentService.updateDepartment(selectDepartment);
		reloadDepartment();
		return "listaDepartment.xhtml?faces-redirect=true";
	}

	// reload
	public void reloadDepartment() {
		setArrDepartment(departmentService.getAllDepartment());
	}

	public Department getDepartment() {
		return newDepartment;
	}

	public void setDepartment(Department departament) {
		this.newDepartment = departament;
	}

	public DepartmentService getDepartmentService() {
		return departmentService;
	}

	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	public Department getNewDepartment() {
		return newDepartment;
	}

	public void setNewDepartment(Department newDepartment) {
		this.newDepartment = newDepartment;
	}

	public List<Department> getArrDepartment() {
		return arrDepartment;
	}

	public void setArrDepartment(List<Department> arrDepartment) {
		this.arrDepartment = arrDepartment;
	}

	public Department getSelectDepartment() {
		return selectDepartment;
	}

	public void setSelectDepartment(Department selectDepartment) {
		this.selectDepartment = selectDepartment;
	}

}
