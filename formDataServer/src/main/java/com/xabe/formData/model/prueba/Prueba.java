package com.xabe.formData.model.prueba;

import java.io.Serializable;

import com.xabe.formData.model.EntityBase;
import javax.xml.bind.annotation.XmlRootElement;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown=true)
public class Prueba extends EntityBase implements Serializable {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_prueba.id
     *
     * @mbggenerated Wed Jun 18 12:48:11 CEST 2014
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_prueba.nombre
     *
     * @mbggenerated Wed Jun 18 12:48:11 CEST 2014
     */
    private String nombre;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_prueba.nombreFichero
     *
     * @mbggenerated Wed Jun 18 12:48:11 CEST 2014
     */
    private String nombrefichero;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_prueba
     *
     * @mbggenerated Wed Jun 18 12:48:11 CEST 2014
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_prueba.id
     *
     * @return the value of t_prueba.id
     *
     * @mbggenerated Wed Jun 18 12:48:11 CEST 2014
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_prueba.id
     *
     * @param id the value for t_prueba.id
     *
     * @mbggenerated Wed Jun 18 12:48:11 CEST 2014
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_prueba.nombre
     *
     * @return the value of t_prueba.nombre
     *
     * @mbggenerated Wed Jun 18 12:48:11 CEST 2014
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_prueba.nombre
     *
     * @param nombre the value for t_prueba.nombre
     *
     * @mbggenerated Wed Jun 18 12:48:11 CEST 2014
     */
    public void setNombre(String nombre) {
        this.nombre = nombre == null ? null : nombre.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_prueba.nombreFichero
     *
     * @return the value of t_prueba.nombreFichero
     *
     * @mbggenerated Wed Jun 18 12:48:11 CEST 2014
     */
    public String getNombrefichero() {
        return nombrefichero;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_prueba.nombreFichero
     *
     * @param nombrefichero the value for t_prueba.nombreFichero
     *
     * @mbggenerated Wed Jun 18 12:48:11 CEST 2014
     */
    public void setNombrefichero(String nombrefichero) {
        this.nombrefichero = nombrefichero == null ? null : nombrefichero.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_prueba
     *
     * @mbggenerated Wed Jun 18 12:48:11 CEST 2014
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Prueba other = (Prueba) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getNombre() == null ? other.getNombre() == null : this.getNombre().equals(other.getNombre()))
            && (this.getNombrefichero() == null ? other.getNombrefichero() == null : this.getNombrefichero().equals(other.getNombrefichero()));
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_prueba
     *
     * @mbggenerated Wed Jun 18 12:48:11 CEST 2014
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getNombre() == null) ? 0 : getNombre().hashCode());
        result = prime * result + ((getNombrefichero() == null) ? 0 : getNombrefichero().hashCode());
        return result;
    }
}
