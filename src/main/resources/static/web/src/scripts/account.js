const { createApp } = Vue


let app = createApp({
    data() {
        return{
            url:'http://localhost:8080/api/clients/favs',
            url2:'http://localhost:8080/api/clients/current',
            url3:'http://localhost:8080/api/clientcurrent/purchaseorder',
            active: 'Profile',
            favs: [],
            client: [],
            orders:[],
            editMode: false,
            newPassword: '',
            changeImage:'',
            oldPassword:'',
            idDelete: null,
        }
    },
    created(){
        this.loadData(this.url)
        this.loadData2(this.url2)
        this.loadData3(this.url3)
    },
    methods:{
        loadData(url){
            axios.get(url)
            .then((data) => {
                this.favs = data.data
                console.log(this.favs);
            })
        },
        loadData2(url){
            axios.get(url)
            .then((data) => {
                this.client = data.data
                console.log(this.client);
            })
        },
        loadData3(url){
            axios.get(url)
            .then((data) => {
                this.orders = data.data
                console.log(this.orders);
            })
        },
        changePassword(){
            axios.patch('/api/clients/current/changePassword',`password=${this.newPassword}&oldPassword=${this.oldPassword}`)
            .then(() => this.editMode = false)
            .catch(err=> console.error(err))
        },
        changeProfileImage(){
            let form = document.querySelector('#profileImage');
            let formData = new FormData(form)
            formData.append('upload_preset', 'r16u29xq')
            axios.post('https://api.cloudinary.com/v1_1/dlfic0owc/image/upload', formData)
                .then(response => {
                    axios.post("/api/clients/current/uploadImage", "image=" + response.data.secure_url)
                        .then(() => {
                            this.loadData2(this.url2)
                            Swal.fire('Uploaded!', '', 'success')
                        })
                        .catch(err => console.error(err))
                })
                .catch(err => console.error(err))
        },
        removeFav(id){
            id = this.idDelete
            axios.delete('/api/clients/favs/delete',`id=${this.idDelete}`).then(() => (console.log("salio")))
        }



    },
    computed:{
        

    },

})

app.mount('#app')

