package com.ammgroup.sep.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "RECLAMACIONES")
public class Reclamacion {
	
    @Id
    @Column(name="RECLAMACION_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Column(name="NUMERO", precision=5, nullable=false, unique=false)
    private int numero;
    
	@ManyToOne
	@JoinColumn(name="SOCIO_ID", nullable=false)
    private Socio socio;
	
    @ManyToOne
	@JoinColumn(name="AGENCIA_ID", nullable=true)
    private Agencia agencia;
    
    @Column(name="FECHA_RECLAMACION", nullable=true, unique=false)
    @Temporal(TemporalType.DATE)
    private Date fechaReclamacion;
    
    @Column(name="RECLAMACION_COMENTARIO", length=250, nullable=false, unique=true)
    private String reclamacionComentario;
    
    @Column(name="FECHA_RESPUESTA", nullable=true, unique=false)
    @Temporal(TemporalType.DATE)
    private Date fechaRespuesta;
    
    @Column(name="ANOTACIONES", length=250, nullable=true, unique=true)
    private String anotaciones;
    
    @ManyToOne
	@JoinColumn(name="ESTADORECLAM_ID", nullable=false)
    private EstadoReclamacion estadoReclamacion;

	public Reclamacion() {
		super();
	}

	public Reclamacion(Socio socio, Agencia agencia, Date fechaReclamacion, String reclamacionComentario,
			Date fechaRespuesta, String anotaciones, EstadoReclamacion estadoReclamacion) {
		super();
		this.socio = socio;
		this.agencia = agencia;
		this.fechaReclamacion = fechaReclamacion;
		this.reclamacionComentario = reclamacionComentario;
		this.fechaRespuesta = fechaRespuesta;
		this.anotaciones = anotaciones;
		this.estadoReclamacion = estadoReclamacion;
	}

	public Socio getSocio() {
		return socio;
	}

	public void setSocio(Socio socio) {
		this.socio = socio;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public Date getFechaReclamacion() {
		return fechaReclamacion;
	}

	public void setFechaReclamacion(Date fechaReclamacion) {
		this.fechaReclamacion = fechaReclamacion;
	}

	public String getReclamacionComentario() {
		return reclamacionComentario;
	}

	public void setReclamacionComentario(String reclamacionComentario) {
		this.reclamacionComentario = reclamacionComentario;
	}

	public Date getFechaRespuesta() {
		return fechaRespuesta;
	}

	public void setFechaRespuesta(Date fechaRespuesta) {
		this.fechaRespuesta = fechaRespuesta;
	}

	public String getAnotaciones() {
		return anotaciones;
	}

	public void setAnotaciones(String anotaciones) {
		this.anotaciones = anotaciones;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public EstadoReclamacion getEstadoReclamacion() {
		return estadoReclamacion;
	}

	public void setEstadoReclamacion(EstadoReclamacion estadoReclamacion) {
		this.estadoReclamacion = estadoReclamacion;
	}

	public Long getId() {
		return id;
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reclamacion )) return false;
        return id != null && id.equals(((Reclamacion) o).getId());
    }
 
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    
    @Override
    public String toString() {  
    	//return id.toString() + " - " + descripcion.strip() + "; ";
    	return String.valueOf(numero).strip();
    } 
}
