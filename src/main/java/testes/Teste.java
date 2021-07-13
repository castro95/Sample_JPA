package testes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;

import dao.AlunoDao;
import dao.AlunoDisciplinaDao;
import dao.DisciplinaDao;
import dao.ProfessorDao;
import modelo.Aluno;
import modelo.AlunoDisciplina;
import modelo.Disciplina;
import modelo.Professor;
import util.JPAUtil;

public class Teste {

	private static final boolean CADASTRAR_ALUNOS = false;
	private static final int NUMERO_ALUNOS = 10000;
	private static final boolean CADASTRAR_PROFESSORES = false;
	private static final int NUMERO_PROFESSORES = 100;
	private static final boolean CADASTRAR_DISCIPLINAS = false;
	private static final int NUMERO_DISCIPLINAS = 500;
	private static final boolean CADASTRAR_ALUNO_DISCIPLINAS = false;
	private static final int MEDIA = 7;
	private static final int MEDIA_MINIMA_RECUPERACAO = 5;
	private static final Random GERADOR = new Random();

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Long dataInicio = System.currentTimeMillis();
		cadastrarAlunos();
		cadastrarProfessores();
		cadastrarDisciplinas();
		cadastrarAlunoDisciplina();
		Long dataFim = System.currentTimeMillis();
		if(CADASTRAR_ALUNOS || CADASTRAR_PROFESSORES || CADASTRAR_DISCIPLINAS || CADASTRAR_ALUNO_DISCIPLINAS)
			System.out.println("Tempo Execução: " + (dataFim - dataInicio) + " ms");
		
		EntityManager entityManager = JPAUtil.getEntityManager();
		AlunoDisciplinaDao alunoDisciplinaDao = new AlunoDisciplinaDao(entityManager);
		
//		AlunoDisciplina alunoDisciplina = alunoDisciplinaDao.buscarPorId(5);
		AlunoDisciplina alunoDisciplina2 = alunoDisciplinaDao.buscarAlunoDisciplinaComAlunoEDisciplinaEProfessor(5);
		entityManager.close();
//		System.out.println(alunoDisciplina); // Exception in thread "main" org.hibernate.LazyInitializationException: could not initialize proxy [modelo.Disciplina#1] - no Session
		System.out.println(alunoDisciplina2);
	}

	private static void cadastrarAlunos() {
		if (CADASTRAR_ALUNOS) {
			EntityManager entityManager = JPAUtil.getEntityManager();
			entityManager.getTransaction().begin();
			AlunoDao alunoDao = new AlunoDao(entityManager);
			try {
				for (int i = 0; i < NUMERO_ALUNOS; i++) {
					Aluno aluno = new Aluno();
					aluno.setNome("Aluno_" + (i + 1));
					aluno.setCpf(Teste.gerarCpf());
					alunoDao.cadastrar(aluno);
				}
				entityManager.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
				entityManager.getTransaction().rollback();
			}
			entityManager.close();
		}
	}

	private static void cadastrarProfessores() {
		if (CADASTRAR_PROFESSORES) {
			EntityManager entityManager = JPAUtil.getEntityManager();
			entityManager.getTransaction().begin();
			ProfessorDao professorDao = new ProfessorDao(entityManager);
			try {
				for (int i = 0; i < NUMERO_PROFESSORES; i++) {
					Professor professor = new Professor();
					professor.setNome("Professor_" + (i + 1));
					professor.setCpf(Teste.gerarCpf());
					professorDao.cadastrar(professor);
				}
				entityManager.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
				entityManager.getTransaction().rollback();
			}
			entityManager.close();
		}
	}

	private static void cadastrarDisciplinas() {
		if (CADASTRAR_DISCIPLINAS) {
			EntityManager entityManager = JPAUtil.getEntityManager();
			entityManager.getTransaction().begin();
			DisciplinaDao disciplinaDao = new DisciplinaDao(entityManager);
			ProfessorDao professorDao = new ProfessorDao(entityManager);

			try {
				for (int i = 0; i < NUMERO_DISCIPLINAS; i++) {
					Disciplina disciplina = new Disciplina();
					disciplina.setNome("Disciplina_" + (i + 1));
					disciplina.setDataInicio(LocalDate.now());
					disciplina.setDataFim(LocalDate.of(2021, 12, 21));
					disciplina.setProfessor(
							professorDao.buscarPorId(Math.abs(GERADOR.nextInt() % NUMERO_PROFESSORES) + 1));
					disciplinaDao.cadastrar(disciplina);
				}
				entityManager.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
				entityManager.getTransaction().rollback();
			}
		}
	}

	private static void cadastrarAlunoDisciplina() {
		if (CADASTRAR_ALUNO_DISCIPLINAS) {
			EntityManager entityManager = JPAUtil.getEntityManager();
			entityManager.getTransaction().begin();
			DisciplinaDao disciplinaDao = new DisciplinaDao(entityManager);
			AlunoDao alunoDao = new AlunoDao(entityManager);
			AlunoDisciplinaDao alunoDisciplinaDao = new AlunoDisciplinaDao(entityManager);

			try {
				for (int i = 0; i < NUMERO_DISCIPLINAS; i++) {
					int qtdAlunosPorDisciplina = NUMERO_ALUNOS / NUMERO_DISCIPLINAS;
					Disciplina disciplina = disciplinaDao.buscarPorId(i + 1);
					List<Integer> idAlunosDisciplina = new ArrayList<>();

					for (int j = 0; j < qtdAlunosPorDisciplina; j++) {
						Integer idAluno = Math.abs((GERADOR.nextInt() % NUMERO_ALUNOS)) + 1;
						while (idAlunosDisciplina.contains(idAluno)) {
							idAluno = Math.abs((GERADOR.nextInt() % NUMERO_ALUNOS)) + 1;
						}
						idAlunosDisciplina.add(idAluno);

						Aluno aluno = alunoDao.buscarPorId(idAluno);
						AlunoDisciplina alunoDisciplina = new AlunoDisciplina(disciplina, aluno);
						alunoDisciplina.setNota1(gerarNota());
						alunoDisciplina.setNota2(gerarNota());
						alunoDisciplina.setNota3(gerarNota());
						alunoDisciplina.setNota4(gerarNota());

						Double mediaAluno = (alunoDisciplina.getNota1() + alunoDisciplina.getNota2()
								+ alunoDisciplina.getNota3() + alunoDisciplina.getNota4()) / 4;
						if (mediaAluno >= MEDIA_MINIMA_RECUPERACAO && mediaAluno < MEDIA)
							alunoDisciplina.setNotaRecuperacao(gerarNota());
						else
							alunoDisciplina.setNotaRecuperacao(0.0);

						alunoDisciplinaDao.cadastrar(alunoDisciplina);
					}
				}
				entityManager.getTransaction().commit();
			} catch (Exception e) {
				e.printStackTrace();
				entityManager.getTransaction().rollback();
			}

		}
	}

	private static String gerarCpf() {
		String cpf = "";
		for (int i = 0; i < 11; i++) {
			cpf += Math.abs(GERADOR.nextInt() % 10);
		}
		return cpf;
	}

	private static Double gerarNota() {
		Double nota = 0.0;
		nota = Math.abs(GERADOR.nextDouble() % 11) + Math.abs(GERADOR.nextInt() % 11);
		for (int i = 0; i < 2 && nota < 3; i++) {
			nota = Math.abs(GERADOR.nextDouble() % 11) + Math.abs(GERADOR.nextInt() % 11);
		}
		if (nota > 10)
			nota = 10.0;

		nota = Double.parseDouble(String.format("%.2f", nota).replace(",", "."));

		return nota;
	}
}
