package dao;

import java.util.List;

import javax.persistence.EntityManager;

import modelo.Aluno;
import modelo.AlunoDisciplina;
import modelo.Disciplina;
import modelo.Professor;

public class AlunoDisciplinaDao {
	private EntityManager entityManager;
	
	public AlunoDisciplinaDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void cadastrar(AlunoDisciplina alunoDisciplina) {
		this.entityManager.persist(alunoDisciplina);
	}
	
	public AlunoDisciplina buscarPorId(Integer id) {
		return this.entityManager.find(AlunoDisciplina.class, id);
	}
	
	public List<AlunoDisciplina> buscarTodos() {
		String jpql = "SELECT ad FROM AlunoDisciplina ad ";
		return this.entityManager.createQuery(jpql, AlunoDisciplina.class).getResultList();
	}
	
	// o ideal seria realizar somente 1 select. Tal como foi feito no método buscarAlunoDisciplinaComAlunoEDisciplinaEProfessor(Integer)
	public AlunoDisciplina buscarAlunoDisciplinaComDisciplinaEProfessor(Integer id) {
		String jpql = "SELECT ad FROM AlunoDisciplina ad JOIN FETCH ad.disciplina where ad.id = :id";
		AlunoDisciplina alunoDisciplina = this.entityManager.createQuery(jpql, AlunoDisciplina.class).setParameter("id", id).getSingleResult();
		
		String jpql2 = "SELECT d.professor FROM Disciplina d where d.id = :id";
		modelo.Professor professor = this.entityManager.createQuery(jpql2, modelo.Professor.class).setParameter("id", alunoDisciplina.getDisciplina().getId()).getSingleResult();
		
		alunoDisciplina.getDisciplina().setProfessor(professor);
		
		return alunoDisciplina;
	}
	
	public AlunoDisciplina buscarAlunoDisciplinaComAlunoEDisciplinaEProfessor(Integer id) {
		String jpql = "SELECT ad, ad.aluno, ad.disciplina, ad.disciplina.professor FROM AlunoDisciplina ad where ad.id = :id";
		Object[] retornoConsulta = this.entityManager.createQuery(jpql, Object[].class).setParameter("id", id).getSingleResult();

		AlunoDisciplina alunoDisciplina = (AlunoDisciplina) retornoConsulta[0];
		Aluno aluno = (Aluno) retornoConsulta[1];
		Disciplina disciplina = (Disciplina) retornoConsulta[2];
		Professor professor = (Professor) retornoConsulta[3];
		
		disciplina.setProfessor(professor);		
		alunoDisciplina.setAluno(aluno);
		alunoDisciplina.setDisciplina(disciplina);
		
		return alunoDisciplina;
	}
	
}
