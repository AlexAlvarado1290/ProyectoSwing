/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alex Alvarado
 */
public class Profesor extends Persona implements Serializable{
    private static final long serialVersionUID = 1L;
    private String idProfesor;
    private List<Materia> materias;

    public Profesor(String nombre, String apellido, String idProfesor) {
        super(nombre, apellido);
        this.idProfesor = idProfesor;
        this.materias = new ArrayList<>();
    }

    public String getIdentificacion() {
        return idProfesor;
    }

    public void agregarMateria(Materia materia) {
        this.materias.add(materia);
    }

    public List<Materia> getMaterias() {
        return materias;
    }
}
