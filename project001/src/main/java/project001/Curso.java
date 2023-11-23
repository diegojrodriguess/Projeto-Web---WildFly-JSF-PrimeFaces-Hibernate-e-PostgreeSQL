package project001;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Curso {
	@Id
	private String sigla;

	private String nome;

	@ElementCollection
	@CollectionTable(name = "curso_materias", joinColumns = @JoinColumn(name = "curso_sigla"))
	@Column(name = "materias")
	private List<String> materias;

	@OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
	private List<Aluno> alunos;

	public List<String> getMaterias() {
		return materias;
	}

	public void setMaterias(List<String> materias) {
		this.materias = materias;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return sigla;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Curso curso = (Curso) o;
		return Objects.equals(getSigla(), curso.getSigla());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getSigla());
	}

}
