import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class CuentaDAO {

    public void guardar(CuentaBancaria cuenta) {


        String sql = "INSERT INTO cuentas (iban, titular, saldo, tipo, tasas_interes) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, cuenta.getIban());
            pst.setString(2, cuenta.getTitular());
            pst.setDouble(3, cuenta.getSaldo());
            pst.setString(4, cuenta.getTipo());

            if (cuenta instanceof CuentaAhorro) {

                pst.setDouble(5, ((CuentaAhorro) cuenta).getTasaInteres());
            } else {
                pst.setDouble(5, 0.0);
            }

            pst.executeUpdate();
            System.out.println("Cuenta guardada.");

        } catch (SQLException e) {
            System.out.println("Falló la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }





    public CuentaBancaria buscarPorIban(String ibanABuscar) {
        CuentaBancaria cuentaEncontrada = null;

        String sql = "SELECT * FROM cuentas WHERE iban = ?";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, ibanABuscar);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String iban = rs.getString("iban");
                String titular = rs.getString("titular");
                double saldo = rs.getDouble("saldo");
                double tasa = rs.getDouble("tasas_interes");


                cuentaEncontrada = new CuentaAhorro(iban, titular, saldo, tasa);
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar " + e.getMessage());

        }

        return cuentaEncontrada;
    }

}