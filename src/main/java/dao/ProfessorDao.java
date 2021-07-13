package dao;

import java.util.List;

import javax.persistence.EntityManager;

import modelo.Professor;

public class ProfessorDao {
	private EntityManager entityManager;
	
	public ProfessorDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void cadastrar(Professor professor) {
		this.entityManager.persist(professor);
	}
	
	public Professor buscarPorId(Integer id) {
		return this.entityManager.find(Professor.class, id);
	}
	
	public List<Professor> buscarTodos() {
		String jpql = "SELECT p FROM Professor p ";
		return this.entityManager.createQuery(jpql, Professor.class).getResultList();
	}
}
