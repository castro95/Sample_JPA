package dao;

import java.util.List;

import javax.persistence.EntityManager;

import modelo.Disciplina;
import modelo.Professor;

public class DisciplinaDao {

	private EntityManager entityManager;

	public DisciplinaDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void cadastrar(Disciplina disciplina) {
		this.entityManager.persist(disciplina);
	}

	public Disciplina buscarPorId(Integer id) {
		return this.entityManager.find(Disciplina.class, id);
	}

	public List<Disciplina> buscarTodos() {
		String jpql = "SELECT d FROM Disciplina d ";
		return this.entityManager.createQuery(jpql, Disciplina.class).getResultList();
	}

}
