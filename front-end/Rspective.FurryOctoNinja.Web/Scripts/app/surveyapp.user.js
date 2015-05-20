(function () {
    angular.module("surveyapp")
        .controller("UserController", user);

    user.$inject = ["dataRefresher", "api", "users"];

    function user(dataRefresher, api, users) {
        var self = this;

        self.edited = { Id: undefined, Name:undefined, Login: undefined, Password: undefined };
        self.created = { Id: undefined, Name:undefined, Login: undefined, Password: undefined };
        self.mode = -1;

        self.users = users;

        self.edit = function (user) {
            self.mode = user.Id;
            angular.extend(self.edited, user);
            self.edited.Password = "";
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