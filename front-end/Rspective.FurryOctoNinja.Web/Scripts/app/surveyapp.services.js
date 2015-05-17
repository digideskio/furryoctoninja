(function () {
    angular.module("surveyapp")
        .service("api", api)
        .service("loginManager", loginManager)

    api.$inject = [ "$http" ];

    function api($http) {

    }
})();