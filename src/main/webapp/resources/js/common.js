window.onscroll = function() {
    scrollFunction()
};

function scrollFunction() {
    var mybutton = document.getElementById("btnUp");
    if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
        mybutton.style.display = "block";
    } else {
        mybutton.style.display = "none";
    }
}

function topFunction() {
    document.body.scrollTop = 0; // For Safari
    document.documentElement.scrollTop = 0; // For Chrome, Firefox, IE and Opera
}

function reloadRequestsTable(request_id) {
    var request = new XMLHttpRequest();
    var url = "close-request?id=" + request_id;
    request.open('GET', url);
    request.addEventListener("readystatechange", function() {
        if (request.readyState === 4 && request.status === 200) {
            alert(request.responseText);
        }
    });
    request.send();
}