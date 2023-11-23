package project001;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ConversationScoped
public class AlunoListController implements Serializable{
	private static final long serialVersionUID = 1L;

	@Inject
	private AlunoDAO alunoDAO;

	@Inject
	private Conversation conversation;

	private List<Aluno> alunos;

	public void atualizarListaAlunos() {
		try {
			iniciarConversacao();
			
			alunos = alunoDAO.findAll();

		} catch (Exception e) {
			System.err.println("Erro ao atualizar lista de alunos: " + e.getMessage());
		} finally {
			encerrarConversacao();
		}
	}

	public List<Aluno> getAlunos() {
		if (alunos == null) {
			atualizarListaAlunos();
		}
		return alunos;
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
