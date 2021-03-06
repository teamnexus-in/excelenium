// jsdoc --readme ./README.md suite.js -t ./node_modules/minami

/**
 * the various actions to be performed by the Selenium WebDriver
 */
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
        "HAS_CSS_CLASS", "CHECK_CSS_ATTRIBUTE", "MAXIMIZE_WINDOW"
    ],
    "preprocess": [
        "ADD_ATTRIBUTE", "REMOVE_ATTRIBUTE", "SET_ATTRIBUTE"
    ],
};

/// ================ SuiteView =========================
/**
 * The SuiteView class that takes care of  managing the UI elements and 
 * also getting/settings the values to UI elements
 * binding listeners etc.
 *
 * @class SuiteView
 */
class SuiteView {
    /**
     * Creates an instance of SuiteView.
     * @memberof SuiteView
     */
    constructor() {
        this.data = { "scripts": [] };
        this.tabIdx = 0;
        this.scriptsJxl;
    }

    /**
     * Initializes the view with the controller object and registers the binding for the UI elements
     *
     * @param {SuiteController} controller - the SuiteController object
     * @memberof SuiteView
     */
    initialize(controller) {
        this.controller = controller;
        this.registerHandlers();
    }

    /**
     * Registers listeners to the events on the UI elements - dialogs, buttons etc.
     *
     * @memberof SuiteView
     */
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
            $(tbEl).attr('required', result);
            if (result) {
                $(tbEl).focus();
            }
            else {
                $(tbEl).removeClass('error');
                let errMsgDiv = '#errormsg-' + elName;
                console.log(errMsgDiv);
                $(errMsgDiv).empty();
            }
            // $("#tb-chrome").attr('disabled', !result);
        });

        $('#tb-suite-name').blur(function () {
            let el = $('#tb-suite-name')
            let val = el.val();
            if (val !== '') {
                el.removeClass('error');
                $('#errormsg-suite-name').empty();
            }
        });

        $('#tb-server-url').blur(function () {
            let el = $('#tb-server-url');
            let val = el.val();
            if (val.startsWith('http://') || val.startsWith('https://')) {
                el.removeClass('error');
                $('#errormsg-server-url').empty();
            }
        });

        $('#tb-script-name').blur(function () {
            let el = $('#tb-script-name');
            let val = el.val();
            if (val !== '') {
                el.removeClass('error');
                $('#errormsg-script-name').empty();
            }
        });

        $('#btn-settings-save').click(function () {
            if (!thisViewObj.bouncerSuite) {
                thisViewObj.bouncerSuite = Bouncer('form-new-suite', {
                    messageAfterField: true
                });
            }
            let form = document.getElementById('form-new-suite');
            let valid = thisViewObj.bouncerSuite.validateAll(form);
            console.log('Valid: ', valid);
            if (valid.length <= 0) {
                let settings = thisViewObj.getUISettingsValues();
                // console.log(settings);
                thisViewObj.controller.saveSettings(settings);
                thisViewObj.hideSettings();
                sessionStorage.removeItem('savedSuite');
            }
        });

        // Create New Script
        $('#btn-new-script-ok').click(function () {
            if (!thisViewObj.bouncerScript) {
                thisViewObj.bouncerScript = Bouncer('form-new-script', {
                    messageAfterField: true
                });
            }
            let form = document.getElementById('form-new-script');
            let valid = thisViewObj.bouncerScript.validateAll(form);
            console.log('Valid: ', valid);
            if (valid.length <= 0) {
                var scriptName = $('#tb-script-name').val();
                let stopOnError = $('#cb-ns-stop-on-error').prop('checked');
                let run = $('#cb-ns-execute').prop('checked');
                let isScriptExists = thisViewObj.checkIfExists(scriptName);
                if (isScriptExists) {
                    bootbox.alert({
                        size: "small",
                        title: "Script Exists",
                        message: "A script with the same name already exists, please choose a different name.",
                        buttons: {
                            ok: {
                                label: "Ok",
                                className: "btn-danger"
                            }
                        }

                    });
                }
                else {
                    thisViewObj.controller.createNewScript(scriptName, run, stopOnError);
                }
            }
        });

        $("#btn-suite-new").click(function () {
            // check if a script is already open and save it before creating a new one
            thisViewObj.showSuiteOverwriteWarning(false);
        });

        $('#btn-suite-load').click(function () {
            thisViewObj.showSuiteOverwriteWarning(true);
        });

        $('#btn-upload-file').click(function () {
            if ($('#file-suite').val() == '') {
                bootbox.alert({
                    size: "small",
                    title: "Choose File",
                    message: "Please choose a suite file to upload",
                });
            }
            else {
                $('#file-upload-modal').modal('hide');
                $('#form-file-upload').submit();
                sessionStorage.removeItem('savedSuite');
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

        $('.nav-tabs').on('click', '.tabClose', function () {
            console.log(this, $(this).parent, $(this).siblings());
            let tabId = $(this).siblings()[0].attributes['id'].value;
            console.log('tabId: ', tabId);
            $('#scripts-tabs ul li').children('a').first().click();
            $('#' + tabId).hide();
        });

        $("#newScriptModal").on('shown.bs.modal', function (e) {
            $("#tb-script-name").focus();
        });

        $("#settingsModal").on('shown.bs.modal', function (e) {
            $("#tb-suite-name").focus();
        });

    }


    /**
     * Checks if the script with the given name exists
     *
     * @param {String} scriptName
     * @returns true if the script with the given name exists, false otherwise
     * @memberof SuiteView
     */
    checkIfExists(scriptName) {
        let result = false;
        const scripts = this.data.scripts;
        for (let i = 0; i < scripts.length; ++i) {
            if (scripts[i].name.toLowerCase() === scriptName.toLowerCase()) {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * The functions that allows the user to save the suite as a json file
     *
     * @memberof SuiteView
     */
    downloadSuite() {
        console.log("About to download...");
        let suiteContent = this.controller.getSuiteJson();

        var filename = suiteContent.name.replaceAll(' ', '-').trim().toLowerCase() + '.json';

        var blob = new Blob([JSON.stringify(suiteContent, null, 2)], {
            type: "application/json;charset=utf-8"
        });

        saveAs(blob, filename);
    }

    /**
     * Save the suite by getting values from the UI elements
     *
     * @param {boolean} isRun - Run the script after saving, if isRun is true
     * @memberof SuiteView
     */
    doSaveAction(isRun) {
        let settings = this.getUISettingsValues();
        let validSettings = this.validateSettings(settings);
        if (validSettings) {
            this.controller.saveSettings(settings);
            let sData = this.getScriptsData();
            if (sData && (sData.length > 0)) {
                this.controller.saveSuite(sData, isRun);
            }
            else {
                bootbox.alert({
                    title: "Empty Script",
                    message: "There are no scripts to save/run. Please create a script.",
                });
            }
        }
    }

    /**
     * Validate the settings - serverURL, browser webdriver path etc.
     *
     * @param {JSON} settings - settings values
     * @returns true is settings are valid, false otherwise
     * @memberof SuiteView
     */
    validateSettings(settings) {
        let errorMsg = '';
        let hasError = false;
        console.log(settings);
        if (!settings.name || settings.name.trim() === '') {
            errorMsg = errorMsg + '- Suite name cannot be empty</br>';
            hasError = true;
        }
        if (!settings.serverUrl || settings.serverUrl.trim() === '') {
            errorMsg = errorMsg + '- Server Url cannot be empty<br/>';
            hasError = true;
        }
        let browserEnabled = false;
        settings.browsers.forEach(obj => {
            if (obj.enabled) {
                browserEnabled = true;
            }
        });

        if (!browserEnabled) {
            hasError = true;
            errorMsg = errorMsg + '- Please choose at least on browser and provide the appropriate driver path</br>';
        }
        console.log('Error state:', hasError, errorMsg);

        if (hasError) {
            errorMsg = 'Please correct the following errors in the Settings<br/>' + errorMsg;
            bootbox.alert({
                title: "Settings Error",
                message: errorMsg,
            })
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Get the settings values from the UI elements
     *
     * @returns a JSON of settings values
     * @memberof SuiteView
     */
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

    /**
     * Populate the values to the UI elements from the loaded suite file
     *
     * @param {JSON} settings - settings values loaded from file
     * @memberof SuiteView
     */
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
        if (obj && (obj != null)) {
            $('#cb-user-agent').prop('checked', obj.enabled);
            $('#tb-user-agent').val(obj.value);
        }
        $('#cb-run-concurrent').prop('checked', settings.runConcurrent);
    }

    /**
     * Get browser settings from the UI elements - Settings Dialog
     *
     * @param {String} browser - browser name one of [firefox, chrome, edge, opera, safari ]
     * @returns JSON object containing values for a browser - if selected, driverPath
     * @memberof SuiteView
     */
    getBrowserOptions(browser) {
        let retVal = {};
        let enabled = $('#cb-' + browser).prop('checked');
        let textVal = ((browser === 'safari') ? "" : $('#tb-' + browser).val());
        retVal.enabled = enabled;
        if (browser === 'user-agent') {
            retVal.userAgent = textVal;
        }
        else {
            retVal.name = browser;
            retVal.driverPath = textVal;
        }
        // console.log(retVal);
        return retVal;
    }

    /**
     * Get the script data from the spreadsheet
     *
     * @returns JSON containing the scripts in the suite
     * @memberof SuiteView
     */
    getScriptsData() {
        // console.log("Getting scripts data");
        let retVal = [];
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

    /**
     * Removes the empty rows in the script returned by the JExcel sheet.
     *
     * @param {JSON} jsonObj - Spreadsheet Json
     * @returns JSON that has been sanitized by removing the empty rows
     * @memberof SuiteView
     */
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

    /**
     * Shows a warning dialog that the suite will be overwritten.
     * Shown when a new suite is created or another suite is loaded.
     *
     * @param {boolean} isLoad - True when the suite is loaded from a file, false if it is a new suite
     * @memberof SuiteView
     */
    showSuiteOverwriteWarning(isLoad) {
        let thisViewObj = this;
        console.log("Showing new suite warning");
        bootbox.confirm({
            size: "medium",
            buttons: {
                confirm: {
                    label: 'Ok',
                },
                cancel: {
                    label: 'Cancel',
                }
            },
            title: 'Suite Alert!',
            message: 'This action will overwrite your current suite. If you have not saved the current suite, please click "Cancel", export the script and try again. ',
            callback: function (result) {
                if (result) {
                    thisViewObj.controller.closeSuite();
                    if (isLoad) {
                        console.log('Load the suite');
                        $('#file-suite').val('');
                        $('#file-upload-modal').modal('show');
                    }
                    else {
                        console.log('New suite');
                        thisViewObj.displaySettings(true);
                    }
                }
            }
        })
    }

    /**
     * Shows the new script dialog
     *
     * @memberof SuiteView
     */
    showNewScriptDialog() {
        $("#newScriptModal").modal('show');
    }

    /**
     * Hides the new script dialog
     *
     * @memberof SuiteView
     */
    hideNewScriptDialog() {
        $("#newScriptModal").modal('hide');
    }

    /**
     * Adds the newly created script to the scripts sheet
     *
     * @param {String} scriptName - name of the script
     * @param {boolean} run - Run the script if true
     * @param {boolean} stopOnError - Stop the script if any error occur in the steps
     * @memberof SuiteView
     */
    addScriptsTabEntry(scriptName, run, stopOnError) {
        let thisViewObj = this;
        let delHtml = '<i class="fas fa-trash-alt"></i>';
        if (this.tabIdx === 1) {
            $('#no-scripts').hide();
            this.scriptsJxl = $('#scripts-list').jexcel({
                data: [[]],
                defaultColWidth: 100,
                tableOverflow: true,
                tableWidth: "100%",
                tableHeight: "100%",
                allowManualInsertRow: false,
                allowManualInsertColumn: false,
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
                onselection: function (instance, x1, y1, x2, y2, origin) {
                    thisViewObj.handleCellSelection(thisViewObj, this, x1, y1, x2, y2);
                },
                onchange: function (instance, cell, x, y, value) {
                    thisViewObj.handleCellChange(this, cell, x, y, value);
                }
            });
            this.scriptsJxl.setRowData(0, [scriptName, run, stopOnError]);
            this.scriptsJxl.setValueFromCoords(3, 0, delHtml, true);
        }
        else {
            this.scriptsJxl.insertRow([scriptName, run, stopOnError]);
            this.scriptsJxl.setValueFromCoords(3, this.tabIdx - 1, delHtml, true);
        }

    }

    /**
     *
     *
     * @param {JExcel} jxl - JExcel Object
     * @param {HTMLElement} cell
     * @param {integer} x - Column of the cell that is modified
     * @param {integer} y - Row of the cell that is modified
     * @param {*} value - Changed Value
     * @memberof SuiteView
     */
    handleCellChange(jxl, cell, x, y, value) {
        //console.log("Changed", x, y, typeof (x));
        // Strange behaviour initially x, y are of 
        // type number and when editing they become strings
        if (typeof (x) === 'string') {
            x = parseInt(x);
        }
        if (typeof (y) === 'string') {
            y = parseInt(y);
        }
        if (x === 0) {
            // console.log("Name changes", value);
            this.data.scripts[y].name = value;
            let tabId = '#' + this.data.scripts[y].tabId;
            $(tabId).text(value);
        }
        else if (x === 1) {
            // console.log("Run changes", value);
            this.data.scripts[y].run = value;
        }
        else if (x === 2) {
            // console.log("Stop On Error changes", value);
            this.data.scripts[y].stopOnError = value;
        }
    }

    /**
     * Handles the click event in the sheet for deleting the script
     *
     * @param {SuiteView} thisViewObj - The reference to this object to be used in a callback
     * @param {JExcel} jxl - JExcel object
     * @param {integer} x1 - Starting cell column in the sheet where change started
     * @param {integer} y1 - Starting cell row in the sheet where change started
     * @param {integer} x2 - Ending cell column in the sheet where change ended
     * @param {integer} y2 - Ending cell row in the sheet where change ended
     * @memberof SuiteView
     */
    handleCellSelection(thisViewObj, jxl, x1, y1, x2, y2) {
        // console.log('handling selection: ', thisViewObj, jxl, x1, y1, x2, y2);
        // delete icon clicked
        if (x1 == 3 && x2 == 3) {
            console.log('true showwing alert');
            let scriptName = jxl.getRowData(y1)[0];
            bootbox.confirm({
                size: "small",
                buttons: {
                    confirm: {
                        label: 'Yes',
                    },
                    cancel: {
                        label: 'No',
                    }
                },
                title: 'Delete Script?',
                message: "Action cannot be undone. Are you sure?",
                callback: function (result) {
                    if (result) {
                        thisViewObj.removeScriptAndTab(scriptName, y1);
                    }
                }
            })
        }

        // script name clicked
        if (x1 === 0 && x2 === 0) {
            let scriptName = jxl.getRowData(y1)[0];
            thisViewObj.displayTab(scriptName, false);
        }
    }

    /**
     * Reopen and display the script tab if closed already.
     *
     * @param {String} tabName - Script name which is displayed as the tab name
     * @param {boolean} isHide - true if tab is to be hidden, false if to be shown
     * @memberof SuiteView
     */
    displayTab(tabName, isHide) {
        let findObj = this.data.scripts.find(function (obj, idx, arr) {
            if (obj.name === tabName) {
                return obj;
            }
        }, tabName);
        if (isHide) {
            $('#' + findObj.tabId).hide();
        }
        else {
            $('#' + findObj.tabId).show();
        }

    }

    /**
     * Removes the tab, the row entry in the scripts tab
     *
     * @param {String} scriptName - name of the script to be deleted
     * @param {integer} rowNum -  row number where the delete click event happened
     * @memberof SuiteView
     */
    removeScriptAndTab(scriptName, rowNum) {
        this.displayTab(scriptName, true);
        let objIdx = -1;
        let obj = this.data.scripts.find(function (obj, idx, arr) {
            if (obj.name === scriptName) {
                objIdx = idx;
                return true;
            }
        }, scriptName);
        this.data.scripts.splice(objIdx, 1);
        $('#' + obj.panelId).remove();
        $('#' + obj.tabId).parent().remove();
        this.tabIdx--;
        if (this.tabIdx !== 0) {
            this.scriptsJxl.deleteRow(rowNum);
        }
        else {
            this.scriptsJxl.destroy(document.getElementById('#scripts-list'), true);
            this.scriptsJxl = null;
            $('#no-scripts').show();
        }
    }

    /**
     * Creates a new script tab
     *
     * @param {String} scriptName - name of the script
     * @param {boolean} run - run the script if true
     * @param {boolean} stopOnError -  true is script to be stopped on error
     * @param {String} data - script data, if loaded from file
     * @memberof SuiteView
     */
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

        $('#suite-tabs').append('<li class="nav-item"><a class="nav-link" id="' + tabId + '" data-toggle="tab" href="#' + panelId + '" role="tab" aria-controls="scripts" aria-selected="true">' + scriptName + '</a><span class="tabClose" aria-hidden="true">&times;</span></li>');
        $('#scripts-tab-content').append('<div class="tab-pane fade show" id="' + panelId + '" role="tabpanel" aria-labelledby="' + tabId + '"><div id="' + sheetsId + '" class="sheets-content pt-1"></div></div>');

        let jxl = this.createSheets(sheetsId, data);
        scriptData.jxl = jxl;

        this.data.scripts.push(scriptData);
        console.log('View data:', this.data);
        this.addScriptsTabEntry(scriptName, run, stopOnError);
        if (!data) {
            $('#' + tabId).tab('show');
        }
    }

    /**
     * Create new JExcel sheets in the tab container
     *
     * @param {String} sheetsId - Element id in the UI
     * @param {JSON} data - script data loaded from the file
     * @returns - JExcel Object filled with the values
     * @memberof SuiteView
     */
    createSheets(sheetsId, data) {
        let thisViewObj = this;
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
            ],
            toolbar: [
                {
                    type: 'i',
                    content: 'plus_one',
                    tooltip: 'Add a blank row at the end',
                    onclick: function () {
                        thisViewObj.addRows(jxl, 1);
                        toastr.success("One row added");
                    }
                },
                {
                    type: 'i',
                    content: 'reorder',
                    tooltip: 'Add 10 blank rows at the end',
                    onclick: function () {
                        thisViewObj.addRows(jxl, 10);
                        toastr.success("Ten rows added")
                    }

                }
            ]

        });

        if (data && data.length > 0) {
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

    
    /**
     * Adds the specified number of rows to the sheet
     *
     * @param {JExcel} jxl - JExcel instance
     * @param {integer} numRows - Number of rows to be added at the end
     * @memberof SuiteView
     */
    addRows(jxl, numRows){
        jxl.insertRow(numRows);
    }

    /**
     * Loads the scripts from the suite file uploaded
     *
     * @param {JSON} scripts - Scripts json from the suite
     * @memberof SuiteView
     */
    loadScripts(scripts) {
        scripts.forEach(script => {
            this.createNewScriptTab(script.name, script.run, script.stopOnError, script.actions);
        });
    }

    /**
     * Displays the suite settings dialog
     *
     * @param {boolean} isClearSettings - Clear the UI elements if true
     * @memberof SuiteView
     */
    displaySettings(isClearSettings) {
        if (isClearSettings) {
            this.clearSettingsFields();
        }
        $("#settingsModal").modal('show');
    }

    /**
     * Hides the suite settings dialog
     *
     * @memberof SuiteView
     */
    hideSettings() {
        $("#settingsModal").modal('hide');
    }

    /**
     * Clears the fields in the suite settings dialog
     *
     * @memberof SuiteView
     */
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

    /**
     * Clears the fields in the new script dialog
     *
     * @memberof SuiteView
     */
    clearNewScriptDialogFields() {
        $("#tb-script-name").val('');
        $('cb-ns-stop-on-error').prop('checked', false);
        $('cb-ns-execute').prop('checked', false);
    }

    /**
     * Shows validation dialogs for suite during save
     *
     * @param {JSON} data - Validation response that contains errors / warnings
     * @memberof SuiteView
     */
    showErrorDialog(data) {
        const TYPE_ERROR = 101;
        const TYPE_WARNING = 102;
        let errorBody = $('#errorDialog .modal-body');
        errorBody.empty();
        let msgs = data.messages;
        for (let i = 0; i < msgs.length; ++i) {
            let str = "";
            if (msgs[i].type === TYPE_ERROR) {
                str = '<span class="type-error">ERROR: </span><span>' + msgs[i].message + '</span><br/>'
            }
            else {
                str = '<span class="type-warning">WARNING: </span><span>' + msgs[i].message + '</span><br/>'
            }
            errorBody.append(str);
        }
        $('#errorDialog').modal('show');
    }

    clearErrorDialog() {
        let errorBody = $('#errorDialog .modal-body');
        errorBody.empty();
        errorBody.append('There are no errors to display. Please save the suite to see validation errors, if any.');
    }

}










