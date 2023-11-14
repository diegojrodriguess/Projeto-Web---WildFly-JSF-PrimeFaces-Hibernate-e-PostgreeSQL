package project001;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

@ManagedBean
@RequestScoped
public class AlunoController {
	private Aluno aluno = new Aluno();
	private AlunoDAO alunoDAO = new AlunoDAO();
	
	
	public Aluno getAluno() {
        return aluno;
    }
	
    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }
    
    
	public void salvar() {
        try {
            alunoDAO.createAluno(aluno);
            System.out.println("Aluno salvo com sucesso!");
        } catch (Exception e) {
            System.err.println("Erro ao salvar aluno: " + e.getMessage());
        }
	}
}
