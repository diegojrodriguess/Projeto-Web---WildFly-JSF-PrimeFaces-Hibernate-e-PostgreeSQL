package project001;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
public class CursoDAO {

	@PersistenceContext
	private EntityManager entityManager;

	public void createCurso(Curso curso) {
		try {
			entityManager.persist(curso);
		} catch (Exception e) {
			System.err.println("Erro ao criar curso");
			e.printStackTrace();
			throw e;
		}
	}

	public Curso findBySigla(String sigla) {
		Curso cursoEncontrado = null;
		try {
			cursoEncontrado = entityManager.find(Curso.class, sigla);
		} catch (Exception e) {
			System.err.println("Erro ao buscar o curso.");
			e.printStackTrace();
		}
		return cursoEncontrado;
	}
	public void updateCurso(Curso curso) {
        try {
            Curso cursoFound = entityManager.find(Curso.class, curso.getSigla());
            if (cursoFound != null) {
                // Se o curso que queremos atualizar realmente estiver no banco de dados
                entityManager.merge(curso);
            } else {
                // Curso não foi encontrado no BD
                System.err.println("Curso não encontrado");
            }
        } catch (Exception e) {
            System.err.println("Erro ao atualizar curso.");
            e.printStackTrace();
        }
    }

    public void deleteCurso(String sigla) {
        Curso cursoFound = null;
        try {
            cursoFound = entityManager.find(Curso.class, sigla);
            if (cursoFound == null) {
                // Se o curso que quisermos deletar não for encontrado
                throw new EntityNotFoundException("Curso não encontrado");
            } else {
                entityManager.remove(cursoFound);
            }
        } catch (EntityNotFoundException e) {
            System.err.println("Erro ao deletar curso " + e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Curso> findAll() {
        try {
            Query query = entityManager.createQuery("SELECT DISTINCT c FROM Curso c LEFT JOIN FETCH c.materias");
            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Erro ao buscar todos os cursos.");
            throw e;
        }
    }
    
}
