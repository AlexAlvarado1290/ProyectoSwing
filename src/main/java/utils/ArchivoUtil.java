/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import model.Estudiante;
import model.Examen;
import model.Nota;
import model.Profesor;
import model.Tarea;

/**
 *
 * @author Alex Alvarado
 */
public class ArchivoUtil {
    
    public static void escribirEstudiantes(String archivo, List<Estudiante> estudiantes) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            for (Estudiante estudiante : estudiantes) {
                System.out.println("Escribiendo estudiante: " + estudiante.getNombre() + " " + estudiante.getApellido());
                writer.write(estudiante.getNombre() + "," + estudiante.getApellido() + "," + estudiante.getCarnet());
                writer.newLine();

                for (Nota nota : estudiante.getNotas()) {
                    System.out.println("Escribiendo nota: " + nota.getTipo() + " " + nota.getValor());
                    writer.write(nota.getTipo() + "," + nota.getValor() + "," + nota.getDescripcion());
                    writer.newLine();
                }
                writer.write("----");
                writer.newLine();
            }
        } catch (IOException e) {
            throw e;
        }

    }
    
    public static List<Estudiante> cargarEstudiantesDesdeArchivo(String rutaArchivo) {
        List<Estudiante> estudiantes = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            Estudiante estudiante = null;

            while ((linea = reader.readLine()) != null) {
                if (linea.equals("----")) {
                    if (estudiante != null) {
                        estudiantes.add(estudiante);
                    }
                    estudiante = null;
                } else {
                    String[] partes = linea.split(",");
                    if (partes.length == 3) {
                        if (estudiante == null) {
                            estudiante = new Estudiante(partes[0], partes[1], partes[2]);
                        } else {
                            String tipo = partes[0];
                            double valor = Double.parseDouble(partes[1]);
                            String descripcion = partes[2];

                            Nota nota;
                            switch (tipo) {
                                case "Examen" -> nota = new Examen(valor, descripcion);
                                case "Tarea" -> nota = new Tarea(valor, descripcion);
                                default -> {
                                    System.err.println("Tipo de nota desconocido: " + tipo);
                                    continue;
                                }
                            }

                            estudiante.agregarNota(nota);
                        }
                    }
                }
            }

            // Agregar el último estudiante si el archivo no termina con "----"
            if (estudiante != null) {
                estudiantes.add(estudiante);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return estudiantes;
    }
    
}
