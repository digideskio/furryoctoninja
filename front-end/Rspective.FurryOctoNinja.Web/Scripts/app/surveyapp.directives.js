(function () {
    "use strict";
    angular.module('surveyapp.directives', [])
    .directive('chart', chart);

    chart.$inject = [];


    function chart() {
        return {
            restrict: 'EAC',
            replace: true,
            scope: {
                results: '=results'
            },
            template: "<canvas id='results' width='400' height='400'></canvas>",
            link: function (scope, element, attribute) {
                var getChartData = function () {
                    var s = scope.results;
                    var question = s.results.Questions[s.displayedQuestion];

                    var data = {
                        labels: [],
                        datasets: [{
                            label: question.Text,
                            fillColor: "rgba(151,187,205,0.5)",
                            strokeColor: "rgba(151,187,205,0.8)",
                            highlightFill: "rgba(151,187,205,0.75)",
                            highlightStroke: "rgba(151,187,205,1)",
                            data: []
                        }]
                    };

                    var answers = [];
                    question.Answers.forEach(function (answer) {
                        data.labels.push(answer.Text);
                        data.datasets[0].data.push(answer.Count);
                    });

                    return data;
                }

                var ctx = element[0].getContext("2d");
                var chart = new Chart(ctx).Bar(getChartData(), {});
                scope.$watch("results.displayedQuestion", function (newValue, oldValue) {
                    if (newValue !== oldValue) {
                        chart.destroy();
                        chart = new Chart(ctx).Bar(getChartData(), {});
                    }
                });
            }
        }
    }
})();