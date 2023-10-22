package com.mindhub.homebanking.models;

import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {

    //atributos y propiedades
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO, generator = "native" )
    @GenericGenerator( name = "native", strategy = "native" )
    private Long id;
    private String number;
    private LocalDate creationDate;
    private Double balance;
    @ManyToOne(fetch = FetchType.EAGER) //relacion muchos a uno ->(Eager) que me traiga cuenta y propietario
    @JoinColumn(name = "client_id") //nombre en la tabla
    private Client client;  // atributo para hacer una relacion con la clase Client

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER) //relacion muchos a uno ->(Eager) que me traiga cuenta y propietario
    private Set<Transaction> transactions = new HashSet<>(); //Set java.util


    //Constructores
    public Account() {
    }

    public Account(String number, LocalDate creationDate, Double balance) {
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
    }

    //Metodos

    //@JsonIgnore
    public Client getClient() {
        return client;
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public Double getBalance() {
        return balance;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(Transaction transaction){  //método addTransaction se utiliza para agregar transacciones a la cuenta.
        transaction.setAccount(this);
        this.transactions.add(transaction);
    }
}
