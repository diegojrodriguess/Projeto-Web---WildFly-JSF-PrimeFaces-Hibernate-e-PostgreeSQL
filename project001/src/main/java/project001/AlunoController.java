package project001;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;


@Named
@ConversationScoped
public class AlunoController implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Aluno aluno = new Aluno();
	
	@Inject
	private AlunoDAO alunoDAO = new AlunoDAO();
	
	@Inject
	private CursoDAO cursoDAO = new CursoDAO();
	
	
    @Inject
    private Conversation conversation;
	
	
	public Aluno getAluno() {
        return aluno;
    }
	
    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
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
    
    
	public void salvar() {
        try {
        	System.out.println("Iniciando save");
        	iniciarConversacao();
        	
        	String siglaCurso = aluno.getCurso().getSigla();
        	
        	Curso curso = cursoDAO.findBySigla(siglaCurso);
        	
        	aluno.setCurso(curso);
        	
        	System.out.println("Curso selecionado: " + aluno.getCurso());
            alunoDAO.createAluno(aluno);
            System.out.println("Aluno salvo com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao salvar aluno: " + e.getMessage());
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
