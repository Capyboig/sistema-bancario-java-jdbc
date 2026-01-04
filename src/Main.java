import java.util.Scanner;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            CuentaDAO dao = new CuentaDAO();

            System.out.println(" -------- BANCO -------");
            System.out.println("1.- Añadir Cuenta Bancaria");
            System.out.println("2.- Buscar Datos");
            int opcionMenu = sc.nextInt();


            switch (opcionMenu) {
                case 1:

                    System.out.println("Introduce el iban");
                    String iban = sc.nextLine();

                    System.out.println("Introduce el titular");
                    String titular = sc.nextLine();

                    System.out.println("Introduce el saldo inicial");
                    int saldo = sc.nextInt();

                    System.out.println("Introduce el interes (Ej: 0,05)");
                    int interes = sc.nextInt();

                    CuentaAhorro nuevaCuenta = new CuentaAhorro(iban, titular, saldo, interes);


                    dao.guardar(nuevaCuenta);


                    sc.nextLine();
                    break;





                case 2:
                    sc.nextLine();
                    System.out.println("Introduce el IBAN a buscar");
                    String ibanBusqueda = sc.nextLine();


                    CuentaBancaria cuenta = dao.buscarPorIban(ibanBusqueda);

                    if (cuenta != null) {
                        System.out.println();
                        System.out.println("----------------");
                        System.out.println("Cuenta encontrada");
                        System.out.println("----------------");

                        System.out.println("Titular: " + cuenta.getTitular());
                        System.out.println("Saldo: " + cuenta.getSaldo());
                        System.out.println("IBAN: " + cuenta.getIban());


                        if (cuenta instanceof CuentaAhorro) {
                            System.out.println("Tasa interes: " + ((CuentaAhorro) cuenta).getTasaInteres());
                        } else {
                            System.out.println("No existe el IBAN");
                        }





                    }


                    break;

                default:
                    System.out.println("Introduciste una opcion no valida");


                    break;



            }























        }

    }
}