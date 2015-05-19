(function () {
    angular.module("surveyapp")
        .controller("UserController", user);

    user.$inject = ["dataRefresher", "api", "users"];

    function user(dataRefresher, api, users) {
        var self = this;

        self.json = JSON.stringify(users, null, 4);
        self.users = users;

        dataRefresher.addTemporary(function () {
            api.user.all()
                .then(function (users) {
                    self.json = JSON.stringify(users, null, 4);
                    self.users = users;
                });
        }, 45);
    }
})();