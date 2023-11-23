package project001;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Named
@ConversationScoped
public class CursoController implements Serializable {
	private static final long serialVersionUID = 1L;

	private Curso curso = new Curso();

	@Inject
	private CursoDAO cursoDAO;

	@Inject
	private Conversation conversation;

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public void iniciarConversacao() {
		if (conversation.isTransient()) {
			conversation.begin();
		}
	}

	public void encerrarConversacao() {
		if (!conversation.isTransient()) {
			conversation.end();
		}
	}
	
	public List<String> getAllMaterias(){
		return Arrays.asList("Calculo", "Fisica", "Algoritmos", "Programação Orientada a Objetos",
                "Banco de Dados", "Gestao Empresarial", "Biomedicina", "Telecomunicacoes", "Automacao");
	}

	public void salvar() {
		try {
			iniciarConversacao();
			cursoDAO.createCurso(curso);
			System.out.println("Curso salvo com sucesso!");
		} catch (Exception e) {
			System.err.println("Erro ao salvar curso: " + e.getMessage());
		} finally {
			encerrarConversacao();
		}
	}

	private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
	}

	private void writeObject(java.io.ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
	}
}
