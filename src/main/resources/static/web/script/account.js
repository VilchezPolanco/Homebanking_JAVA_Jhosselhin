const { createApp } = Vue;
createApp({
data() {
    return {
      account: [],
      transactions: [],
    };
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
      })
      .catch((error) => {
        console.log(error);
      });
  },
  methods: {},
}).mount("#app");