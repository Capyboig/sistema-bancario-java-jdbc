
public abstract class CuentaBancaria {

    protected String iban;
    protected String titular;
    protected double saldo;

    // CONSTRUCTOR: Prepara los datos básicos cuando nace la cuenta
    public CuentaBancaria(String iban, String titular, double saldo) {
        this.iban = iban;
        this.titular = titular;

        // Validación básica (RA4 - Integridad)
        if (saldo < 0) {
            System.out.println("[AVISO] Saldo inicial negativo corregido a 0");
            this.saldo = 0;
        } else {
            this.saldo = saldo;
        }
    }


    public String getIban() { return iban; }
    public String getTitular() { return titular; }
    public double getSaldo() { return saldo; }


    public abstract String getTipo();
}