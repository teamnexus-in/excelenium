var scriptsCount = 0;

var suiteData = {};

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
        }//showScriptsTab();

    }
    else if (qp == "load") {

    }


});

function showScriptsTab() {
    db = {
        "actions": ["FILL", "CLEAR", "CLICK", "RIGHT_CLICK", "CHECK", "SELECT", "VERIFY_TEXT", "VERIFY_PRESENT", "IS_VISIBLE", "CHECK_ATTRIBUTE", "ACCEPT_POPUP", "DISMISS_POPUP", "SWITCH_TO_IFRAME", "SWITCH_TO_PARENT", "CAPTURE_SCREEN", "NAVIGATE", "SET_VARIABLE", "UNSET_VARIABLE"
        ],
        scripts: [[]]
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
            { type: 'text', title: 'Name', width: 200 },
            { type: 'checkbox', title: 'Execute', width: 120 },
            { type: 'checkbox', title: 'Stop on Error', width: 120 },
            // { type: 'dropdown', title: 'Actions', width: 200, autocomplete:true, source: db.actions },
        ]
    });

    jxl.setRowData(0, ["Script10", true, true, "FILL"]);


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
        createNewScript();
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

    $('#tab-add-script').click(function () {
        clearnNewScriptDialog();
        $("#newScriptModal").modal('show');
    });

}

function clearnNewScriptDialog() {
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

function createNewScript() {
    // Add a new tab
    // register event handler
    // create sheet
}

function saveSuiteSettings() {
    console.log("Saved Suite Settings");
}