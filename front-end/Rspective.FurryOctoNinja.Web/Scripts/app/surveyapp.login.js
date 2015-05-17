(function () {
    angular.module("surveyapp")
        .controller("LoginController", login);

    login.$inject = [ "api" ];

    function login(api) {
        var that = this;
        that.login = "";
        that.password = "";
        that.submit = function () {
        };
    }
})();