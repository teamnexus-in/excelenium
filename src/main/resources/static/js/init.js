$(document).ready(function () {
    $('#btn-load').click(function () {
        document.location.href = "/script?type=load"
    });
    $('#btn-new').click(function () {
        document.location.href = "/script?type=new"
    });
});