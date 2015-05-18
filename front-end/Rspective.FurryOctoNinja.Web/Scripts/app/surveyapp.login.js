(function () {
    angular.module("surveyapp")
        .controller("LoginController", login);

    login.$inject = ["api", "authStorage" ];

    function login(api, authStorage) {
        var self = this;
        self.login = "";
        self.password = "";
        self.errors = {};
        self.disabled = false;
        var method = api.auth.login;

        self.submit = function () {
            self.errors = {};
            self.disabled = true;
            api.auth.login(self.login, self.password)
                .then(function () {
                    redirectToApp();
                })
                .catch(function (errorCode) {
                    errorCode && (self.errors[errorCode] = true);
                    self.disabled = false;
                })

            return false;
        };

        if (authStorage.isAuthenticated()) {
            redirectToApp();
        }

        function redirectToApp() {
            window.location = "/app";
        }
    }
})();