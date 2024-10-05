package utils;

import model.Estudiante;
import model.Examen;
import model.Nota;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/bd_control_notas";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("Driver JDBC no encontrado", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos.");
            e.printStackTrace();
        }
    }

    public static List<Estudiante> obtenerEstudiantesBD() {
        List<Estudiante> estudiantes = new ArrayList<>();

        try (Connection conn = getConnection()) {
            String sqlEstudiantes = "SELECT e.id_estudiante, p.nombre, p.apellido, e.carnet FROM Estudiante e JOIN Persona p ON e.id_persona = p.id_persona";
            PreparedStatement stmtEstudiantes = conn.prepareStatement(sqlEstudiantes);
            ResultSet rsEstudiantes = stmtEstudiantes.executeQuery();

            while (rsEstudiantes.next()) {
                int idEstudiante = rsEstudiantes.getInt("id_estudiante");
                String nombre = rsEstudiantes.getString("nombre");
                String apellido = rsEstudiantes.getString("apellido");
                String carnet = rsEstudiantes.getString("carnet");

                Estudiante estudiante = new Estudiante(nombre, apellido, carnet);
                estudiantes.add(estudiante);

                String sqlNotas = "SELECT tipo, valor, descripcion FROM Nota WHERE id_estudiante = ?";
                PreparedStatement stmtNotas = conn.prepareStatement(sqlNotas);
                stmtNotas.setInt(1, idEstudiante);
                ResultSet rsNotas = stmtNotas.executeQuery();

                while (rsNotas.next()) {
                    String tipo = rsNotas.getString("tipo");
                    double valor = rsNotas.getDouble("valor");
                    String descripcion = rsNotas.getString("descripcion");

                    Nota nota = new Examen(valor, descripcion);
                    estudiante.agregarNota(nota);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al conectar o consultar la base de datos.");
        }

        return estudiantes;
    }

    public static String generarCarnet() {
        String baseCarnet = "20240";
        int correlativo = 1;

        try (Connection conn = getConnection()) {
            String sql = "SELECT COUNT(*) AS total FROM Estudiante";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if (rs.next()) {
                correlativo += rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al generar el carnet.");
        }

        return baseCarnet + String.format("%02d", correlativo);
    }

    public static void guardarEstudiante(String nombre, String apellidos, String carnet) {
        try (Connection conn = getConnection()) {
            String sqlPersona = "INSERT INTO Persona (nombre, apellido) VALUES (?, ?)";
            PreparedStatement stmtPersona = conn.prepareStatement(sqlPersona, Statement.RETURN_GENERATED_KEYS);
            stmtPersona.setString(1, nombre);
            stmtPersona.setString(2, apellidos);
            stmtPersona.executeUpdate();

            ResultSet rsPersona = stmtPersona.getGeneratedKeys();
            if (rsPersona.next()) {
                int idPersona = rsPersona.getInt(1);

                String sqlEstudiante = "INSERT INTO Estudiante (id_persona, carnet) VALUES (?, ?)";
                PreparedStatement stmtEstudiante = conn.prepareStatement(sqlEstudiante);
                stmtEstudiante.setInt(1, idPersona);
                stmtEstudiante.setString(2, carnet);
                stmtEstudiante.executeUpdate();

                System.out.println("Estudiante guardado con éxito: " + carnet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al guardar el estudiante.");
        }
    }

    public static void actualizarEstudiantes(List<Estudiante> estudiantes) {
        try (Connection conn = getConnection()) {
            for (Estudiante estudiante : estudiantes) {
                String sqlEstudiante = "UPDATE Persona p JOIN Estudiante e ON p.id_persona = e.id_persona SET p.nombre = ?, p.apellido = ? WHERE e.carnet = ?";
                PreparedStatement stmtEstudiante = conn.prepareStatement(sqlEstudiante);
                stmtEstudiante.setString(1, estudiante.getNombre());
                stmtEstudiante.setString(2, estudiante.getApellido());
                stmtEstudiante.setString(3, estudiante.getCarnet());
                stmtEstudiante.executeUpdate();

                String sqlEliminarNotas = "DELETE FROM Nota WHERE id_estudiante = (SELECT id_estudiante FROM Estudiante WHERE carnet = ?)";
                PreparedStatement stmtEliminarNotas = conn.prepareStatement(sqlEliminarNotas);
                stmtEliminarNotas.setString(1, estudiante.getCarnet());
                stmtEliminarNotas.executeUpdate();

                for (Nota nota : estudiante.getNotas()) {
                    String sqlNota = "INSERT INTO Nota (tipo, valor, descripcion, id_estudiante) VALUES (?, ?, ?, (SELECT id_estudiante FROM Estudiante WHERE carnet = ?))";
                    PreparedStatement stmtNota = conn.prepareStatement(sqlNota);
                    stmtNota.setString(1, nota.getTipo());
                    stmtNota.setDouble(2, nota.getValor());
                    stmtNota.setString(3, nota.getDescripcion());
                    stmtNota.setString(4, estudiante.getCarnet());
                    stmtNota.executeUpdate();
                }
            }
            System.out.println("Estudiantes actualizados con éxito.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al actualizar los estudiantes.");
        }
    }

    public static void escribirEstudiantes(List<Estudiante> estudiantes) {
        actualizarEstudiantes(estudiantes);
    }
    
    public static void eliminarEstudiantePorCarnet(String carnet) {
        try (Connection conn = getConnection()) {
            String sqlEliminarNotas = "DELETE FROM Nota WHERE id_estudiante = (SELECT id_estudiante FROM Estudiante WHERE carnet = ?)";
            PreparedStatement stmtEliminarNotas = conn.prepareStatement(sqlEliminarNotas);
            stmtEliminarNotas.setString(1, carnet);
            stmtEliminarNotas.executeUpdate();

            String sqlEliminarEstudiante = "DELETE FROM Estudiante WHERE carnet = ?";
            PreparedStatement stmtEliminarEstudiante = conn.prepareStatement(sqlEliminarEstudiante);
            stmtEliminarEstudiante.setString(1, carnet);
            stmtEliminarEstudiante.executeUpdate();

            String sqlEliminarPersona = "DELETE FROM Persona WHERE id_persona = (SELECT id_persona FROM Estudiante WHERE carnet = ?)";
            PreparedStatement stmtEliminarPersona = conn.prepareStatement(sqlEliminarPersona);
            stmtEliminarPersona.setString(1, carnet);
            stmtEliminarPersona.executeUpdate();

            System.out.println("Estudiante con carnet " + carnet + " eliminado con éxito.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al eliminar el estudiante.");
        }
    }
}
