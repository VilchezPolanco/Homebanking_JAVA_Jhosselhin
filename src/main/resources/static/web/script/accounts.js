const { createApp } = Vue;
createApp({
  data() {
    return {
      clients: [],
      /* accounts: [], */
    };
  },
  created() {
    axios.get("http://localhost:8080/api/clients/1")
      .then((response) => {
        this.clients = response.data;
        console.log(this.clients);
        /* this.accounts = response.data.accounts; */
      })
      .catch((error) => {
        console.error(error);
      });
  },
  methods: {
    logOut() {
      axios.post("/api/logout").then((response) => {
        console.log("Signed out");
        location.pathname = "/web/index.html"; // Redirige al usuario a la página de inicio.
      });
    },
  },
}).mount("#app");