//ARROW BUTTON SCROLL UP
window.onscroll = function() {
    scrollFunction()
};

function scrollFunction() {
    let buttonArrowUp = document.getElementById("btnUp");
    if (document.body.scrollTop > 40 || document.documentElement.scrollTop > 40) {
        buttonArrowUp.style.display = "block";
    } else {
        buttonArrowUp.style.display = "none";
    }
}

function topFunction() {
    document.body.scrollTop = 0; // For Safari
    document.documentElement.scrollTop = 0; // For Chrome, Firefox, IE and Opera
}

//BUILD REQUESTS TABLE
function reloadRequestsTable(request_id) {
    let request = new XMLHttpRequest();
    let url = "close-request?id=" + request_id;
    request.open('GET', url);
    request.addEventListener("readystatechange", function() {
        if (request.readyState === 4 && request.status === 200) {
            let table = document.getElementById("requestsTable");
            table.innerHTML = '';
            let requests = JSON.parse(request.responseText);
            buildRequestsTable(table, requests);
        }
    });
    request.send();
}

function buildRequestsTable(table, jsonRequests) {
    let thead = table.createTHead();
    let row = thead.insertRow();
    for(let key of Object.keys(jsonRequests[0])) {
        let th = document.createElement("th");
        let headerText = document.createTextNode(key);
        th.appendChild(headerText);
        row.appendChild(th);
    }
    let tbody = table.createTBody();
    for(let element of jsonRequests) {
        let row = tbody.insertRow();
        for(let key of Object.keys(element)) {
            let cell = row.insertCell();
            let text = document.createTextNode(element[key]);
            cell.appendChild(text);
        }
        let button = document.createElement("button");
        let cell = row.insertCell();
        button.addEventListener("click", function () {
            reloadRequestsTable(element["ID"]);
        });
        button.setAttribute('class', 'btn-req-cancel');
        cell.setAttribute('class', 'close-request-column');
        cell.appendChild(button);
    }
}

//WHEN DOCUMENT LOADED
document.addEventListener('DOMContentLoaded', function(){
    showSlides();

    let collapsibleItems = document.getElementsByClassName("collapsible");
    if(collapsibleItems[0]) {
        collapsibleItems[0].classList.toggle("active");
        collapsibleItems[0].nextElementSibling.style.display = "block";
        collapsibleItems[0].style.backgroundImage = "url('/resources/img/minus.png')";
    }
    for (let item = 0; item < collapsibleItems.length; item++) {
        collapsibleItems[item].addEventListener("click", function() {
            this.classList.toggle("active");
            let content = this.nextElementSibling;
            if (content.style.display === "block") {
                content.style.display = "none";
                this.style.backgroundImage = "url('/resources/img/plus.png')";
            } else {
                content.style.display = "block";
                this.style.backgroundImage = "url('/resources/img/minus.png')";
            }
        });
    }

    if(document.getElementById("catalog-search")) {
        document.getElementById("catalog-search").addEventListener("keyup", function () {
            let text = this.value;
            let request = new XMLHttpRequest();
            let url = "catalog-search?text=" + text;
            request.open('GET', url);
            request.addEventListener("readystatechange", function() {
                if (request.readyState === 4 && request.status === 200) {
                    let table = document.getElementById("catalog-search-table");
                    table.innerHTML = '';
                    let jsonResponse = JSON.parse(request.responseText);
                    buildCatalogSearchTable(table, jsonResponse);
                }
            });
            request.send();
        });
    }
});

//SLIDER
let slideIndex = 0;
let slideTimeout;

function moveSlides(difference) {
    clearTimeout(slideTimeout);
    if(difference === -1) {
       slideIndex = slideIndex - 2;
    }
    showSlides();
}

function showSlides() {
    let slides = document.getElementsByClassName("mySlides");
    for (let i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
    }
    if (slideIndex > slides.length - 1) {
        slideIndex = 0;
    }
    if(slideIndex < 0) {
        slideIndex = slides.length - 1;
    }
    if(slides[slideIndex] != null) {
        slides[slideIndex].style.display = "inline";
    }
    slideIndex++;
    slideTimeout = setTimeout(showSlides, 5000);
}

//CATALOG SEARCH
function buildCatalogSearchTable(table, jsonResponse) {
    let thead = table.createTHead();
    let row = thead.insertRow();
    for(let key of Object.keys(jsonResponse[0])) {
        let th = document.createElement("th");
        let headerText = document.createTextNode(key);
        th.appendChild(headerText);
        row.appendChild(th);
    }
    let tbody = table.createTBody();
    for(let element of jsonResponse) {
        let row = tbody.insertRow();
        for(let key of Object.keys(element)) {
            let cell = row.insertCell();
            let text = document.createTextNode(element[key]);
            cell.appendChild(text);
        }
    }
}