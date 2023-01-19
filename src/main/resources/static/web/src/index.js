const app = Vue.createApp({
  data(){
      return{
          url: "http://localhost:8080/api/clients/current",
          client: "",
          clientUp: "",
      }
  },
  created(){
      this.getdata()
  },
  methods:{
      getdata(){
          axios.get(this.url)
          .then(data => {
            this.client = data.data.firstName
            this.clientUp = data.data.firstName.toUpperCase()
            console.log(this.client)

          })
      },
  },
})
app.mount("#app")

