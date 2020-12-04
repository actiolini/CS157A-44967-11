function loadMovieInfo(movieId, title, releaseDate, duration, trailer, description) {
    document.getElementById("movieId").setAttribute('value', movieId);
    document.getElementById("title").setAttribute('value', title);
    document.getElementById("releaseDate").setAttribute('value', releaseDate);
    document.getElementById("duration").setAttribute('value', duration);
    document.getElementById("trailer").setAttribute('value', trailer);
    document.getElementById("description").innerHTML = description;
}