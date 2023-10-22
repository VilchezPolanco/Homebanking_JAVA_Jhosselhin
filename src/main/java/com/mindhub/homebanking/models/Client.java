package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity  // Crea una tabla en la base de datos con esa clase
public class Client {

    // Crear atributos y propiedades, estas van a ser representadas como columnas en la base de datos
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO, generator = "native" )
    @GenericGenerator( name = "native", strategy = "native" )
    private Long id;

    private String firstName, lastName, email;

    // ---- One to many de Client to Account
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER) //relacion muchos a uno ->(Eager) que me traiga cuenta y propietario
    private Set<Account> accounts = new HashSet<>(); //Set java.util

    // ---- One to many de Client to ClientLoan
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();

    // ---- One to many de Client to Card
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();

    // Constructores
    public Client() {
    }
    public Client(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    // Metodos o comportamientos
    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // metodos accesores de las propiedades relacionales

    public Set<Account> getAccounts() { return accounts; }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    public void setClientLoans(Set<ClientLoan> clientLoans) {
        this.clientLoans = clientLoans;
    }

    public Set<Card> getCards() { return cards; }

    public void setCards(Set<Card> card) { this.cards = card; }

    // Métodos para agregar cuentas y préstamos de cliente a este cliente
    public void addAccount(Account account){
        account.setClient(this);
        accounts.add(account);
    }

    public void addClientLoan(ClientLoan clientLoan) {
        clientLoan.setClient(this);
        this.clientLoans.add(clientLoan);
    }

    public void addCard(Card card) {
        card.setClient(this);
        this.cards.add(card);
    }
}
