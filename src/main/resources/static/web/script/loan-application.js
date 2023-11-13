const app = Vue.createApp({
    data() {
        return {
            loans: [],
            accounts: {},
            loanId: 0,
            amount: 0,
            payments: 0,
            destinationAccount: "",
            messageError: "",
        };
    },

    created() {
        axios.get("/api/loans")
        .then(response=>{
        this.loans = response.data
        console.log(this.loans);
        })
        .catch(error => this.messageError = error.response.data)

        axios.get("/api/clients/current/accounts")
            .then(response => {
                this.accounts = response.data;
                console.log(this.accounts);
            })
            .catch(error => {
                console.log(error);
            });
    },
    methods:{
        newLoan(){
            axios.post("/api/loans", {"id":`${this.loanId}`,"amount":`${this.amount}`,"payments":`${this.payments}`,"destinationAccount":`${this.destinationAccount}`})
            .then(response=>{
                Swal.fire("successful loan!")
                .then(()=> location.pathname = "/web/pages/loan-application.html")
            })
            .catch(error => this.messageError = error.response.data)
            },

        logOut() {
            axios.post("/api/logout").then((response) => {
              console.log("Signed out");
              location.pathname = "/web/index.html"; // Redirige al usuario a la página de inicio.
            });
        }
    },
})
app.mount('#app');