const { createApp } = Vue;

createApp({
  data() {
    return {
      firstName: "",
      lastName: "",
      email: "",
      password: "",
    };
  },
  created() {},
  methods: {
    login(){
      axios.post("/api/login", `email=${this.email}&password=${this.password}`)
        .then((response) => {
          console.log("hola login");
          /* console.log("Signed in"); */
          location.pathname = "/web/pages/accounts.html";
        })
        .catch((error) => {
          console.log(error);
        });
    },
    register() {
      axios.post("/api/clients",
          `firstName=${this.firstName}&lastName=${this.lastName}&email=${this.email}&password=${this.password}`
          )
          .then((response) => {
          console.log("holaaa");
          /* console.log("User registered"); */
          this.login()
        })
        .catch((error) => {
          console.error("Error registering user:", error);
        });
    },
  },
}).mount("#app");