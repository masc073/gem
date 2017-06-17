package br.edu.ifpe.monitoria.localbean;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import br.edu.ifpe.monitoria.entidades.Usuario;

@Stateless
@LocalBean
public class UsuarioLocalBean 
{
	@PersistenceContext(name = "monitoria", type = PersistenceContextType.TRANSACTION)
	private EntityManager em;
	
	public List<Usuario> consultaUsuarios()
	{
		List<Usuario> usuarios = em.createNamedQuery("Usuario.findAll", Usuario.class).getResultList();
		
		return usuarios;
	}
	
	public boolean atualizaUsuario(Usuario usuario)
	{
		Usuario usuarioAtualizar = em.createNamedQuery("Usuario.findById", Usuario.class).setParameter("id", usuario.getId()).getSingleResult();
		
		usuarioAtualizar.setEmail(usuario.getEmail());
		usuarioAtualizar.setNome(usuario.getNome());
		usuarioAtualizar.setSenha(usuario.getSenha());
		
		em.merge(usuarioAtualizar);
		
		return true;
	}
	
	public Usuario consultaUsuarioPorEmailSenha(String email, String senha)
	{
		Usuario userResult = new Usuario();
		
		try {
			userResult = em.createNamedQuery("Usuario.findByEmailSenha", Usuario.class).setParameter("email", email)
																					   .setParameter("senha", senha)
																					   .getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
		}
		
		return userResult;
	}
	
	public Usuario consultaUsuarioById(Long id)
	{
		Usuario usuarioPorId = em.createNamedQuery("Usuario.findById", Usuario.class).setParameter("id", id).getSingleResult();
		
		return usuarioPorId;
	}
	
	public boolean deletaUsuario(Long id)
	{
		Usuario usuarioDeletado = em.createNamedQuery("Usuario.findById", Usuario.class).setParameter("id", id).getSingleResult();
		
		em.remove(usuarioDeletado);
		
		return true;
	}
	
	public boolean persisteUsuario(@NotNull @Valid Usuario usuario)
	{
		em.persist(usuario);
		
		return true;
	}
}