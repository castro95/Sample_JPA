package modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "aluno")
public class Aluno {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Embedded
	private DadosPessoais dadosPessoais = new DadosPessoais();

	@OneToMany(mappedBy = "aluno", fetch = FetchType.LAZY)
	private List<AlunoDisciplina> alunoDisciplina = new ArrayList<>();
	
	public Aluno() {
	}

	public Aluno(String nome, String cpf) {
		this.dadosPessoais = new DadosPessoais(nome, cpf);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return dadosPessoais.getNome();
	}

	public void setNome(String nome) {
		dadosPessoais.setNome(nome);
	}

	public String getCpf() {
		return dadosPessoais.getCpf();
	}

	public void setCpf(String cpf) {
		dadosPessoais.setCpf(cpf);
	}

	public List<AlunoDisciplina> getAlunoDisciplina() {
		return alunoDisciplina;
	}

	public void setAlunoDisciplina(List<AlunoDisciplina> alunoDisciplina) {
		this.alunoDisciplina = alunoDisciplina;
	}

	@Override
	public String toString() {
		return "Aluno [id=" + id + ", nome=" + this.getNome() + ", cpf=" + this.getCpf() + "]";
	}
}
