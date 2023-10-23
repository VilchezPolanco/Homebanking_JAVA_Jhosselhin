const { createApp } = Vue

  createApp({
    data() {
      return {
        clients:{},
        debitCards:[],
        creditCards:[],
      }
    },
    created(){
        axios("http://localhost:8080/api/clients/currents")
        .then(response => {
            this.clients = response.data;
            console.log(this.clients.cards)
            this.debitCards = this.clients.cards.filter(card => card.type == "DEBIT");
            console.log(this.debitCards)
            this.creditCards = this.clients.cards.filter(card => card.type == "CREDIT");
            console.log(this.creditCards)            
        })
        .catch(err => console.log(err))
    },
    methods:{
        logOut() {
          axios.post("/api/logout").then((response) => {
            console.log("Signed out");
            location.pathname = "/web/index.html"; // Redirige al usuario a la página de inicio.
          });
        },

        formatDate(date) {
            return new Date(date).toLocaleString('en-US', { month: '2-digit', year: '2-digit' });
        }
    }
  }).mount('#app')