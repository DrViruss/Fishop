$(document).ready(

    function() {
        var cart_owner =  window.localStorage.getItem("cart_owner");
        var cart_items = window.localStorage.getItem("cart_items");

        var items = []

        if(!(cart_owner == null || cart_items == null))
        {
            var ids = [];

            cart_items = cart_items.split('},');
            var arrayLength = cart_items.length;
            for (var i = 0; i < arrayLength; i++) {
                if(i+1 != arrayLength)
                {
                    cart_items[i] = cart_items[i]+'}'; 
                }
    
    
                var item = JSON.parse(cart_items[i]);
                if(item.quantity > 1)
                {
                    ids.push(item.id);
                    items.push(item);
                }
            }

        }


        var token = $("meta[name='_csrf']").attr("content");
	    var header = $("meta[name='_csrf_header']").attr("content");


        $.ajaxSetup({
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            }
        });
        $.ajax({
            type: "POST",
            contentType : "application/json",
            url: "/api/user/getCartItems",
            data : JSON.stringify(ids),
            dataType : 'json',
            success: function(result) {
                if (result != null) {
                    var arrayLength = result.length;
                    for (var i = 0; i < arrayLength; i++) {
                        var item = result[i];
                        var tmp = "SOLD";
                        if(item.quantity > 0) tmp = item.productprice+'$';
//                        if(item.quantity > 0)
//                        {
                        //   $("#cartitemsbody").append("<div class='card-body p-4'><div class='row d-flex justify-content-between align-items-center'><div class='col-md-2 col-lg-2 col-xl-2'><img src='https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-shopping-carts/img1.webp' class='img-fluid rounded-3' alt='Cotton T-shirt'></div><div class='col-md-3 col-lg-3 col-xl-3'><p class='lead fw-normal mb-2' >"+item.productname+"</p><p><span class='text-muted'>Specie: </span> <span> "+item.speciename+" </span> </p></div><div class='col-md-3 col-lg-3 col-xl-2 d-flex'><button class='btn btn-link px-2' onclick='Decrement(this)'><i class='fas bi-dash-lg'></i></button><input id='form1' min='1' name='quantity' value='"+item.quantity+"' type='number' class='form-control form-control-sm' /><button class='btn btn-link px-2' onclick='Increment(this)'><i class='fas bi-plus-lg'></i></button></div><div class='col-md-3 col-lg-2 col-xl-2 offset-lg-1'><h5 class='mb-0'>"+item.productprice+"<h6><span id='price_mlt' class='text-muted' >x"+item.quantity+"</span></h6></h5></div></div></div>");
                            $("#cartitemsbody").append(
                            "<div class='card-body p-4'>"+
                                "<div class='row d-flex justify-content-between align-items-center'>"+
                                    "<div class='col-md-2 col-lg-2 col-xl-2'>"+
                                        "<img src='https://dummyimage.com/400x400/dee2e6/6c757d.jpg' class='img-fluid rounded-3' alt='Cotton T-shirt'>"+
                                    "</div>"+
                                    "<div class='col-md-3 col-lg-3 col-xl-3'>"+
                                        "<p class='lead fw-normal mb-2' >"+item.productname+"</p>"+
                                        "<p> <span class='text-muted'>Specie: </span> <span> "+item.speciename+" </span> </p>"+
                                    "</div>"+
                                    "<div class='col-md-3 col-lg-3 col-xl-2 d-flex'>"+
                                        "<button class='btn btn-link px-2' onclick='Decrement(this,"+item.id+")'>"+
                                            "<i class='fas bi-dash-lg'> </i>"+
                                        "</button>"+
                                        "<input id='form1' min='0' max='"+((item.quantity == 0) ? items[i].quantity : item.quantity)+"' name='quantity' value='"+items[i].quantity+"' type='number' class='form-control form-control-sm' disabled/>"+
                                        "<button class='btn btn-link px-2' onclick='Increment(this,"+item.id+")'>"+
                                            "<i class='fas bi-plus-lg'> </i>"+
                                        "</button>"+
                                    "</div>"+
                                    "<div class='col-md-3 col-lg-2 col-xl-2 offset-lg-1'>"+
                                        "<h5 class='mb-0'>"+ tmp +"<h6>"+
                                            "<span id='price_mlt' class='text-muted' >x"+item.quantity+"</span>"+
                                            "</h6>"+
                                        "</h5>"+
                                    "</div>"+
                                "</div>"+
                            "</div>");
//                        }
                    }
                } else {
                    $("#cartitemsbody").append("<strong>Error</strong>");
                    console.log("Fail: ", result);
                }
            },
            error: function(e) {
                $("#getResultDiv").html("<strong>Error</strong>");
                console.log("ERROR: ", e);
            }
        });
    })
