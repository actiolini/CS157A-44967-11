const username_min_length = 2;
const username_max_length = 20;
const password_min_length = 8;

function validateSignUp(form) {
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
    invalidUserName = validateUserName(elementId.value);
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

function validateStaffSignUp(form) {
    fail = "";

    invalidRole = validateRole(form.role.value);
    document.getElementById("roleError").innerHTML = invalidRole;
    invalidLocation = "";
    if (invalidRole == "" && form.role.value != "admin") {
        invalidLocation = validateTheatreLocation(form.theatreLocation.value);
    }
    document.getElementById("theatreLocationError").innerHTML = invalidLocation;

    invalidUserName = validateUserName(form.userName.value);
    document.getElementById("userNameError").innerHTML = invalidUserName;

    invalidPassword = validatePassword(form.password.value);
    document.getElementById("passwordError").innerHTML = invalidPassword;

    invalidEmail = validateEmail(form.email.value);
    document.getElementById('emailError').innerHTML = invalidEmail;
    if (invalidEmail == "") {
        findRegisteredEmail(form.email.name + "=" + form.email.value).then((result => {
            document.getElementById('emailError').innerHTML = result;
            fail += result;
            if (fail == "") {
                document.getElementById(form.id).submit();
            }
        }));
    }

    fail += invalidRole;
    fail += invalidLocation;
    fail += invalidUserName;
    fail += invalidEmail;
    fail += invalidPassword;
    return false;
}

function checkRole(elementId, errorId, theatreLocationInputId) {
    invalidRole = validateRole(elementId.value)
    document.getElementById(errorId).innerHTML = invalidRole;
    if (invalidRole == "" && elementId.value == "admin") {
        document.getElementById(theatreLocationInputId).setAttribute("hidden", "");
    } else {
        document.getElementById(theatreLocationInputId).removeAttribute("hidden");
    }
    return invalidRole;
}

function validateRole(role) {
    if (role == "none") {
        return "Please select a role\n";
    }
    if (!(role == "admin" || role == "manager" || role == "faculty")) {
        return "Invalid role selected\n";
    }
    return "";
}

function checkTheatreLocation(elementId, errorId) {
    invalidLocation = validateTheatreLocation(elementId.value)
    document.getElementById(errorId).innerHTML = invalidLocation;
    return invalidLocation;
}

function validateTheatreLocation(location) {
    if (location == "" || location == "none") {
        return "Please select a theatre location\n";
    }
    return "";
}

function validateSignIn(form) {
    fail = "";
    if (window.location.href.includes("staffsignin.jsp")) {
        fail += checkSignInInput(form.staffId, "staffIdError");
    } else if (window.location.href.includes("signin.jsp")) {
        fail += checkSignInInput(form.email, "emailError");
    }
    fail += checkSignInInput(form.password, "passwordError");
    return fail == "";
}

function checkSignInInput(elementId, errorId) {
    invalidInput = "";
    if (elementId.value == "") {
        if (elementId.name == "email") {
            invalidInput = "Please enter an email";
        } else if (elementId.name == "password") {
            invalidInput = "Please enter a password";
        } else if (elementId.name == "staffId") {
            invalidInput = "Please enter a staff ID number";
        }
    }
    document.getElementById(errorId).innerHTML = invalidInput;
    return invalidInput;
}

function validateMovieForm(form) {
    title = form.title.value;
    releaseDate = form.releaseDate.value;
    duration = form.duration.value;
    trailer = form.trailer.value;
    description = form.description.value
    errorId = document.getElementById("errorMessage");
    if (title == "" || releaseDate == "" || duration == "" || trailer == "" || description == "") {
        errorId.innerHTML = "* required fields"
        return false;
    }
    if (!/^\d+$/.test(duration)) {
        errorId.innerHTML = "duration must be a number"
        return false;
    }
    return true;
}

function validateTheatreForm(form) {
    theatreName = form.theatreName.value;
    address = form.address.value;
    city = form.city.value;
    state = form.state.value;
    country = form.country.value;
    zip = form.zip.value;
    errorId = document.getElementById("errorMessage");
    if (theatreName == "" || address == "" || city == "" || state == "none" || country == "" || zip == "") {
        errorId.innerHTML = "* required fields"
        return false;
    }
    if (!/^\d+$/.test(zip)) {
        errorId.innerHTML = "Invalid zip code"
        return false;
    }
    return true;
}

function validateRoomForm(form) {
    roomNumber = form.roomNumber.value;
    sections = form.sections.value;
    seats = form.seats.value;
    errorId = document.getElementById("errorMessage");
    if (sections == "" || seats == "") {
        errorId.innerHTML = "* required fields"
        return false;
    }
    if (!/^\d+$/.test(roomNumber) || !/^\d+$/.test(sections) || !/^\d+$/.test(seats)) {
        errorId.innerHTML = "Invalid input"
        return false;
    }
    return true;
}

function validateTicketPriceForm(form) {
    startTime = form.startTime.value;
    price = form.price.value;
    errorId = document.getElementById("errorMessage");
    if (startTime == "" || price == "") {
        errorId.innerHTML = "One or more inputs are empty"
        return false;
    }
    return true;
}