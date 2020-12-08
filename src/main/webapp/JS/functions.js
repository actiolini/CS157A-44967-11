function loadSelectedState(state) {
    if (state != "") {
        document.getElementById("default").removeAttribute("selected");
        document.getElementById("state").value = state;
    }
}