package project001;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class AlunoDAO {

	@PersistenceContext
	private EntityManager entityManager;

	public void createAluno(Aluno a) {
		try {
			entityManager.persist(a);
		} catch (Exception e) {
			System.err.println("Erro ao criar aluno");
			e.printStackTrace();
			throw e;
		}
	}

	public Aluno findById(int mat) {
		Aluno alunoFound = null;

		try {
			alunoFound = entityManager.find(Aluno.class, mat);
			if (alunoFound == null) {
				throw new EntityNotFoundException("Aluno não encontrado");
			}
		} catch (Exception e) {
			System.err.println("Erro ao buscar o aluno.");
			throw e;
		}
		return alunoFound;
	}

	public void updateAluno(Aluno a) {
		try {
			Aluno alunoFound = entityManager.find(Aluno.class, a.getMatricula());
			if (alunoFound != null) {
				// se o aluno que queremos atualizar realmente estiver no banco de dados
				entityManager.merge(a);
			} else {
				// aluno nao foi encontrado no bd
				System.err.println("Aluno nao encontrado");
			}
		} catch (Exception e) {
			System.err.println("Erro ao atualizar aluno.");
			e.printStackTrace();
		}

	}

	public void deleteAluno(int mat) {
		Aluno alunoFound = null;
		try {
			alunoFound = entityManager.find(Aluno.class, mat);
			if (alunoFound == null) {
				// se o aluno que quisermos deletar nao for encontrado
				throw new EntityNotFoundException("Aluno não encontrado");
			} else {
				entityManager.remove(alunoFound);
			}
		} catch (EntityNotFoundException e) {
			System.err.println("Erro ao deletar aluno " + e);
		}
	}
	
    public List<Aluno> findAll() {
        try {
            Query query = entityManager.createQuery("SELECT a FROM Aluno a");
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro ao buscar todos os alunos.");
            throw e;
        }
    }
    
    @SuppressWarnings("unchecked")
    public List<Object[]> contarAlunosPorCurso(){
    	try {
        	String sql = "SELECT c.sigla, COUNT(a.matricula) " +
                    "FROM Curso c LEFT JOIN Aluno a ON c.sigla = a.curso.sigla " +
                    "GROUP BY c.sigla";
        	Query query = entityManager.createQuery(sql);
        	return query.getResultList();
    	}catch(Exception e) {
    		System.err.println("Erro ao contar alunos por curso: " + e.getMessage());
    		throw e;
    	}
    }
}
