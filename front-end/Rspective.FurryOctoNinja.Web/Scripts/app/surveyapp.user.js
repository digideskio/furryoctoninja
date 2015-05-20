(function () {
    angular.module("surveyapp")
        .controller("UserController", user);

    user.$inject = ["dataRefresher", "api", "users"];

    function user(dataRefresher, api, users) {
        var self = this;

        self.edited = -1;
        self.users = users;

        self.edit = function (user) {
            self.edited = user.Id;
        };

        self.create = function () {
            return false;
        };

        self.update = function () {
            return false;
        };

        dataRefresher.addTemporary(function () {
            api.user.all()
                .then(function (users) {
                    self.users = users;
                });
        }, 45);
    }
})();