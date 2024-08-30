/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package files;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import model.Estudiante;
import model.Examen;
/**
 *
 * @author Alex Alvarado
 */
public class CrearArchivoEstudiantes {
    public static void main(String[] args) {
        List<Estudiante> estudiantes = new ArrayList<>();
        estudiantes.add(new Estudiante("Juan", "Perez", "2021001"));
        estudiantes.add(new Estudiante("Maria", "Lopez", "2021002"));

        // Agregar algunas notas para los estudiantes
        estudiantes.get(0).agregarNota(new Examen(85, "Examen Bimestre 1"));
        estudiantes.get(0).agregarNota(new Examen(90, "Examen Bimestre 2"));
        estudiantes.get(1).agregarNota(new Examen(75, "Examen Bimestre 1"));
        estudiantes.get(1).agregarNota(new Examen(80, "Examen Bimestre 2"));

        try (FileOutputStream fos = new FileOutputStream("C:\\Users\\Alex Alvarado\\Documents\\NetBeansProjects\\ProyectoSwing\\src\\main\\java\\files\\estudiantes.dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(estudiantes);
            System.out.println("Archivo creado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
