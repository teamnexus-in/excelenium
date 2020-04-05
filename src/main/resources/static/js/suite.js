var actions = {
    "main": ["FILL", "CLEAR", "CLICK", "RIGHT_CLICK", "CHECK", "SELECT", "VERIFY_TEXT", "VERIFY_PRESENT", "IS_VISIBLE", "CHECK_ATTRIBUTE", "ACCEPT_POPUP", "DISMISS_POPUP", "SWITCH_TO_IFRAME", "SWITCH_TO_PARENT", "CAPTURE_SCREEN", "NAVIGATE", "SET_VARIABLE", "UNSET_VARIABLE"
    ],
    "preprocess": ["ADD_ATTRIBUTE", "REMOVE_ATTRIBUTE", "SET_ATTRIBUTE"],
};


/// ================ SuiteModel =========================
class SuiteModel {
    constructor() {
    }
    getJson() {
    }
    addNewScript(scriptName) {
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

    saveSettings() {
        console.log("Saving settings");
    }

    createNewScript(scriptName) {
        this.suiteModel.addNewScript(scriptName);
        this.suiteView.hideNewScriptDialog();
        this.suiteView.createNewScriptTab(scriptName);
    }

    closeSuite() {
    }
}


/// ================ SuiteView =========================
class SuiteView {
    constructor() {
        this.data = { "scripts": {} };
        this.tabIdx = 0;
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
            thisViewObj.controller.saveSettings();
        });
        // Create New Script
        $('#btn-new-script-ok').click(function () {
            var scriptName = $('#tb-script-name').val();
            console.log(thisViewObj);
            thisViewObj.controller.createNewScript(scriptName);
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

    createNewScriptTab(scriptName) {
        this.tabIdx++;
        let tabId = 'tab-scripts-' + this.tabIdx;
        let panelId = "tpanel-scripts-tab-" + this.tabIdx;
        let sheetsId = "sheets-" + this.tabIdx;
        if (this.data.scripts[scriptName] == undefined) {
            this.data.scripts[scriptName] = {};
        }
        console.log(this.data);
        let scriptData = this.data.scripts[scriptName];
        scriptData.tabIdx = this.tabIdx;
        scriptData.tabId = tabId;
        scriptData.panelId = panelId;
        scriptData.sheetsId = sheetsId;

        $('#suite-tabs').append('<li class="nav-item"><a class="nav-link" id="' + tabId + '" data-toggle="tab" href="#' + panelId + '" role="tab" aria-controls="scripts" aria-selected="true">' + scriptName + '</a></li>');
        $('#scripts-tab-content').append('<div class="tab-pane fade show" id="' + panelId + '" role="tabpanel" aria-labelledby="' + tabId + '"><div id="' + sheetsId + '" class="sheets-content"></div></div>');

        let jxl = this.createSheets(sheetsId);
        scriptData.jxl = jxl;
        this.addScriptsTabEntry(scriptName);
        $('#'+tabId).tab('show');
    }

    createSheets(sheetsId) {
        let jxl = $('#' + sheetsId).jexcel({
            data: [[]],
            defaultColWidth: 100,
            tableOverflow: true,
            tableWidth: "100%",
            tableHeight: "100%",
            columns: [
                { type: 'checkbox', title: 'Run', width: 50 },
                { type: 'checkbox', title: 'Stop on Error', width: 100 },
                { type: 'text', title: 'Name', width: 150 },
                { type: 'autocomplete', title: 'Type', width: 150, source: actions.main },
                { type: 'text', title: 'Id/XPath', width: 250 },
                { type: 'text', title: 'Element Val', width: 150 },
                { type: 'text', title: 'Attr Name', width: 150 },
                { type: 'text', title: 'Attr Val', width: 100 },
                { type: 'autocomplete', title: 'Action', width: 200, source: actions.preprocess },
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
                        title: 'PreProcess',
                        colspan: '3',
                    },
                ],
            ]
        });
        jxl.insertRow(19);

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
            console.log("Before: " + $(this).is(':checked'));
            $(this).prop('checked', false);
            console.log("After: " + $(this).is(':checked'));
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










