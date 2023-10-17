package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;


public class ClientLoanDTO {

    private Long ID;
    private Long loanId;
    private String loanName;
    private double amount;
    private int payments;


    public ClientLoanDTO(ClientLoan clientLoan) {
        this.ID = clientLoan.getID(); // Obtener el ID del préstamo del cliente
        this.loanId = clientLoan.getLoan().getID(); // Obtener el ID del préstamo asociado
        this.loanName = clientLoan.getLoan().getName(); // Obtener el nombre del préstamo
        this.amount = clientLoan.getAmount(); // Obtener el monto del préstamo
        this.payments = clientLoan.getPayments(); // Obtener la cantidad de pagos del préstamo
    }

    public Long getID() {
        return ID;
    }

    public Long getLoanId() {
        return loanId;
    }

    public String getLoanName() {
        return loanName;
    }

    public double getAmount() {
        return amount;
    }

    public int getPayments() {
        return payments;
    }

}

//En resumen, esta clase ClientLoanDTO toma una instancia de la clase ClientLoan como parámetro en su constructor y
// extrae información relevante del préstamo del cliente, como su ID, el ID del préstamo asociado, el nombre del préstamo,
// el monto y la cantidad de pagos. Este objeto DTO se utiliza para enviar información sobre un
// préstamo de cliente a través de la API REST de la aplicación.