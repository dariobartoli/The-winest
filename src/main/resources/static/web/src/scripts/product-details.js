const app = Vue.createApp({
    data(){
        return{
            url: `/api/products/`,
            id: (new URLSearchParams(location.search)).get("id"),
            wine: [],
            wineNext: 0,
            wineBack: 0,
        }
    },
    created(){
        this.getdata()
        this.allWinesData()
        
    },
    methods:{
        getdata(){
            axios.get(this.url + this.id)
            .then(data => {
                this.wine = data.data
                this.wineNext = this.wine.id +1
                this.wineBack = this.wine.id -1
                console.log(this.wine.id)
            })
        },
        allWinesData(){
            axios.get("/api/products")
            .then(data =>{
                this.wines = data.data
                console.log(this.wines)
            })
        },
        modificarSaldo(saldo){
            return new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }).format(saldo);
        },

    },computed: {
      
    },
})
app.mount("#app")