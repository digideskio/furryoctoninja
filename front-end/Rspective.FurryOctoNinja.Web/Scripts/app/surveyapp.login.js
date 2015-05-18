(function () {
    angular.module("surveyapp")
        .controller("LoginController", login);

    login.$inject = ["api"];

    function login(api) {
        var that = this;
        that.login = "";
        that.password = "";
        that.errors = {};
        that.disabled = false;
        var method = api.auth.login;

        that.submit = function () {
            that.errors = {};
            that.disabled = true;
            api.auth.login(that.login, that.password)
                .then(function () {
                    window.location = "/Home/Index";
                })
                .catch(function (errorCode) {
                    errorCode && (that.errors[errorCode] = true);
                    that.disabled = false;
                })

            return false;
        };
    }
})();