function showResults(val, endpoint, name) {
    res = document.getElementsByName(name);
    res.innerHTML = '';
    if (val === '') {
        return;
    }
    let list = '';
    fetch('/' + endpoint + '?q=' + val).then(
        function (response) {
            return response.json();
        }).then(function (data) {
        for (i = 0; i < data.length; i++) {
            list += '<li>' + data[i] + '</li>';
        }
        res.innerHTML = '<ul>' + list + '</ul>';
        return true;
    }).catch(function (err) {
        return false;
    });
}