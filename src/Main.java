import java.util.Scanner;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            CuentaDAO dao = new CuentaDAO();
            boolean salir = false;

            while (!salir) {
                System.out.println("");
                System.out.println(" --------------- BANCO ---------------");
                System.out.println("1.- Añadir Cuenta Bancaria");
                System.out.println("2.- Buscar Datos");
                System.out.println("3.- Transferencia");
                int opcionMenu = sc.nextInt();
                sc.nextLine();


                switch (opcionMenu) {
                    case 1:
                        System.out.println();
                        System.out.println("###################################");
                        System.out.println("        CREACION DE CUENTA          ");
                        System.out.println("    Estos seran los requisitos");
                        System.out.println("1.- Un IBAN");
                        System.out.println("2.- Un titular");
                        System.out.println("3.- Saldo inicial");
                        System.out.println("4.- Intereses");
                        System.out.println("###################################");
                        System.out.println();


                        String iban = sc.nextLine();

                        System.out.println("Introduce el titular");
                        String titular = sc.nextLine();

                        System.out.println("Introduce el saldo inicial");
                        double saldo = sc.nextDouble();

                        System.out.println("Introduce el interes (Ej: 0,05)");
                        double interes = sc.nextDouble();

                        CuentaAhorro nuevaCuenta = new CuentaAhorro(iban, titular, saldo, interes);


                        dao.guardar(nuevaCuenta);


                        sc.nextLine();


                        break;





                    case 2:
                        System.out.println();
                        System.out.println("###################################");
                        System.out.println("        BUSQUEDA DE DATOS          ");
                        System.out.println("    Estos seran los requisitos");
                        System.out.println("1.- Un IBAN existente");
                        System.out.println("###################################");
                        System.out.println();


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



                    case 3:
                        System.out.println();
                        System.out.println("###################################");
                        System.out.println("        TRANSACCION DE DINERO      ");
                        System.out.println("    Estos seran los requisitos");
                        System.out.println("1.- Un IBAN del emisor");
                        System.out.println("2.- Un IBAN del receptor");
                        System.out.println("3.- Saldo valido");
                        System.out.println("###################################");
                        System.out.println();

                        System.out.println(" ---- Sistema de transferencias ----");


                        System.out.println("Introduce el IBAN de el que va a salir el dinero");
                        String ibanOrigen = sc.nextLine();

                        System.out.println("Introduzca el IBAN del receptor del dinero");
                        String ibanDestino = sc.nextLine();


                        System.out.println("Introduce la cantidad de dinero a transferir");
                        double cantidad = sc.nextDouble();
                        sc.nextLine();


                        System.out.println("[PROCESO] Generando la transferencia");
                        boolean exito = dao.transferir(ibanOrigen, ibanDestino, cantidad);

                        if (exito) {
                            System.out.println("[EXITO] Transferencia generada satisfactoriamente");
                        } else {
                            System.out.println("[ERROR] Transferencia fallida (Revisa el IBAN o la cantidad de dinero)");
                        }


                        break;

                    default:

                        System.out.println("Introduciste una opcion no valida");


                        break;



                }
            }
























        }




    }




}