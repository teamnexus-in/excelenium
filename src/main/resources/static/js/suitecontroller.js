
/// ================ SuiteController =========================
/**
 * The Controller Class that interfaces between the model and the UI elements
 *
 * @class SuiteController
 */
class SuiteController {
    /**
     * Creates an instance of SuiteController.
     * @memberof SuiteController
     */
    constructor() {
    }

    /**
     * Initialize the SuiteController with the SuiteView and SuiteModel objects
     *
     * @param {SuiteView} suiteView - The SuiteView Object
     * @param {SuiteModel} suiteModel - The SuiteModel Object
     * @memberof SuiteController
     */
    initialize(suiteView, suiteModel) {
        this.suiteView = suiteView;
        this.suiteModel = suiteModel;
    }

    /**
     * Load the Suite content from a file that was uploaded the server and 
     * returned back as a json object in the html
     *
     * @param {JSON} suiteContent - suite content from file
     * @memberof SuiteController
     */
    loadSuite(suiteContent) {
        console.log("Suite Model suiteContent")
        this.suiteModel.loadJson(suiteContent);
        this.suiteView.populateUISettingsValue(this.suiteModel.settings);
        this.suiteView.loadScripts(this.suiteModel.scripts);
        toastr.success("Script loaded!");
    }

    /**
     * Save the Suite settings - serverURL, browser settings etc.
     *
     * @param {JSON} settings - settings json
     * @memberof SuiteController
     */
    saveSettings(settings) {
        console.log("Saving settings");
        this.suiteModel.saveSettings(settings)
        console.log(this.suiteModel.settings);
        toastr.success('Settings saved!')
    }

    /**
     * Creates a new script and also the UI elements
     *
     * @param {String} scriptName - name of the script
     * @param {boolean} run - run the script, if true
     * @param {boolean} stopOnError - stop the script on error, if true
     * @memberof SuiteController
     */
    createNewScript(scriptName, run, stopOnError) {
        this.suiteView.hideNewScriptDialog();
        this.suiteView.createNewScriptTab(scriptName, run, stopOnError);
    }


    /**
     * Save the Suite data including settings and isRun is true, execute the script
     *
     * @param {JSON} sData - Suite Data - script and settings JSON
     * @param {boolean} isRun - Run the script if true
     * @memberof SuiteController
     */
    saveSuite(sData, isRun) {
        let thisController = this;
        let suiteName = this.suiteModel.settings.name;
        console.log("Before Ajax: ", suiteName);
        this.suiteModel.saveSuite(sData);
        const modelData = this.suiteModel.getJson();
        console.log("Making Ajax Request", modelData);
        $.ajax({
            'type': 'POST',
            'url': '/save',
            'data': JSON.stringify(modelData),
            'success': function (data) {
                console.log("Server Response: ", data);
                if (isRun) {
                    console.log("running: " + suiteName)
                    thisController.runSuite(suiteName);
                }
            },
            'dataType': "json",
            'contentType': 'application/json; charset=UTF-8'
        });
        toastr.success('Suite Saved!')
        console.log("Ajax Request done");
    }

    /**
     * Make a call to the server to run the suite
     *
     * @param {String} suiteName - name of the suite
     * @memberof SuiteController
     */
    runSuite(suiteName) {
        console.log("Making get request")
        $.get("/run", "suiteName=" + suiteName, function (data) {
            console.log(data);
        });
    }

    /**
     * Get the JSON Model of the Suite
     *
     * @returns JSON mode of the suite
     * @memberof SuiteController
     */
    getSuiteJson() {
        return this.suiteModel.getJson();
    }


    // facade methods for shortcut keys binding
    /**
     * A facade method for shortcut key bindings <br/>
     * Saves the suite
     * @memberof SuiteController
     */
    save() {
        this.suiteView.doSaveAction(false);
    }

    /**
     * A facade method for shortcut key bindings <br/>
     * Creates a new script in the suite
     * @memberof SuiteController
     */
    newScript() {
        this.suiteView.clearNewScriptDialogFields();
        this.suiteView.showNewScriptDialog();
        $("#tb-script-name").focus();
    }

    /**
     * A facade method for shortcut key bindings <br/>
     * Creates a new suite, provides a warning that the current suite will be overwritten.
     * @memberof SuiteController
     */
    newSuite() {
        this.suiteView.showSuiteOverwriteWarning(true);
    }

    /**
     * A facade method for shortcut key bindings <br/>
     * Exports the current suite as a json file
     * @memberof SuiteController
     */
    export() {
        this.suiteView.doSaveAction(false);
        this.suiteView.downloadSuite();
    }

    /**
     * A facade method for shortcut key bindings <br/>
     * Runs the current suite
     * @memberof SuiteController
     */
    run() {
        this.suiteView.doSaveAction(true);
    }

    /**
     * A facade method for shortcut key bindings <br/>
     * Shows the file open dialog for choose the script to be loaded
     * @memberof SuiteController
     */
    load() {
        this.suiteView.showSuiteOverwriteWarning(true);
    }

    /**
     * A facade method for shortcut key bindings <br/>
     * Shows the settings dialog
     * @memberof SuiteController
     */
    settings() {
        // load the settings into the fields and show
        this.suiteView.displaySettings(false);
    }

    /**
     * Closes the current suite before creating a new suite or load another suite
     *
     * @memberof SuiteController
     */
    closeSuite() {
        const scripts = this.suiteModel.scripts;
        for (let i = 0; i < scripts.length; ++i) {
            this.suiteView.removeScriptAndTab(scripts[i].name, 0);
        }
        this.suiteModel.initialize();
    }
}
