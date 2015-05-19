(function () {
    angular.module("surveyapp")
        .controller("UserController", user);

    user.$inject = ["api", "users"];

    function user(api, users) {
        var self = this;

        api.auth.refresh();

        self.json = JSON.stringify(users, null, 4);
        self.users = users;
    }
})();