function addToCart(productid,username,quantityEl){
    var cart_owner =  window.localStorage.getItem("cart_owner");
    var cart_items = window.localStorage.getItem("cart_items");

    if((cart_owner == null || cart_owner != username) || cart_items == null)
    {
        createNewCart(productid,username,quantityEl.parentNode.querySelector('input[id=inputQuantity]').value)
    }
    else
    {
        let q = parseInt(quantityEl.parentNode.querySelector('input[id=inputQuantity]').value);

        cart_items = cart_items.split('},');

        var arrayLength = cart_items.length;
        var idx = -1;
        for (var i = 0; i < arrayLength; i++) {
            if(i+1 != arrayLength)
            {
                cart_items[i] = cart_items[i]+'}'; 
            }


            var item = JSON.parse(cart_items[i]);
            if(item.id == productid)
            {
                idx = i;
                break;
            }
        }

        if(idx > -1)
        {
            var item = JSON.parse(cart_items[idx]);
            item.quantity = item.quantity+q;
            
            cart_items.splice(idx, 1);
            cart_items.push(JSON.stringify(item));
        }
        else
        {
            cart_items.push(JSON.stringify({
                id : parseInt(productid),
                quantity :parseInt(q)
            }));
        }
        window.localStorage.setItem("cart_items",cart_items)
    }
}


function removeFtomCart(productid,username,quantityEl){
    var cart_owner =  window.localStorage.getItem("cart_owner");
    var cart_items = window.localStorage.getItem("cart_items");

    if(!((cart_owner == null || cart_owner != username) || cart_items == null))
    {
        let q = parseInt(quantityEl.parentNode.querySelector('input[id=inputQuantity]').value);

        cart_items = cart_items.split('},');
        var arrayLength = cart_items.length;
        var idx = -1;
        for (var i = 0; i < arrayLength; i++) {
            if(i+1 != arrayLength)
            {
                cart_items[i] = cart_items[i]+'}'; 
            }

            var item = JSON.parse(cart_items[i]);
            if(item.id == productid)
            {
                idx = i;
                break;
            }
        }

        if(idx > -1)
        {
            var item = JSON.parse(cart_items[idx]);
            item.quantity = item.quantity-q;

            cart_items.splice(idx, 1);
            if(item.quantity > 0)
            {
                cart_items.push(JSON.stringify(item));
                window.localStorage.setItem("cart_items",cart_items)
            }
            else
            {
                if(cart_items.length == 0)
                {
                    window.localStorage.removeItem("cart_items");
                }
            }
        }
    }
}

function createNewCart(productid,username,quantity){
    window.localStorage.setItem("cart_owner",username);
    
    var items = [];
    items.push(JSON.stringify({
        id : parseInt(productid),
        quantity :parseInt(quantity)
    }));
    
    window.localStorage.setItem("cart_items",items);
}

function Increment(el,prodid)
{
    el = el.parentNode.children[1];
    el.stepUp();

    var cart_items = window.localStorage.getItem("cart_items");
    cart_items = cart_items.split('},');
    var arrayLength = cart_items.length;
    var idx = -1;
    for (var i = 0; i < arrayLength; i++) {
        if(i+1 != arrayLength)
        {
            cart_items[i] = cart_items[i]+'}'; 
        }
        var item = JSON.parse(cart_items[i]);
        if(item.id == prodid)
        {
            idx = i;
        }
    }

    
    var item = JSON.parse(cart_items[idx]);
    cart_items.splice(idx, 1);
    item.quantity = el.value;

    cart_items.push(JSON.stringify(item));
    window.localStorage.setItem("cart_items",cart_items)



   el.parentNode.parentNode.children[3].children[1].textContent='x'+el.value;
}

function Decrement(el,prodid)
{
    el = el.parentNode.children[1];
    el.stepDown();



    var cart_items = window.localStorage.getItem("cart_items");
    cart_items = cart_items.split('},');
    var arrayLength = cart_items.length;
    var idx = -1;
    for (var i = 0; i < arrayLength; i++) {
        if(i+1 != arrayLength)
        {
            cart_items[i] = cart_items[i]+'}'; 
        }
        var item = JSON.parse(cart_items[i]);
        if(item.id == prodid)
        {
            idx = i;
        }
    }

    var item = JSON.parse(cart_items[idx]);
    cart_items.splice(idx, 1);
    item.quantity = el.value;

    if(item.quantity > 0)
    {
        cart_items.push(JSON.stringify(item));
        window.localStorage.setItem("cart_items",cart_items)
    }
    else
    {
        if(cart_items.length == 0)
        {
            window.localStorage.removeItem("cart_items");
        }
        else
        {
            window.localStorage.setItem("cart_items",cart_items)
        }
    }



    el.parentNode.parentNode.children[3].children[1].textContent='x'+el.value;
}