$(document).ready(function () {
    bsCustomFileInput.init();
    $('#btn-load').click(function () {
        $('#file-suite').val('');
        $('#file-error-msg').hide();
        $('#file-upload-modal').modal('show');
    });
    
    $('#btn-upload-close').click(function(){
        $('#file-suite').val('');
    });

    $('#btn-new').click(function () {
        console.log("savedSuite", sessionStorage.getItem('savedSuite'));
        sessionStorage.removeItem('savedSuite');
        
        document.location.href = "/suite"
    });

    $('#btn-upload-file').click(function(){
        sessionStorage.removeItem('savedSuite');
        if($('#file-suite').val()==''){
            $('#file-error-msg').show();
        }
        else{
            $('#file-upload-modal').modal('hide');
            $('#form-file-upload').submit();
        }
    });

});