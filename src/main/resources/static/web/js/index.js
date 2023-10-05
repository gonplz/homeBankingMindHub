Vue.createApp({
    data() {
        return {
            email: "",
            password: "",
            firstName: "",
            lastName: "",
            errorToats: null,
            errorMsg: "",
            showSignUp: false,
        }
    },
    methods: {
        signIn: function (event) {
            event.preventDefault();
            let config = {
                headers: {
                    'content-type': 'application/x-www-form-urlencoded'
                }
            }
            axios.post('/api/login', `email=${this.email}&password=${this.password}`, config)
                .then(response => window.location.href = "/web/accounts.html")
                .catch(() => {
                    this.errorMsg = "Sign in failed, check the information"
                    this.errorToats.show();
                })
        },
        signUp: function (event) {
            event.preventDefault();
            let config = {
                headers: {
                    'content-type': 'application/x-www-form-urlencoded'
                }
            }
            axios.post('/api/clients', `firstName=${this.firstName}&lastName=${this.lastName}&email=${this.email}&password=${this.password}`, config)
                .then(() => { this.signIn(event) })
                .catch(() => {
                    this.errorMsg = "Sign up failed, check the information"
                    this.errorToats.show();
                })
        },
        showSignUpToogle: function () {
            this.showSignUp = !this.showSignUp;
        },
        formatDate: function (date) {
            return new Date(date).toLocaleDateString('en-gb');
        }
    },
    mounted: function () {
        this.errorToats = new bootstrap.Toast(document.getElementById('danger-toast'));
    }
}).mount('#app')

