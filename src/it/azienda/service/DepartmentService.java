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
import it.azienda.utility.AlredyExistDepartmentExeption;

@Service
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class DepartmentService {

	@PersistenceContext
	private EntityManager em;

	// List
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

	// create
	@Transactional
	public void createDepartment(Department department) throws AlredyExistDepartmentExeption, Exception {
		if(!isSameDepartment(department.getNameDep())) {
			em.persist(department);
			em.flush();

		}else {
			throw new AlredyExistDepartmentExeption(department.getNameDep());
		}

	}

	// delete
	public void deleteDepartment(Department department) {
		try {

			em.remove(em.find(Department.class, department.getIdDep()));
			em.flush();

		} catch (Exception e) {
			 
		e.printStackTrace();
		}

	}

	// update

	public void updateDepartment(Department department) {
		try {
			em.merge(department);
			em.flush();
			
	
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	// Metodo privato per controllare l'esistenza di un utente
		private boolean isSameDepartment(String name) {
			
			//prende i valori dal db
			Department checkName = null;
			try {
				 checkName = (Department) em.createQuery("SELECT d FROM Department d WHERE d.nameDep = :name")
						.setParameter("name", name).getSingleResult();	
			} catch(NoResultException nre) {
				
			}
			//setParameter sostituisce name con il vero valore di name String
			if(checkName  != null)
				return true;
			
			return false;
		}
}
