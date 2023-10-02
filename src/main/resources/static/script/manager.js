const { createApp } = Vue;
createApp({
  data() {
    return {
      clients:[],
      firstName: "",
      lastName: "",
      email: ""
    }
  },
  created(){
    axios.get("/clients")
    .then((respuesta) =>{
      this.clients = respuesta.data._embedded.clients
    })
    .catch(error => console.log(error));
  },
  methods:{
      addClient(){
        axios.post("/clients",{
          firstName: this.firstName, 
          lastName: this.lastName, 
          email: this.email
        })
        .then(function (response){console.log(response);})
        .catch(function(error){console.log(error)});
      }
  }
}).mount('#app')