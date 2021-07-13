package dao;

import java.util.List;

import javax.persistence.EntityManager;

import modelo.Aluno;

public class AlunoDao {
	private EntityManager entityManager;
	
	public AlunoDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void cadastrar(Aluno aluno) {
		this.entityManager.persist(aluno);
	}
	
	public Aluno buscarPorId(Integer id) {
		return this.entityManager.find(Aluno.class, id);
	}
	
	public List<Aluno> buscarTodos() {
		String jpql = "SELECT a FROM Aluno a ";
		return this.entityManager.createQuery(jpql, Aluno.class).getResultList();
	}
}
