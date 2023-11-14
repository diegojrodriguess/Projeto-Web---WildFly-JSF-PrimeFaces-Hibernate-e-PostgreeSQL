package project001;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.EntityManager;

public class AlunoDAO {

	private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("postgres");

	private static EntityManager entityManager = entityManagerFactory.createEntityManager();

	public void createAluno(Aluno a) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(a);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			System.err.println("Erro ao criar aluno");
			e.printStackTrace();
			entityManager.getTransaction().rollback();// desfaz as alteracoes feitas no bd
		}
	}

	public Aluno findById(int mat) {
		Aluno alunoFound = null;

		try {
			entityManager.getTransaction().begin();
			alunoFound = entityManager.find(Aluno.class, mat);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			System.err.println("Erro ao buscar o aluno.");
		}
		if (alunoFound == null) {
			throw new EntityNotFoundException("Aluno não encontrado");
		}

		return alunoFound;
	}

	public void updateAluno(Aluno a) {
		try {
			entityManager.getTransaction().begin();
			Aluno alunoFound = entityManager.find(Aluno.class, a.getMatricula());
			if (alunoFound != null) {
				// se o aluno que queremos atualizar realmente estiver no banco de dados
				entityManager.merge(a);
			} else {
				// aluno nao foi encontrado no bd
				System.err.println("Aluno nao encontrado");
				entityManager.getTransaction().rollback();// desfaz as alteracoes feitas no bd
			}
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			System.err.println("Erro ao atualizar aluno.");
			entityManager.getTransaction().rollback();// desfaz as alteracoes feitas no bd
		}

	}

	public void deleteAluno(int mat) {
		Aluno alunoFound = null;
		try {
			entityManager.getTransaction().begin();
			alunoFound = entityManager.find(Aluno.class, mat);
			if (alunoFound == null) {
				// se o aluno que quisermos deletar nao for encontrado
				throw new EntityNotFoundException("Aluno não encontrado");
			} else {
				entityManager.remove(alunoFound);
				entityManager.getTransaction().commit();
			}
		} catch (EntityNotFoundException e) {
			System.err.println("Erro ao deletar aluno " + e);
			entityManager.getTransaction().rollback();
		}
	}
}
