const username_min_length = 2;
const username_max_length = 20;
const password_min_length = 8;

function validate(form) {
    fail = "";

    invalidUserName = validateUserName(form.userName.value);
    document.getElementById("userNameError").innerHTML = invalidUserName;

    invalidPassword = validatePassword(form.password.value);
    document.getElementById("passwordError").innerHTML = invalidPassword;

    invalidRePassword = validateRePassword(form.password.value, form.rePassword.value);
    document.getElementById("rePasswordError").innerHTML = invalidRePassword;

    invalidEmail = validateEmail(form.email.value);
    document.getElementById('emailError').innerHTML = invalidEmail;
    if (invalidEmail == "") {
        findRegisteredEmail(form.email.name + "=" + form.email.value).then((result => {
            document.getElementById('emailError').innerHTML = result;
            fail += result;
            console.log(fail);
            if (fail == "") {
                document.getElementById(form.id).submit();
            }
        }));
    }

    fail += invalidUserName;
    fail += invalidEmail;
    fail += invalidPassword;
    fail += invalidRePassword;
    return false;
}

function checkName(elementId, errorId) {
    invalidUserName = validateUserName(elementId.value)
    document.getElementById(errorId).innerHTML = invalidUserName;
    return invalidUserName;
}

async function checkEmail(elementId, errorId) {
    invalidEmail = validateEmail(elementId.value);
    if (invalidEmail == "") {
        invalidEmail = await findRegisteredEmail(elementId.name + "=" + elementId.value);
    }
    document.getElementById(errorId).innerHTML = invalidEmail;
    return invalidEmail;
}

function findRegisteredEmail(args) {
    return new Promise((resolve) => {
        var xhttp = new XMLHttpRequest();
        xhttp.open("POST", "FindRegisteredUser");
        xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhttp.send(args);
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                resolve(this.responseText);
            }
        };
    });
}

function checkPassword(elementId, errorId) {
    invalidPassword = validatePassword(elementId.value)
    document.getElementById(errorId).innerHTML = invalidPassword;
    return invalidPassword;
}

function checkRePassword(formId, errorId) {
    form = document.getElementById(formId);
    invalidRePassword = validateRePassword(form.password.value, form.rePassword.value);
    document.getElementById(errorId).innerHTML = invalidRePassword;
    return invalidRePassword;
}

function validateUserName(userName) {
    if (userName == "") return "Username field cannot be empty\n";
    if (/[^a-zA-Z0-9]/.test(userName)) return "Username must contain only letters and numbers\n";
    if (!/^[a-zA-Z]/.test(userName)) return "Username must contain a letter as first character\n";
    if (userName.length < username_min_length) return "Username must contain at least " + username_min_length + " characters\n";
    if (userName.length > username_max_length) return "Username must not contain more than " + username_max_length + " characters\n";
    return "";
}

function validateEmail(email) {
    if (email == "") return "Email address field cannot be empty\n";
    if (!/\S+@\S+\.\S+/.test(email)) return "Invalid email address\n";
    return "";
}

function validatePassword(password) {
    if (password == "") return "Passwords field cannot be empty\n";
    if (!/[a-z]/.test(password)) return "Passwords must contain at least a lowercase letter\n";
    if (!/[A-Z]/.test(password)) return "Passwords must contain at least an uppercase letter\n";
    if (!/[0-9]/.test(password)) return "Passwords must contain at least a number\n";
    if (password.length < password_min_length) return "Passwords must be at least " + password_min_length + " characters\n";
    return "";
}

function validateRePassword(password, rePassword) {
    if (rePassword == "") return "Re-enter passwords field cannot be empty\n";
    if (password != rePassword) return "Passwords are not matched\n";
    return "";
}