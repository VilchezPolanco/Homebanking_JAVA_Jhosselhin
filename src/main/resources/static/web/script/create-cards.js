const { createApp } = Vue;

  createApp({
    data() {
      return {
        cards:[],
        cardType:"",
        cardColor:""

      }
    },
    created(){

    },
    methods:{
    generateCard(){
        axios.post("/api/clients/current/card", `type=${this.cardType}&color=${this.cardColor}`)
        .then((response) => {
            location.href = "http://localhost:8080/WEB/pages/cards.html"

        }).catch((err) => console.log(err))
    },
    logOut() {
        axios.post("/api/logout").then((response) => {
          console.log("Signed out");
          location.pathname = "/web/index.html"; // Redirige al usuario a la página de inicio.
        });
      },
    }

  }).mount('#app')