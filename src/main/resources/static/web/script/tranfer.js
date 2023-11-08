const app = Vue.createApp({
    data() {
        return {
            accounts: {},
        };
    },

    created() {
        axios.get("/api/clients/current/accounts")
            .then(response => {
                this.accounts = response.data;
            })
            .catch(error => {
                console.log(error);
            });
    },

    methods: {
        
    },

    logOut() {
        axios.post("/api/logout").then((response) => {
            console.log("Signed out");
            location.pathname = "/web/index.html"; // Redirige al usuario a la página de inicio.
        });
    }
})
app.mount('#app');