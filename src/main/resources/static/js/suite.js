var actions = {
    "main": ["FILL", "CLEAR", "CLICK", "RIGHT_CLICK", "CHECK", "SELECT", "VERIFY_TEXT", "VERIFY_PRESENT", "IS_VISIBLE", "CHECK_ATTRIBUTE", "ACCEPT_POPUP", "DISMISS_POPUP", "SWITCH_TO_IFRAME", "SWITCH_TO_PARENT", "CAPTURE_SCREEN", "NAVIGATE", "SET_VARIABLE", "UNSET_VARIABLE"
    ],
    "preprocess": ["ADD_ATTRIBUTE", "REMOVE_ATTRIBUTE", "SET_ATTRIBUTE"],
};


/// ================ SuiteModel =========================
class SuiteModel {
    constructor() {
        this.settings = {};
        this.data = {};
    }

    getJson() {
    }

    addNewScript(scriptName, run, stopOnError) {
    }

    saveSettings(settings) {
        this.settings = settings;
    }

    saveSuite(sData) {
        this.data = sData;
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

    saveSettings(settings) {
        console.log("Saving settings");
        this.suiteModel.saveSettings(settings)
        console.log(this.suiteModel.settings);
    }

    createNewScript(scriptName, stopOnError, run) {
        this.suiteModel.addNewScript(scriptName, run, stopOnError);
        this.suiteView.hideNewScriptDialog();
        this.suiteView.createNewScriptTab(scriptName, run, stopOnError);
    }

    saveSuite(sData) {
        this.suiteModel.saveSuite(sData);
    }

    closeSuite() {
    }
}


/// ================ SuiteView =========================
class SuiteView {
    constructor() {
        this.data = { "scripts": {} };
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
            console.log(cboxEl + " " + elName);
            var tbEl = '#tb-' + elName;
            $(tbEl).attr('disabled', !result);
            // $("#tb-chrome").attr('disabled', !result);
        });

        $('#btn-settings-save').click(function () {
            let settings = {};
            settings.name = $('#tb-suite-name').val();
            settings.serverUrl = $('#tb-server-url').val();
            settings.browsers = {};
            settings.browsers.chrome = thisViewObj.getBrowserOptions('chrome');
            settings.browsers.firefox = thisViewObj.getBrowserOptions('firefox');
            settings.browsers.edge = thisViewObj.getBrowserOptions('edge');
            settings.browsers.opera = thisViewObj.getBrowserOptions('opera');
            settings.browsers.safari = thisViewObj.getBrowserOptions('safari');
            settings.userAgent = thisViewObj.getBrowserOptions('user-agent');
            settings.runConcurrent = $('#cb-run-concurrent').prop('checked');

            // console.log(settings);

            thisViewObj.controller.saveSettings(settings);
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

        $('#btn-settings').click(function () {
            // load the settings into the fields and show
            thisViewObj.displaySettings(false);
        });

        $('#btn-new-script').click(function () {
            thisViewObj.clearNewScriptDialogFields();
            thisViewObj.showNewScriptDialog();
        });

        $('#btn-save').click(function () {
            console.log('Save clicked');
            let sData = thisViewObj.getScriptsData();
            thisViewObj.controller.saveSuite(sData);
        });
    }

    getBrowserOptions(browser) {
        let check = $('#cb-' + browser).prop('checked');
        let driverPath = ((browser == 'safari') ? "" : $('#tb-' + browser).val());
        return [check, driverPath];
    }

    getScriptsData() {
        console.log("Getting scripts data");
        let retVal = {};
        let obj = this.data.scripts;
        for (var key in obj) {
            let jxl = obj[key].jxl;
            retVal[key] = this.sanitizeJson(jxl.getJson());
        }
        console.log(retVal);
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
        if (this.tabIdx == 1) {
            $('#no-scripts').hide();
            this.scriptsJxl = $('#scripts-list').jexcel({
                data: [[]],
                defaultColWidth: 100,
                tableOverflow: true,
                tableWidth: "100%",
                tableHeight: "100%",
                columns: [
                    { type: 'Name', title: 'Name', width: 250 },
                    { type: 'checkbox', title: 'Run', width: 50 },
                    { type: 'checkbox', title: 'Stop on Error', width: 100 },
                ],
                nestedHeaders: [
                    [
                        {
                            title: 'Scripts',
                            colspan: '3',
                        },
                    ],
                ]
            });

            this.scriptsJxl.setRowData(0, [scriptName, run, stopOnError]);

        }
        else {
            this.scriptsJxl.insertRow([scriptName, run, stopOnError]);
        }

    }

    createNewScriptTab(scriptName, run, stopOnError) {
        this.tabIdx++;
        let tabId = 'tab-scripts-' + this.tabIdx;
        let panelId = "tpanel-scripts-tab-" + this.tabIdx;
        let sheetsId = "sheets-" + this.tabIdx;
        if (this.data.scripts[scriptName] == undefined) {
            this.data.scripts[scriptName] = {};
        }
        // console.log(this.data);
        let scriptData = this.data.scripts[scriptName];
        scriptData.tabIdx = this.tabIdx;
        scriptData.tabId = tabId;
        scriptData.panelId = panelId;
        scriptData.sheetsId = sheetsId;

        $('#suite-tabs').append('<li class="nav-item"><a class="nav-link" id="' + tabId + '" data-toggle="tab" href="#' + panelId + '" role="tab" aria-controls="scripts" aria-selected="true">' + scriptName + '</a></li>');
        $('#scripts-tab-content').append('<div class="tab-pane fade show" id="' + panelId + '" role="tabpanel" aria-labelledby="' + tabId + '"><div id="' + sheetsId + '" class="sheets-content"></div></div>');

        let jxl = this.createSheets(sheetsId);
        scriptData.jxl = jxl;
        this.addScriptsTabEntry(scriptName, run, stopOnError);
        $('#' + tabId).tab('show');
    }

    createSheets(sheetsId) {
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
                { type: 'text', title: 'Name', width: 150 },
                { type: 'autocomplete', title: 'Type', width: 150, source: actions.main },
                { type: 'text', title: 'Element Id/XPath', width: 250 },
                { type: 'text', title: 'Element Val', width: 150 },
                { type: 'text', title: 'Attr Name', width: 100 },
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
        jxl.setRowData(0, [true, true, 'test1', 'FILL', 'id1'])
        jxl.setRowData(1, [true, true, 'test2', 'CLEAR', 'id2'])
        jxl.setRowData(2, [true, true, 'test3', 'CHECK_ATTRIBUTE', 'id3'])

        return jxl;
    }

    displaySettings(isClearSettings) {
        if (isClearSettings) {
            this.clearSettingsFields();
        }
        $("#settingsModal").modal('show');
    }

    clearSettingsFields() {
        console.log("Clearing settings for new suite");
        $('.cb-browser-settings').each(function () {
            $(this).prop('checked', false);
        });
        $('.tb-browser-settings').each(function () {
            $(this).val('');
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










