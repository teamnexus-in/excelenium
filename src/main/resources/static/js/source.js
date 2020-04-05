var suiteView;
var suiteController;
var suiteModel;

$(document).ready(function () {

    suiteController = new SuiteController();
    suiteModel = new SuiteModel();
    suiteView = new SuiteView(suiteController);

    suiteController.initialize(suiteView, suiteModel);
    suiteView.initialize(suiteController);
});








