Vue.createApp({

    data() {
        return {
            errorToats: null,
            errorMsg: null,
            cardType: "none",
            cardColor: "none",
        }
    },
    methods: {
        formatDate: function (date) {
            return new Date(date).toLocaleDateString('en-gb');
        },
        signOut: function () {
            axios.post('/api/logout')
                .then(response => window.location.href = "/web/index.html")
                .catch(() => {
                    this.errorMsg = "Sign out failed"
                    this.errorToats.show();
                })
        },
        create: function (event) {
            event.preventDefault();
            if (this.cardType == "none" || this.cardColor == "none") {
                this.errorMsg = "You must select a card type and color";
                this.errorToats.show();
            } else {
                let config = {
                    headers: {
                        'content-type': 'application/x-www-form-urlencoded'
                    }
                }
                axios.post(`/api/clients/current/cards?cardType=${this.cardType}&cardColor=${this.cardColor}`, config)
                    .then(response => window.location.href = "/web/cards.html")
                    .catch((error) => {
                        this.errorMsg = error.response.data;
                        this.errorToats.show();
                    })
            }
        }
    },
    mounted: function () {
        this.errorToats = new bootstrap.Toast(document.getElementById('danger-toast'));
    }
}).mount('#app')