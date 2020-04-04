function Suite() {
    var scriptCount;
}

Suite.prototype.initialize = function(){
    this.scriptCount = 0;

    // Clear the settings fields
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

Suite.prototype.displaySettings = function(){
    // Show the settings modal
    $('#settingsModal').modal('show');
}

Suite.prototype.saveSettings = function(){
    console.log("Saving the settings");
}

Suite.prototype.save = function(){

}

Suite.prototype.addNewScript = function(){

}

Suite.prototype.close = function(){

}