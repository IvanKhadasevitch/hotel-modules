$(document).ready(function () {
    $('.addProductBtn').click(function () {
        addProduct($(this));
    });
    $('.reduceProductBtn').click(function () {
        reduceProduct($(this));
    });
});

function addProduct(element) {
    var productId = $(element).attr('id');
    var json = JSON.stringify(productId);
    console.log(json);
    $.ajax({
        type: 'get',
        url: contextUrl + '/frontController?command=addProduct&productId=' + productId
    }).done(function (data) {
        $('#count'+productId).text(data);
    }).fail(function (data) {
        if (console && console.log) {
            console.log("Error data:", data);
        }
    });
}

function reduceProduct(element) {
    var productId = $(element).attr('id');
    $.ajax({
        type: 'post',
        url: contextUrl + '/frontController',
        data:{command:'reduceProduct', id:productId}
    }).done(function (data) {
        $('#count'+productId).text(data);
    }).fail(function (data) {
        if (console && console.log) {
            console.log("Error data:", data);
        }
    });
}
