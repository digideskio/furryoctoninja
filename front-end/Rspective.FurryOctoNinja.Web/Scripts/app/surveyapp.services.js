(function () {
    angular.module("surveyapp")
        .service("api", api)
        .service("token", token)

    api.$inject = [ "$http" ];

    function api($http) {
    }

    token.$inject = ["$http"];

    function token($http) {
    }
})();