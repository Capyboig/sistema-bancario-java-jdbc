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


    public boolean transferir(String ibanOrigen, String ibanDestino, double cantidad) {


        Connection con = null;
        PreparedStatement pstRestar = null;
        PreparedStatement pstSumar = null;


        String sqlRestar = "UPDATE cuentas SET saldo = saldo - ? WHERE iban = ?";
        String sqlSumar  = "UPDATE cuentas SET saldo = saldo + ? WHERE iban = ?";

        try {


            con = ConexionBD.conectar();


            con.setAutoCommit(false);




            pstRestar = con.prepareStatement(sqlRestar);



            pstRestar.setDouble(1, cantidad);
            pstRestar.setString(2, ibanOrigen);


            int filasRestadas = pstRestar.executeUpdate();


            pstSumar = con.prepareStatement(sqlSumar);


            pstSumar.setDouble(1, cantidad);
            pstSumar.setString(2, ibanDestino);


            int filasSumadas = pstSumar.executeUpdate();



            if (filasRestadas > 0 && filasSumadas > 0) {

                con.commit();
                System.out.println("[EXITO] Dinero movido correctamente.");
                return true;
            } else {

                con.rollback();
                System.out.println("[ERROR]Uno de los IBAN no es correcto. Deshaciendo...");
                return false;
            }

        } catch (SQLException e) {

            try {

                if (con != null) con.rollback();
                System.out.println("[ERROR GRAVE] Se hizo Rollback por motivos de seguridad (Se revirtieron los cambios).");
            } catch (SQLException ex) {
                System.out.println("[ERROR GRAVE] intentando hacer rollback: " + ex.getMessage());
            }
            return false;

        } finally {

            try {
                if (pstRestar != null) pstRestar.close();
                if (pstSumar != null) pstSumar.close();
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println("[ERROR] cerrando recursos");
            }
        }
    }



}