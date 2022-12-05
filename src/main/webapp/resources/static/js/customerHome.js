document.addEventListener('DOMContentLoaded', function () {
    const localhost = "http://localhost:8080/Ecommerce_war";
    let firstCaruselItem = document.getElementById("CarouselItems").firstElementChild;
    let searchInput = document.getElementById("Search");
    if (firstCaruselItem)
        firstCaruselItem.classList.add("active");

    searchInput.addEventListener("keypress", function (event) {
        if (event.key === "Enter") {
            document.getElementById("btnSearch").click();
        }
    });

    document.getElementById("btnSearch").addEventListener('click', function () {
        window.location.href = localhost + "/customer/search/home.htm?category=" + searchInput.value + "&name=" + searchInput.value;
    });
});