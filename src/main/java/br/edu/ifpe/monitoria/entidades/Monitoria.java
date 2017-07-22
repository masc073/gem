package br.edu.ifpe.monitoria.entidades;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@SequenceGenerator (name = "SEQUENCIA_MONITORIA",
					sequenceName = "SQ_MONITORIA",
					initialValue = 1,
					allocationSize = 1)
@Table(name = "TB_MONITORIA")
@NamedQueries({
	@NamedQuery(name = "Monitoria.findAll", query = "SELECT m FROM Monitoria m"),
	@NamedQuery(name = "Monitoria.findById", query = "SELECT m FROM Monitoria m WHERE m.id = :id"),
	@NamedQuery(name = "Monitoria.findByProfessor", query = "SELECT pm FROM PlanoMonitoria pm WHERE pm.cc = ("
			+ "SELECT cc FROM ComponenteCurricular cc WHERE cc.professor = :id)")
})
@Access(AccessType.FIELD)
public class Monitoria implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	 @GeneratedValue (strategy = GenerationType.SEQUENCE, generator="SEQUENCIA_MONITORIA")
	 private Long id;
	 
	 @NotNull(message = "{mensagem.associacao}{tipo.aluno}")
	 @OneToOne (fetch = FetchType.LAZY, optional = false)
	 @JoinColumn(name = "ID_ALUNO", referencedColumnName = "ID_USUARIO")
	 private Aluno aluno;
	 
	 @NotNull(message = "{mensagem.associacao}{tipo.plano}")
	 @OneToOne (fetch = FetchType.LAZY, optional = false)
	 @JoinColumn(name = "ID_PLANO_MONITORIA", referencedColumnName = "ID")
	 private PlanoMonitoria planoMonitoria;
	 
	 @Column (name="BOOL_BOLSA")
	 private boolean bolsa;

	 @Column (name="BOOL_SELECIONADO")
	 private boolean selecionado;

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public boolean isBolsa() {
		return bolsa;
	}

	public void setBolsa(boolean bolsa) {
		this.bolsa = bolsa;
	}

	public Long getId() {
		return id;
	}

	public PlanoMonitoria getPlanoMonitoria() {
		return planoMonitoria;
	}

	public void setPlanoMonitoria(PlanoMonitoria planoMonitoria) {
		this.planoMonitoria = planoMonitoria;
	}

	public boolean isSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}
}
