function loadSelectedOption(defaultId, selectId, optionValue) {
    if (optionValue != "") {
        document.getElementById(defaultId).removeAttribute("selected");
        document.getElementById(selectId).value = optionValue;
    }
}

function submitOnChange(formId) {
    document.getElementById(formId).submit();
}