const app = Vue.createApp({
  data() {
    return {
      apiProducts: "/api/products",
      apiFavorites: "/api/clients/favs",
      products: [],
      filteredProducts: [],
      categorys: [],
      filterData: {
        category: "",
        name: "",
        priceMax: 0,
      },
      favorites: [],
      cart: [],
      subtotal:0,
      total: 0,
      items:0,
    };
  },
  created() {
    this.getProducts(this.apiProducts);
    console.log("funciona");
  },
  methods: {
    getProducts(api) {
      axios.get(api).then((data) => {
        console.log(data);
        this.products = data.data;
        this.filteredProducts = this.products;
        this.getCategorys(this.products);
        console.log(this.categorys);
      });
    },
    getFavorites(api){
      axios.get(api).then((data) => {
        
        this.favorites = data.data;
        
      });
    },
    getCategorys(array) {
      let categorias = [];

      array.forEach((element) => {
        categorias.push(element.category);
      });

      let categorias1 = new Set(categorias);
      let categorias2 = [...categorias1];

      this.categorys = categorias2;
    },
    getVariertys(array) {
      let categorias = [];

      array.forEach((element) => {
        categorias.push(element.category);
      });

      let categorias1 = new Set(categorias);
      let categorias2 = [...categorias1];

      this.categorys = categorias2;
    },
    wine(wineCategory) {
      if (
        wineCategory == "WHITE" ||
        wineCategory == "RED" ||
        wineCategory == "ROSE"
      ) {
        return wineCategory + " WINE";
      } else return wineCategory;
    },
    filterCategory(array) {
      let filteredProducts = [];

      if (
        this.filterData.category.length &&
        this.filterData.category != "ALL"
      ) {
        filteredProducts = array.filter(
          (product) => product.category == this.filterData.category
        );

        return filteredProducts;
      } else {
        filteredProducts = this.products;
        return filteredProducts;
      }
    },
    filterName(array) {
      let filteredProducts = [];

      if (this.filterData.name.length) {
        filteredProducts = array.filter((product) =>
          product.name
            .toLowerCase()
            .includes(this.filterData.name.toLowerCase())
        );

        return filteredProducts;
      } else {
        filteredProducts = array;
        return filteredProducts;
      }
    },
    filterPrice(array) {
      let filteredProducts = [];

      if (this.filterData.priceMax > 0) {
        filteredProducts = array.filter(
          (producto) => producto.price <= this.filterData.priceMax
        );
      } else if (this.filterData.priceMax <= 0) {
        filteredProducts = array;
      }

      return filteredProducts;
    },
    addFavorite(name) {


      axios.post(`/api/clients/favs?name=${name}`).then(() =>Swal.fire({
        position: 'top-end',
        icon: 'success',
        title: 'Product added to favorites',
        showConfirmButton: false,
        timer: 1000
      })
      ).catch(error =>  Swal.fire(
        'Oops!',
        error.response.data,
        'warning'
      ))


  },addToCart(product){


    
    let existe = this.cart.some( item => item.id == product.id)

    if(existe){

      this.cart.forEach(producto =>{

        if(producto.id == product.id){

          producto.quantity++
          
          if(producto.quantity > producto.stock){

            producto.quantity = producto.stock

            Swal.fire(
              'Out of stock',
              'You have reached the limit of available stock',
              'warning'
            )

          }else{

            Swal.fire({
              position: 'top-end',
              icon: 'success',
              title: 'Product added to cart!',
              showConfirmButton: false,
              timer: 1000
            })

          }
        }

      })


    }
    else{
    this.cart.push({
      id:product.id,
      category: product.category,
      variety: product.variety,
      name:product.name,
      image: product.image,
      price:product.price,
      quantity: 1,
      stock: product.stock,
    })
    
    Swal.fire({
  position: 'top-end',
  icon: 'success',
  title: 'Product added to cart!',
  showConfirmButton: false,
  timer: 1000
})
    this.saveCart()
  }

    
    

    

  },removeFromCart(product){
    let newCart = this.cart.filter(producto => producto.id != product.id)

    this.cart = newCart
    this.saveCart()
  },saveCart(){
    localStorage.setItem('cart',JSON.stringify(this.cart))
  




}},
  computed: {
    filterFinal() {
      if (
        this.filterData.category.length ||
        this.filterData.name.length ||
        this.filterData.priceMax.length
      ) {
        this.filteredProducts = this.filterPrice(
          this.filterName(this.filterCategory(this.products))
        );
      }
    },calculateSomeThings(){

      let subtotal1 = 0
      let total1 = 0
      let items1 = 0

      this.cart.forEach(product => {
        items1 += product.quantity
        subtotal1 += product.price * product.quantity
        


        

      })
      if(subtotal1 != 0){
          total1 = subtotal1 + 30 + 35
      }
      else{total1 = 0}
      this.subtotal = subtotal1.toFixed(2)
      this.total = total1.toFixed(2)
      this.items = items1


    }



  },mounted() {

    let local = JSON.parse(localStorage.getItem('cart'))

    if(local !== null){
	    this.cart = local
    }
    
  },
}).mount("#app");
