/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Alex Alvarado
 */
public class Examen extends Nota {
    public Examen(double valor, String descripcion) {
        super(valor, descripcion);
    }

    @Override
    public String getTipo() {
        return "Examen";
    }
}
