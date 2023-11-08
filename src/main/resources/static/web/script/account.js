const { createApp } = Vue;
createApp({
data() {
    return {
      account: [],
      transactions: [],
      date: "",
      hour: ""
    };
  },
  methods: {
    logOut() {
      axios.post("/api/logout").then((response) => {
        console.log("Signed out");
        location.pathname = "/web/index.html"; // Redirige al usuario a la página de inicio.
      });
    },
  },
  created() {
    const params = new URLSearchParams(location.search) /*te deja usar los metodos de location */    
    const idParam = params.get("id") /* uno de los metodos get para capturar el id*/

    axios
      .get(`http://localhost:8080/api/accounts/${idParam}`)
      .then((response) => {
        this.account = response.data;
        console.log(this.account);
        this.transactions = response.data.transactions;
        /* this.transactions.sort((a, b) => b.id - a.id); */
        console.log(this.transactions);
        this.date = this.transactions[0].date.slice(0, 10);
        console.log(this.date)
        this.hour = this.transactions[0].date.slice(11, 16);
        console.log(this.hours);
      })
      .catch((error) => {
        console.log(error);
      });
  },
  methods: {},
}).mount("#app");