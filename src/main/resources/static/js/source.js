var suiteView;
var suiteController;
var suiteModel;

$(document).ready(function () {
    bsCustomFileInput.init()
    if(isFileLoad){
        console.log('Do Script Load', suiteContent);

    }
    suiteController = new SuiteController();
    suiteModel = new SuiteModel();
    scriptModel = new ScriptModel();
    suiteView = new SuiteView(suiteController);

    suiteModel.initialize(scriptModel);
    suiteController.initialize(suiteView, suiteModel);
    suiteView.initialize(suiteController);

    var url = new URL(window.location.href);
    var qp = url.searchParams.get('type');

    if(qp == 'new'){
        suiteView.displaySettings(true);
    }
});








