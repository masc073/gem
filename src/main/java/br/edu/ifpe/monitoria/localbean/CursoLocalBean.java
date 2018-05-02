package br.edu.ifpe.monitoria.localbean;

import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.edu.ifpe.monitoria.entidades.ComponenteCurricular;
import br.edu.ifpe.monitoria.entidades.Curso;
import br.edu.ifpe.monitoria.utils.DelecaoRequestResult;

@Stateless
@LocalBean
public class CursoLocalBean 
{
	@PersistenceContext(name = "monitoria", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	public List<Curso> consultaCursos()
	{
		List<Curso> cursos = em.createNamedQuery("Curso.findAll", Curso.class).getResultList();
		
		return cursos;
	}
	
	public boolean atualizaCurso(Curso curso)
	{
		em.merge(curso);

		return true;
	}
	
	public Curso consultaCursoById(Long id)
	{
		Curso cursoPorId = em.createNamedQuery("Curso.findById", Curso.class).setParameter("id", id).getSingleResult();
		
		return cursoPorId;
	}
	
	public Curso consultaCursoByName(String nome)
	{
		Curso curso = em.createNamedQuery("Curso.findByNome", Curso.class).setParameter("nome", nome).getSingleResult();
		
		return curso;
	}
	
	public DelecaoRequestResult deletaCurso(Long id)
	{
		DelecaoRequestResult delecao = new DelecaoRequestResult();
		
		List<ComponenteCurricular> componentesDoCurso = em.createNamedQuery("ComponenteCurricular.findByCurso", ComponenteCurricular.class).setParameter("id", id).getResultList();
		
		if(componentesDoCurso != null && !componentesDoCurso.isEmpty())
		{
			delecao.errors.add("Existem componentes vinculados � este curso, remova-os primeiro!");
		}
		else
		{
			try {
				Curso cursoDeletado = em.createNamedQuery("Curso.findById", Curso.class).setParameter("id", id).getSingleResult();
				try {
					em.remove(cursoDeletado);
				} catch (Exception e) {
					delecao.errors.add("Problemas na remo��o da entidade no banco de dados, contate o suporte.");
				}
			} catch (NoResultException e) {
				delecao.errors.add("Entidade n�o encontrada no banco de dados, contate o suporte.");
			}
		}
		
		return delecao;
	}
	
	public boolean persisteCurso(@NotNull @Valid Curso curso)
	{
		em.persist(curso);
		
		return true;
	}
}
