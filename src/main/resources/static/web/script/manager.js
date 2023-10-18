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
    axios.get("http://localhost:8080/api/clients")
    .then((respuesta) =>{
      this.clients = respuesta.data
      console.log(this.clients);
    })
    .catch(error => console.log(error));
  },
  methods:{
      addClient(){
        axios.post("http://localhost:8080/api/clients",{
          firstName: this.firstName,
          lastName: this.lastName,
          email: this.email
        })
        .then(function (response){console.log(response);})
        .catch(function(error){console.log(error)});
      }
  }
}).mount('#app')