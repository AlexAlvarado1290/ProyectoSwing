/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Estudiante extends Persona{
    private String carnet;
    private List<Nota> notas;
    
    public Estudiante(String nombre, String apellido, String carnet) {
        super(nombre, apellido);
        this.carnet = carnet;
        this.notas = new ArrayList<>();
        System.out.println("Estudiante creado: " + nombre + " " + apellido + " con carnet: " + carnet);
    }
    
    /**
     *Cargamos el constructor
     */
    public Estudiante(){
        super();
        this.notas = new ArrayList<>();
    }
    
    public void agregarNota(Nota nota) {
        this.notas.add(nota);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public double calcularPromedio() {
        return notas.stream().mapToDouble(Nota::getValor).average().orElse(0);
    }
    
    public void imprimirNotas() {
        for (Nota nota : notas) {
            System.out.println(nota.getTipo() + ": " + nota.getValor() + " - " + nota.getDescripcion());
        }
    }

    public List<Nota> getNotas() {
        return notas;
    }
    
}
