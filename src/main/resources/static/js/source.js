var suiteView;
var suiteController;
var suiteModel;

$(document).ready(function () {
    bsCustomFileInput.init()

    toastr.options.closeButton = true;
    toastr.options.positionClass = "toast-bottom-right";

    suiteController = new SuiteController();
    suiteModel = new SuiteModel();
    suiteView = new SuiteView(suiteController);

    suiteController.initialize(suiteView, suiteModel);
    suiteView.initialize(suiteController);

    if (isFileLoad) {
        let sContent = JSON.parse(suiteContent);
        console.log('Do Script Load', sContent);

        suiteController.loadSuite(sContent);
    }
    else {
        suiteView.displaySettings(true);
    }

    $('[data-toggle="tooltip"]').tooltip();

    // $(window).bind('beforeunload', function () {
    //     suiteController.save();
    //     return 'Are you sure you want to leave?';
    // });
    bindKeyboardShortcuts();
});

function bindKeyboardShortcuts() {

    Mousetrap.bind('mod+s', function (e) {
        handleDefaults(e);
        console.log("Mousetrap save...");
        suiteController.save();
    });

    Mousetrap.bind('mod+m', function (e) {
        console.log("Mousetrap new script...");
        handleDefaults(e);
        suiteController.newScript();
    });

    Mousetrap.bind('mod+y', function (e) {
        console.log("Mousetrap new suite...");
        handleDefaults(e);
        suiteController.newSuite();
    });

    Mousetrap.bind('mod+e', function (e) {
        console.log("Mousetrap export...");
        handleDefaults(e);
        suiteController.export();
    });

    Mousetrap.bind('mod+q', function (e) {
        console.log("Mousetrap run...");
        handleDefaults(e);
        suiteController.run();
    });

    Mousetrap.bind('mod+l', function (e) {
        console.log("Mousetrap load...");
        handleDefaults(e);
        suiteController.load();
    });

    Mousetrap.bind('mod+,', function (e) {
        handleDefaults(e);
        console.log("Mousetrap settings...");
        suiteController.settings();
    });

    function handleDefaults(e) {
        if (e.preventDefault) {
            e.preventDefault();
        }
        else {
            // internet explorer
            e.returnValue = false;
        }
    }
}

