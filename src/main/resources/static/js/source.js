var suiteView;
var suiteController;
var suiteModel;

$(document).ready(function () {
    bsCustomFileInput.init()
    suiteController = new SuiteController();
    suiteModel = new SuiteModel();
    suiteView = new SuiteView(suiteController);
    
    suiteController.initialize(suiteView, suiteModel);
    suiteView.initialize(suiteController);
    
    if(isFileLoad){
        let sContent = JSON.parse(suiteContent);
        console.log('Do Script Load', sContent);

        suiteController.loadSuite(sContent);
    }
    else{
        suiteView.displaySettings(true);
    }
    $('#form-new-suite').validate();
});








