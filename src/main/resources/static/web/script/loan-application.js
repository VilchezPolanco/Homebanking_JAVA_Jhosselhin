const app = Vue.createApp({
    data() {
        return {
            loans: [],
            accounts: {}
        };
    },

    created() {
        axios.get("/api/loans")
        .then(response=>{
        this.loans = response.data
        console.log(this.loans);
        })
        .catch(error => console.log(error))

        axios.get("/api/clients/current/accounts")
            .then(response => {
                this.accounts = response.data;
                console.log(this.accounts);
            })
            .catch(error => {
                console.log(error);
            });
    },
    method:{

    },
    logOut() {
        axios.post("/api/logout").then((response) => {
          console.log("Signed out");
          location.pathname = "/web/index.html"; // Redirige al usuario a la página de inicio.
        });
    }

})
app.mount('#app');