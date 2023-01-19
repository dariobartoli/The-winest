const app = Vue.createApp({
    data() {
        return {
            cart: [],
            texto: "hola",
            modal: false,
            email: "",
            country: "",
            name: "",
            lastName: "",
            address: "",
            zipcode: "",
            city: "",
            phone: "",
            warningEmail: "",
            warningCountry: "",
            warningName: "",
            warningLastName: "",
            warningAddress: "",
            warningZipcode: "",
            warningCity: "",
            warningPhone: "",
            error1: false,
            error2: false,
            error3: false,
            error4: false,
            error5: false,
            error6: false,
            error7: false,
            error8: false,
            date: "",
            cvv: "",
            cardNumber: "",
            total: 0,
            purchaseOrder: {
                productOrderDTOS: [

                ],
                PaymentMethod: "DEBIT"
            }
          
        }
    },
    mounted() {

        
    },
    created() {

 
            this.getCart()
            console.log(this.cart);
            this.cart.forEach( product =>{

                    this.purchaseOrder.productOrderDTOS.push({
                        productName: product.name,
                        quantity: product.quantity
                    })

            }         
            )
            console.log(this.purchaseOrder);
            this.totalCalc()



    },
    methods: {
        getCart(){

            this.cart = JSON.parse(localStorage.getItem('cart'))
        },
        formPayment() {
            emailRegex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,4})+$/
            if (!emailRegex.test(this.email)) {
                this.warningEmail = "Type your e-mail address"
                this.error1 = true
            } else {
                this.warningEmail = ""
                this.error1 = false
            }
            if (this.country.length == 0) {
                this.warningCountry = "Type your country"
                this.error2 = true
            } else {
                this.warningCountry = ""
                this.error2 = false
            }
            if (this.name.length == 0) {
                this.warningName = "Type your name"
                this.error3 = true
            } else {
                this.warningName = ""
                this.error3 = false
            }
            if (this.lastName.length == 0) {
                this.warningLastName = "Type your last name"
                this.error4 = true
            } else {
                this.warningLastName = ""
                this.error4 = false
            }
            if (this.address.length == 0) {
                this.warningAddress = "Type your address"
                this.error5 = true
            } else {
                this.warningAddress = ""
                this.error5 = false
            }
            if (this.zipcode.length == 0) {
                this.warningZipcode = "Type your zip code"
                this.error6 = true
            } else {
                this.warningZipcode = ""
                this.error6 = false
            }
            if (this.city.length == 0) {
                this.warningCity = "Type your city"
                this.error7 = true
            } else {
                this.warningCity = ""
                this.error7 = false
            }
            if (this.phone.length == 0) {
                this.warningPhone = "Type your number phone"
                this.error8 = true
            } else {
                this.warningPhone = ""
                this.error8 = false
            }
            if (this.country.length > 1 && this.email.length > 1 && this.name.length > 1 && this.lastName.length > 1 && this.address.length > 1 && this.city.length > 1 && this.zipcode != 0
                && this.phone != 0) {
                this.modal = true
            } else {
                this.modal = false
            }
        },
        deleteCaracter() {
            let text = this.cardNumber
            let text2 = text.substring(text.lenght, text.length - 1)
            this.cardNumber = text2
        },
        cardNumberFormat() {
            let flag1 = true;
            if (this.cardNumber.length == 4 && flag1) {
                let first = this.cardNumber + "-"
                this.cardNumber = first
                flag1 = true;
            }
            else if (this.cardNumber.length == 9 && flag1) {
                let first = this.cardNumber + "-"
                this.cardNumber = first
                flag1 = true;
            }
            else if (this.cardNumber.length == 14 && flag1) {
                let first = this.cardNumber + "-"
                this.cardNumber = first
                flag1 = false;
            }
        },
        sendPayment() {
            axios.post('https://lightbank.up.railway.app/api/cards/pay', {
                "cardNumber": this.cardNumber,
                "cardCvv": this.cvv,
                "amount": this.total,
                "description": "The Winest Purchase Successful"
            })
                .then(() => {
                    axios.post("/api/purchaseOrder/create",this.purchaseOrder)
                    .then(()=>{ 
                        
                        Swal.fire(
                            'Payment succesful!',
                            'Check your email for more info about the purchase',
                            'success'
                          )
                        
                        this.modal = false
                    })
                })
                .catch((error) => Swal.fire(
                    error.response.data,
                    'Check your email for more info about the purchase',
                    'success'
                  ))
        },totalCalc(){
            this.cart.forEach(product =>{
                this.total+= product.quantity * product.price
            })
        },purchaseOrder(){

        }
    },computed: {
        
    },
})
app.mount("#app")