const app = Vue.createApp({
    data() {
        return {
            accounts: {},  
            amount: 0,
            description: "",
            originNumber: "",
            destinationNumber: ""  
        };
    },

    created() {
        axios.get("/api/clients/current/accounts")
            .then(response => {
                this.accounts = response.data;
                console.log(this.accounts);
            })
            .catch(error => {
                console.log(error);
            });
    },

    methods: {
        generateTransfer(){
            axios.post(`/api/clients/current/transfers`, `amount=${this.amount}&description=${this.description}&originNumber=${this.originNumber}&destinationNumber=${this.destinationNumber}`)
            .then(response=>{
                Swal.fire("successful transfer!")
                .then(()=> location.pathname = "/web/pages/tranfer.html")
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