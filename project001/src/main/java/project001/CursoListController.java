package project001;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ConversationScoped
public class CursoListController implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	private CursoDAO cursoDAO;

	@Inject
	private Conversation conversation;

	private List<Curso> cursos;
	public void atualizarListacursos() {
		try {
			iniciarConversacao();
			cursos = cursoDAO.findAll();
			
			for (Curso curso: cursos) {
				curso.getMaterias().size();
			}
		} catch (Exception e) {
			System.err.println("Erro ao atualizar lista de cursos: " + e.getMessage());
		} finally {
			encerrarConversacao();
		}
	}

	public List<Curso> getCursos() {
		if (cursos == null) {
			atualizarListacursos();
		}
		return cursos;
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
}
