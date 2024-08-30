/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import model.Estudiante;
import model.Profesor;

/**
 *
 * @author Alex Alvarado
 */
public class ArchivoUtil {
    @SuppressWarnings("unchecked")
    public static List<Estudiante> leerEstudiantes(String archivo) throws IOException, ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream(archivo);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (List<Estudiante>) ois.readObject();
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Profesor> leerProfesores(String archivo) throws IOException, ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream(archivo);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (List<Profesor>) ois.readObject();
        }
    }
    
    public static void guardarEstudiantes(List<Estudiante> estudiantes, String archivo) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(archivo);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(estudiantes);
        }
    }

    public static void guardarProfesores(List<Profesor> profesores, String archivo) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(archivo);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(profesores);
        }
    }
}
