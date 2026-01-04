public class CuentaAhorro extends CuentaBancaria {
    private double tasaInteres;

    public CuentaAhorro(String iban, String titular, double saldo, double tasaInteres) {
        super(iban, titular, saldo);


        this.tasaInteres = tasaInteres;
    }


    public double getTasaInteres() {
        return tasaInteres;
    }

    @Override
    public String getTipo() {
        return "AHORRO";
    }
}