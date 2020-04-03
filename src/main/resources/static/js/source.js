var scriptsCount = 10;

var suiteData = {};

var tabId = 1;

$(document).ready(function () {

    registerBrowserHandlers();
    registerDialogHandlers();
    registerTabHandlers();

    var url = new URL(window.location.href);
    var qp = url.searchParams.get('type');

    console.log("qp:" + qp);
    if (qp == "new") {
        $('#settingsModal').modal('show');
        if (scriptsCount == 0) {
            $('#no-scripts').show();
        }
        else {
            showScriptsTab();
        }

    }
    else if (qp == "load") {

    }


});

function showScriptsTab() {
    db = {
        "actions": ["FILL", "CLEAR", "CLICK", "RIGHT_CLICK", "CHECK", "SELECT", "VERIFY_TEXT", "VERIFY_PRESENT", "IS_VISIBLE", "CHECK_ATTRIBUTE", "ACCEPT_POPUP", "DISMISS_POPUP", "SWITCH_TO_IFRAME", "SWITCH_TO_PARENT", "CAPTURE_SCREEN", "NAVIGATE", "SET_VARIABLE", "UNSET_VARIABLE"
        ],
        "preprocess": ["ADD_ATTRIBUTE", "REMOVE_ATTRIBUTE", "SET_ATTRIBUTE"],
        "scripts": [[]]
        // "scripts": [
        //     { "Name": "Script1", "Execute": true, "Stop on Error": true, "Actions": "FILL" },
        //     { "Name": "Script2", "Execute": true, "Stop on Error": true, "Actions": "VERIFY_TEXT" },
        //     { "Name": "Script3", "Execute": true, "Stop on Error": true, "Actions": "VERIFY_PRESENT" },
        //     { "Name": "Script4", "Execute": true, "Stop on Error": true, "Actions": "IS_VISIBLE" },
        //     { "Name": "Script5", "Execute": true, "Stop on Error": true, "Actions": "SELECT" },
        //     { "Name": "Script6", "Execute": true, "Stop on Error": true, "Actions": "CLEAR" },
        //     { "Name": "Script7", "Execute": true, "Stop on Error": true, "Actions": "CHECK_ATTRIBUTE" },
        //     { "Name": "Script8", "Execute": true, "Stop on Error": true, "Actions": "CHECK_ATTRIBUTE" },
        //     { "Name": "Script9", "Execute": true, "Stop on Error": true, "Actions": "FILL" }
        // ]
    };

    var jxl = $('#scripts-list').jexcel({
        data: db.scripts,
        columns: [
            { type: 'checkbox', title: 'Run', width: 50 },
            { type: 'checkbox', title: 'Stop on Error', width: 100 },
            { type: 'text', title: 'Name', width: 150 },
            { type: 'autocomplete', title: 'Type', width: 150, source: db.actions },
            { type: 'text', title: 'Id/XPath', width: 250 },
            { type: 'text', title: 'Element Val', width: 150 },
            { type: 'text', title: 'Attr Name', width: 150 },
            { type: 'text', title: 'Attr Val', width: 100 },
            { type: 'autocomplete', title: 'Action', width: 200, source: db.preprocess },
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

    jxl.setRowData(0, ["Script10", true, true, "FILL"]);
    jxl.insertRow(20);


}

function registerBrowserHandlers() {
    $('.cb-browser-settings').click(function () {
        var result = $(this).prop('checked') == true;
        var cboxEl = $(this).attr("id");
        var idx = cboxEl.indexOf('-');
        var elName = cboxEl.substr(idx + 1);
        console.log(cboxEl + " " + elName);
        var tbEl = '#tb-' + elName
        $(tbEl).attr('disabled', !result);
        // $("#tb-chrome").attr('disabled', !result);
    });
    // $('#cb-firefox').click(function () {
    //     var result = $(this).prop('checked') == true;
    //     $("#tb-firefox").attr('disabled', !result);
    // });
    // $('#cb-edge').click(function () {
    //     var result = $(this).prop('checked') == true;
    //     $("#tb-edge").attr('disabled', !result);
    // });
    // $('#cb-opera').click(function () {
    //     var result = $(this).prop('checked') == true;
    //     $("#tb-opera").attr('disabled', !result);
    // });
    // $('#cb-user-agent').click(function () {
    //     var result = $(this).prop('checked') == true;
    //     $("#tb-user-agent").attr('disabled', !result);
    // });
}

function registerTabHandlers() {
    // $('#suite-tabs a').click(function () {
    //     const newLocal = $(this).attr('id');
    //     alert("You clicked: " + newLocal + " tab");
    // });
}

function registerDialogHandlers() {
    // Save Suite Settingss
    $('#btn-settings-save').click(function () {
        saveSuiteSettings();
    });

    // Create New Script
    $('#btn-new-script-ok').click(function () {
        var scriptName = $('#tb-script-name').val();
        if (scriptName != '') {
            createNewScript(scriptName);
            $("#newScriptModal").modal('hide');
        }
    });

    $("#btn-new-suite").click(function () {
        // check if a script is already open and save it before creating a new one
        clearSettingsFields();
        $("#settingsModal").modal('show');
    });

    $('#btn-settings').click(function () {
        // load the settings into the fields and show
        $("#settingsModal").modal('show');
    });

    $('#btn-new-script').click(function () {
        clearNewScriptDialog();
        $("#newScriptModal").modal('show');
    });

}

function clearNewScriptDialog() {
    $("#tb-script-name").val('');
    $('cb-ns-stop-on-error').prop('checked', false);
    $('cb-ns-execute').prop('checked', false);
}

function clearSettingsFields() {
    console.log("Clearing settings for new suite");
    $('.cb-browser-settings').each(function () {
        console.log("Before: " + $(this).is(':checked'))
        $(this).prop('checked', false);
        console.log("After: " + $(this).is(':checked'))
    });
    $('.tb-browser-settings').each(function () {
        $(this).val('');
    });

    $('#tb-suite-name').val('');
    $('#tb-server-url').val('');
    $('#cb-run-browsers-concurrent').prop('checked', false);
}

function createNewScript(scriptName) {
    // Add a new tab
    createNewTab(scriptName);
    // register event handler
    // create sheet
    // add row in scripts tab
}

function createNewTab(scriptName) {
    tabId++
    $('#suite-tabs').append('<li class="nav-item"><a class="nav-link" id="tab-scripts' + tabId + '" data-toggle="tab" href="#tpanel-scripts-tab' + tabId + '" role="tab" aria-controls="scripts" aria-selected="true">' + scriptName + '</a></li>');
    $('#scripts-tab-content').append('<div class="tab-pane fade show" id="tpanel-scripts-tab' + tabId + '" role="tabpanel" aria-labelledby="scripts-tab' + tabId + '"><div id="scripts' + tabId + '" class="sheets-content">New content of Tab ' + tabId + '</div></div>')
}

function saveSuiteSettings() {
    console.log("Saved Suite Settings");
}