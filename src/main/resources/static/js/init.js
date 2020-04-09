$(document).ready(function () {
    $('#btn-load').click(function () {
        $('#file-upload').trigger('click'); 
    });
    $('#btn-new').click(function () {
        document.location.href = "/script?type=new"
    });
    $('#file-upload').change(function(data) {
        console.log(data);
        alert("You selected the file");
    });

});