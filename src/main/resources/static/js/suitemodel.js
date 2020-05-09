
/// ================ SuiteModel =========================
/**
 * Represents the Json Model of the Suite
 *
 * @class SuiteModel
 */
class SuiteModel {

    /**
     * Creates an instance of SuiteModel.
     * @memberof SuiteModel
     */
    constructor() {
        this.settings = {};
        this.scripts = [];
    }

    /**
     * Initialize the constructs settings and scripts
     *
     * @memberof SuiteModel
     */
    initialize() {
        this.settings = {};
        this.scripts = [];
    }

    /**
     * Returns the JSON model of the suite
     *
     * @returns JSON Object that represents the Suite
     * @memberof SuiteModel
     */
    getJson() {
        let data = {};
        data.name = this.settings.name;
        data.settings = this.settings;
        data.scripts = this.scripts;
        return data;
    }

    /**
     * Loads the suite Json Model from the file loaded
     *
     * @param {JSON} suiteContent - suite content loaded from file
     * @memberof SuiteModel
     */
    loadJson(suiteContent) {
        this.settings = suiteContent.settings;
        this.scripts = suiteContent.scripts;
    }

    /**
     * Saves the settings
     *
     * @param {JSON} settings - settings of the suite: serverURL, browser settings etc.
     * @memberof SuiteModel
     */
    saveSettings(settings) {
        this.settings = settings;
    }

    /**
     * Saves the suite JSON to the SuiteModel class
     *
     * @param {JSON} sData - Save the scripts to the SuiteModel
     * @memberof SuiteModel
     */
    saveSuite(sData) {
        this.scripts = [];
        sData.forEach(script => {
            let model = this.getScriptModel(script);
            this.scripts.push(model);
        });
        console.log(this.getJson());
    }

    /**
     * Constructs the SuiteModel from the SuiteView object's script data
     *
     * @param {JSON} scriptsData - SuiteView  internal script data
     * @returns JSON object in the SuiteModel Structure
     * @memberof SuiteModel
     */
    getScriptModel(scriptsData) {
        console.log("script model", scriptsData);
        let retVal = {}
        retVal.name = scriptsData.name;
        retVal.run = scriptsData.run;
        retVal.stopOnError = scriptsData.stopOnError;
        retVal.actions = [];
        scriptsData.actions.forEach(obj => {
            let actionsObj = {};
            actionsObj.execute = obj[0];
            actionsObj.stopOnError = obj[1];
            actionsObj.actionName = obj[2];
            actionsObj.actionType = obj[3];
            actionsObj.element = obj[4];
            actionsObj.elementValue = obj[5];
            actionsObj.attributeName = obj[6];
            actionsObj.attributeValue = obj[7];
            if (obj[8] != undefined && obj[8] != '') {
                actionsObj.preprocess = {}
                actionsObj.preprocess.actionType = obj[8];
                actionsObj.preprocess.attributeName = obj[9];
                actionsObj.preprocess.attributeValue = obj[10];
            }
            retVal.actions.push(actionsObj);
        });

        return retVal;
    }

}
