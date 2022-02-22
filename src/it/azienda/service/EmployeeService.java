package it.azienda.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.azienda.entity.Department;
import it.azienda.entity.Employee;
import it.azienda.utility.AleardyExistEmployeeException;

@Service
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class EmployeeService {

	@PersistenceContext
	private EntityManager em;


	// Lista
	@SuppressWarnings("unchecked")
	public List<Employee> getAllEmployees() {
		List<Employee> employeeList = new ArrayList<Employee>();
		try {
			employeeList = em.createNamedQuery("Employee.findAll").getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return employeeList;

	}

	@SuppressWarnings("unchecked")
	public List<Department> getAllDepartment() {
		List<Department> departmentList = new ArrayList<Department>();
		try {
			departmentList = em.createNamedQuery("Department.findAll").getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return departmentList;

	}



	// crea
	@Transactional
	public void createNewEmployee(Employee employee) throws AleardyExistEmployeeException, Exception {
			//verifica se esiste il nome dal db 
			if(!isSameEmployee(employee.getNameEmp())) {
				em.persist(employee);
				em.flush();	
			} else {
				throw new AleardyExistEmployeeException(employee.getNameEmp());
			}
	}

//DELETE

	public void deleteEmployee(Employee employee) {
		try {
			
			em.remove(em.find(Employee.class, employee.getIdEmp()));
			em.flush();

		} catch (Exception e) {
			 e.printStackTrace();  
		}
		
	}

	// Update

	public void updateEmployee(Employee employee) {
		try {

			em.merge(employee);
			em.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Metodo privato per controllare l'esistenza di un utente
	private boolean isSameEmployee(String name) {
		
		//prende i valori dal db
		Employee checkName = null;
		try {
			 checkName = (Employee) em.createQuery("SELECT e FROM Employee e WHERE e.nameEmp = :name")
					.setParameter("name", name).getSingleResult();	
		} catch(NoResultException nre) {
			
		}
		//setParameter sostituisce name con il vero valore di name String
		if(checkName  != null)
			return true;
		
		return false;
	}


	public Employee getEmployeeByName(String nameEmp) {
		Employee emp = null;
		try {
			emp = (Employee) em.createNamedQuery("Employee.findByName").setParameter("nameEmp", nameEmp).getSingleResult();
		} catch (Exception e) {
		e.printStackTrace();
		}
		return emp;
	}
}
