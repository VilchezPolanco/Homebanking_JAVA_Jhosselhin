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
        axios("http://localhost:8080/api/clients/1")
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
        formatDate(date) {
            return new Date(date).toLocaleString('en-US', { month: '2-digit', year: '2-digit' });
        }
    }
  }).mount('#app')