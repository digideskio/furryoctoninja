(function () {
    angular.module("surveyapp")
        .controller("MainController", main);

    main.$inject = [ "api" ];

    function main(api) {
        api.auth.refresh();
    }
})();