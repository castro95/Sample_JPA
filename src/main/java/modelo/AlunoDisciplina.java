package modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "aluno_disciplina")
public class AlunoDisciplina {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_disciplina")
	private Disciplina disciplina;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_aluno")
	private Aluno aluno;

	@Column(name = "nota_1")
	private Double nota1;
	@Column(name = "nota_2")
	private Double nota2;
	@Column(name = "nota_3")
	private Double nota3;
	@Column(name = "nota_4")
	private Double nota4;
	@Column(name = "nota_recuperacao")
	private Double notaRecuperacao;

	public AlunoDisciplina() {
	}

	public AlunoDisciplina(Disciplina disciplina, Aluno aluno) {
		super();
		this.disciplina = disciplina;
		this.aluno = aluno;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Double getNota1() {
		return nota1;
	}

	public void setNota1(Double nota1) {
		this.nota1 = nota1;
	}

	public Double getNota2() {
		return nota2;
	}

	public void setNota2(Double nota2) {
		this.nota2 = nota2;
	}

	public Double getNota3() {
		return nota3;
	}

	public void setNota3(Double nota3) {
		this.nota3 = nota3;
	}

	public Double getNota4() {
		return nota4;
	}

	public void setNota4(Double nota4) {
		this.nota4 = nota4;
	}

	public Double getNotaRecuperacao() {
		return notaRecuperacao;
	}

	public void setNotaRecuperacao(Double notaRecuperacao) {
		this.notaRecuperacao = notaRecuperacao;
	}

	@Override
	public String toString() {
		return "AlunoDisciplina [id=" + id + ", disciplina=" + disciplina + ", aluno=" + aluno + ", nota1=" + nota1
				+ ", nota2=" + nota2 + ", nota3=" + nota3 + ", nota4=" + nota4 + ", notaRecuperacao=" + notaRecuperacao
				+ "]";
	}
}
