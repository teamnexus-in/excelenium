var actions = {
    "main": [
        "FILL", "CLEAR", "CLICK", "RIGHT_CLICK", "CHECK", "SELECT",
        "VERIFY_TEXT", "VERIFY_PRESENT", "IS_VISIBLE", "IS_HIDDEN",
        "CHECK_ATTRIBUTE", "ACCEPT_POPUP", "DISMISS_POPUP",
        "SWITCH_TO_IFRAME", "SWITCH_TO_PARENT", "CAPTURE_SCREEN",
        "NAVIGATE", "SET_VARIABLE", "UNSET_VARIABLE", "CLEAR_COOKIES",
        "DELETE_COOKIE", "ADD_COOKIE", "SWITCH_TO_WINDOW",
        "EXECUTE_JAVASCRIPT", "WAIT_MSECS", "IS_ENABLED",
        "IS_DISABLED", "SET_WINDOW_SIZE", "COMPARE_URL", "RUN_SCRIPT",
        "GET_DOM", "SCROLL_WINDOW_BY", "SCROLL_TO_ELEMENT",
        "MAKE_REQUEST", "HOVER", "DRAG_AND_DROP", "GET_CURRENT_URL",
        "HAS_CSS_CLASS", "CHECK_CSS_ATTRIBUTE"
    ],
    "preprocess": [
        "ADD_ATTRIBUTE", "REMOVE_ATTRIBUTE", "SET_ATTRIBUTE"
    ],
};


/// ================ SuiteModel =========================
class SuiteModel {
    constructor() {
        this.settings = {};
        this.scripts = [];
    }

    initialize() {
    }

    getJson() {
        let data = {};
        data.name = this.settings.name;
        data.settings = this.settings;
        data.scripts = this.scripts;
        return data;
    }

    loadJson(suiteContent) {
        this.settings = suiteContent.settings;
        this.scripts = suiteContent.scripts;
    }

    saveSettings(settings) {
        this.settings = settings;
    }

    saveSuite(sData) {
        this.scripts = [];
        sData.forEach(script => {
            let model = this.getScriptModel(script);
            this.scripts.push(model);
        });
        console.log(this.getJson());
    }

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

/// ================ SuiteController =========================
class SuiteController {
    constructor() {
    }

    initialize(suiteView, suiteModel) {
        this.suiteView = suiteView;
        this.suiteModel = suiteModel;
    }

    loadSuite(suiteContent) {
        console.log("Suite Model suiteContent")
        this.suiteModel.loadJson(suiteContent);
        this.suiteView.populateUISettingsValue(this.suiteModel.settings);
        this.suiteView.loadScripts(this.suiteModel.scripts);
        toastr.success("Script loaded!");
    }

    saveSettings(settings) {
        console.log("Saving settings");
        this.suiteModel.saveSettings(settings)
        console.log(this.suiteModel.settings);
        toastr.success('Settings saved!')
    }

    createNewScript(scriptName, run, stopOnError) {
        this.suiteView.hideNewScriptDialog();
        this.suiteView.createNewScriptTab(scriptName, run, stopOnError);
    }

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

    runSuite(suiteName) {
        console.log("Making get request")
        $.get("/run", "suiteName=" + suiteName, function (data) {
            console.log(data);
        });
    }

    getSuiteJson() {
        return this.suiteModel.getJson();
    }

    closeSuite() {
    }
}




/// ================ SuiteView =========================
class SuiteView {
    constructor() {
        this.data = { "scripts": [] };
        this.tabIdx = 0;
        this.scriptsJxl;
    }

    initialize(controller) {
        this.controller = controller;
        this.registerHandlers();
    }

    registerHandlers() {

        let thisViewObj = this;

        $('.cb-browser-settings').click(function () {
            var result = ($(this).prop('checked') == true);
            var cboxEl = $(this).attr("id");
            var idx = cboxEl.indexOf('-');
            var elName = cboxEl.substr(idx + 1);
            // console.log(cboxEl + " " + elName);
            var tbEl = '#tb-' + elName;
            $(tbEl).attr('disabled', !result);
            if (result) {
                $(tbEl).focus();
            }
            // $("#tb-chrome").attr('disabled', !result);
        });

        $('#btn-settings-save').click(function () {
            let settings = thisViewObj.getUISettingsValues();
            // console.log(settings);
            thisViewObj.controller.saveSettings(settings);
            thisViewObj.hideSettings();
        });

        // Create New Script
        $('#btn-new-script-ok').click(function () {
            var scriptName = $('#tb-script-name').val();
            let stopOnError = $('#cb-ns-stop-on-error').prop('checked');
            let run = $('#cb-ns-execute').prop('checked');
            thisViewObj.controller.createNewScript(scriptName, run, stopOnError);
        });

        $("#btn-suite-new").click(function () {
            // check if a script is already open and save it before creating a new one
            thisViewObj.showNewSuiteWarning();
            thisViewObj.displaySettings(true);
        });

        $('#btn-suite-load').click(function () {
            thisViewObj.showNewSuiteWarning();
            $('#file-suite').val('');
            $('#file-upload-modal').modal('show');
        });

        $('#btn-upload-file').click(function () {
            if ($('#file-suite').val() == '') {
                $('#file-error-msg').show();
            }
            else {
                $('#file-upload-modal').modal('hide');
                $('#form-file-upload').submit();
            }
        });

        $('#btn-suite-download').click(function () {
            console.log('Downloading...');
            thisViewObj.doSaveAction(false);
            thisViewObj.downloadSuite();
        });

        $('#btn-settings').click(function () {
            // load the settings into the fields and show
            thisViewObj.displaySettings(false);
        });

        $('#btn-new-script').click(function () {
            thisViewObj.clearNewScriptDialogFields();
            thisViewObj.showNewScriptDialog();
        });

        // Save suite
        $('#btn-save').click(function () {
            thisViewObj.doSaveAction(false);
        });

        $('#btn-run').click(function () {
            thisViewObj.doSaveAction(true);
        });

        $('.closeTab').click(function(){
            console.log(this.parent);
        });

    }

    downloadSuite() {
        console.log("About to download...");
        let suiteContent = this.controller.getSuiteJson();

        var filename = suiteContent.name.replace(' ', '-').trim().toLowerCase() + '.json';

        var blob = new Blob([JSON.stringify(suiteContent, null, 2)], {
            type: "application/json;charset=utf-8"
        });

        saveAs(blob, filename);
    }

    doSaveAction(isRun) {
        let settings = this.getUISettingsValues();
        this.controller.saveSettings(settings);
        let sData = this.getScriptsData();
        this.controller.saveSuite(sData, isRun);
    }

    getUISettingsValues() {
        let settings = {};
        settings.name = $('#tb-suite-name').val();
        settings.serverUrl = $('#tb-server-url').val();
        settings.browsers = [];
        let browsers = ['chrome', 'firefox', 'edge', 'opera', 'safari'];
        browsers.forEach(obj => {
            let data = this.getBrowserOptions(obj);
            settings.browsers.push(data);
        });
        settings.userAgent = this.getBrowserOptions('user-agent');
        settings.runConcurrent = $('#cb-run-concurrent').prop('checked');
        return settings;
    }

    populateUISettingsValue(settings) {
        $('#tb-suite-name').val(settings.name);
        $('#tb-server-url').val(settings.serverUrl);
        settings.browsers.forEach(obj => {
            $('#cb-' + obj.name).prop('checked', obj.enabled);
            if (obj.enabled && obj.name != 'safari') {
                $('#tb-' + obj.name).val(obj.driverPath);
                $('#tb-' + obj.name).attr('disabled', false);
            }
        });
        const obj = settings.userAgent;
        if (obj && obj != null) {
            $('#cb-user-agent').prop('checked', obj.enabled);
            $('#tb-user-agent').val(obj.value);
        }
        $('#cb-run-concurrent').prop('checked', settings.runConcurrent);
    }

    getBrowserOptions(browser) {
        let retVal = {};
        let enabled = $('#cb-' + browser).prop('checked');
        let textVal = ((browser == 'safari') ? "" : $('#tb-' + browser).val());
        retVal.enabled = enabled;
        if (browser == 'user-agent') {
            retVal.value = textVal;
        }
        else {
            retVal.name = browser;
            retVal.driverPath = textVal;
        }
        // console.log(retVal);
        return retVal;
    }

    getScriptsData() {
        // console.log("Getting scripts data");
        let retVal = [];
        let obj = this.data.scripts;
        this.data.scripts.forEach(obj => {
            let scriptData = {};
            scriptData.name = obj.name;
            scriptData.run = obj.run;
            scriptData.stopOnError = obj.stopOnError;
            let jxl = obj.jxl;
            scriptData.actions = this.sanitizeJson(jxl.getJson());
            retVal.push(scriptData);
        });

        return retVal;
    }

    sanitizeJson(jsonObj) {
        console.log('Sanitizing');
        let retVal = {};
        retVal = jsonObj.filter(function (value, index, array) {
            if (value[2] != '') {
                return value;
            }
        });
        return retVal;
    }

    showNewSuiteWarning() {
        console.log("Showing new suite warning");
    }

    showNewScriptDialog() {
        $("#newScriptModal").modal('show');
    }

    hideNewScriptDialog() {
        $("#newScriptModal").modal('hide');
    }

    addScriptsTabEntry(scriptName, run, stopOnError) {
        let thisViewObj = this;
        let delHtml = '<i class="fas fa-trash-alt"></i>';

        if (this.tabIdx == 1) {
            $('#no-scripts').hide();
            this.scriptsJxl = $('#scripts-list').jexcel({
                data: [[]],
                defaultColWidth: 100,
                tableOverflow: true,
                tableWidth: "100%",
                tableHeight: "100%",
                allowManualInsertRow: false,
                allowManualInsertRow: false,
                columns: [
                    { type: 'Name', title: 'Name', width: 250 },
                    { type: 'checkbox', title: 'Run', width: 50 },
                    { type: 'checkbox', title: 'Stop on Error', width: 100 },
                    { type: 'html', title: 'Delete', width: 100, readOnly: true },
                ],
                nestedHeaders: [
                    [
                        {
                            title: 'Scripts',
                            colspan: '4',
                        },
                    ],
                ],
                onselection : function(instance, x1, y1, x2, y2, origin) {
                    // delete icon clicked
                    if(x1==3 && x2 == 3){
                        console.log(instance);
                        let val = this.getCell('A'+y1).innerText;
                        alert("delete clicked for script: " + val);
                        let findObj = thisViewObj.data.scripts.find(function(obj, idx, arr){
                            if(obj.name == val){
                                console.log("found tab");
                                return obj;
                            }
                        }, val);
                    }

                    // script name clicked
                    if(x1==0 && x2==0){
                        console.log(instance);
                        let val = this.getCell('A'+y1).innerText;
                        alert("name clicked for script: " + val);
                        let findObj = thisViewObj.data.scripts.find(function(obj, idx, arr){
                            if(obj.name == val){
                                console.log("found tab");
                                return obj;
                            }
                        }, val);
                        console.log("open findObj", findObj, $('#'+findObj.tabId));
                    }
                },
            });
            this.scriptsJxl.setRowData(0, [scriptName, run, stopOnError]);
            this.scriptsJxl.setValueFromCoords(3, 0, delHtml, true);
        }
        else {
            this.scriptsJxl.insertRow([scriptName, run, stopOnError, delHtml]);
            this.scriptsJxl.setValueFromCoords(3, this.tabIdx-1, delHtml, true);
        }

    }

    createNewScriptTab(scriptName, run, stopOnError, data) {
        this.tabIdx++;
        let tabId = 'tab-scripts-' + this.tabIdx;
        let panelId = "tpanel-scripts-tab-" + this.tabIdx;
        let sheetsId = "sheets-" + this.tabIdx;

        // console.log(this.data);
        let scriptData = {}
        scriptData.name = scriptName;
        scriptData.tabIdx = this.tabIdx;
        scriptData.tabId = tabId;
        scriptData.panelId = panelId;
        scriptData.sheetsId = sheetsId;
        scriptData.run = run;
        scriptData.stopOnError = stopOnError;
        this.data.scripts.push(scriptData);
        console.log('View data:', this.data);

        $('#suite-tabs').append('<li class="nav-item"><a class="nav-link" id="' + tabId + '" data-toggle="tab" href="#' + panelId + '" role="tab" aria-controls="scripts" aria-selected="true"><button type="button" class="close closeTab"><span aria-hidden="true">&times;</span></button>' + scriptName + '</a></li>');
        $('#scripts-tab-content').append('<div class="tab-pane fade show" id="' + panelId + '" role="tabpanel" aria-labelledby="' + tabId + '"><div id="' + sheetsId + '" class="sheets-content"></div></div>');

        let jxl = this.createSheets(sheetsId, data);
        scriptData.jxl = jxl;
        this.addScriptsTabEntry(scriptName, run, stopOnError);
        $('#' + tabId).tab('show');
    }

    createSheets(sheetsId, data) {
        let jxl = $('#' + sheetsId).jexcel({
            data: [[]],
            defaultColWidth: 100,
            minDimensions: [10, 20],
            tableOverflow: true,
            tableWidth: "100%",
            tableHeight: "100%",
            columns: [
                { type: 'checkbox', title: 'Run', width: 50 },
                { type: 'checkbox', title: 'Stop on Error', width: 100 },
                { type: 'text', title: 'Name', width: 100 },
                { type: 'autocomplete', title: 'Type', width: 125, source: actions.main },
                { type: 'text', title: 'Element Id/XPath', width: 200 },
                { type: 'text', title: 'Element Val', width: 150 },
                { type: 'text', title: 'Attr Name', width: 100 },
                { type: 'text', title: 'Attr Val', width: 100 },
                { type: 'autocomplete', title: 'Action', width: 100, source: actions.preprocess },
                { type: 'text', title: 'Attr Name', width: 100 },
                { type: 'text', title: 'Attr Val', width: 100 },
                // { type: 'dropdown', title: 'Actions', width: 200, autocomplete:true, source: db.actions },
            ],
            nestedHeaders: [
                [
                    {
                        title: 'Actions',
                        colspan: '8',
                    },
                    {
                        title: 'Preprocess',
                        colspan: '3',
                    },
                ],
            ]
        });

        if (data) {
            let idx = 0;
            data.forEach(scriptData => {
                let sd = [
                    scriptData.execute,
                    scriptData.stopOnError,
                    scriptData.actionName,
                    scriptData.actionType,
                    scriptData.element,
                    scriptData.elementValue,
                    scriptData.attributeName,
                    scriptData.attributeValue
                ];
                if (scriptData.preprocess) {
                    sd.push(scriptData.preprocess.actionType);
                    sd.push(scriptData.preprocess.attributeName);
                    sd.push(scriptData.preprocess.attributeValue);
                }
                jxl.setRowData(idx, sd);
                idx++;
            });
        }
        // jxl.setRowData(0, [true, true, 'test1', 'FILL', 'id1'])
        // jxl.setRowData(1, [true, true, 'test2', 'CLEAR', 'id2'])
        // jxl.setRowData(2, [true, true, 'test3', 'CHECK_ATTRIBUTE', 'id3'])

        return jxl;
    }

    loadScripts(scripts) {
        scripts.forEach(script => {
            this.createNewScriptTab(script.name, script.execute, script.stopOnError, script.actions);
        });
    }

    displaySettings(isClearSettings) {
        if (isClearSettings) {
            this.clearSettingsFields();
        }
        $("#settingsModal").modal('show');
    }

    hideSettings() {
        $("#settingsModal").modal('hide');
    }

    clearSettingsFields() {
        // console.log("Clearing settings for new suite");
        $('.cb-browser-settings').each(function () {
            $(this).prop('checked', false);
        });
        $('.tb-browser-settings').each(function () {
            $(this).val('');
            $(this).attr('disabled', 'disabled')
        });
        $('#tb-suite-name').val('');
        $('#tb-server-url').val('');
        $('#cb-run-browsers-concurrent').prop('checked', false);
    }

    clearNewScriptDialogFields() {
        $("#tb-script-name").val('');
        $('cb-ns-stop-on-error').prop('checked', false);
        $('cb-ns-execute').prop('checked', false);
    }

}










