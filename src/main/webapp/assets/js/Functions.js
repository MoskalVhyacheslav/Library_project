function addOrder(title, author, id) {

    // Возвращает содержимое  XMLHttpRequest
    var req = newXMLHttpRequest();

    // Оператор для получения сообщения обратного вызова
    // из объекта запроса;
    req.onreadystatechange = function () {
        if (req.readyState = 4) {
            var row = document.getElementById(id);
            row.parentNode.removeChild(row);
        }
    };

    var body = 'title=' + title +
        '&author=' + author;

    // Открываем HTTP-соединение с помощью POST-метода к
    //сервлету корзины покупателя.
    // Третий параметр определяет, что запрос  асинхронный.
    req.open("POST", "http://localhost:8080/controller?command=addOrder", true);

    // Определяет, что в содержимом запроса есть данные
    req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    req.send(body);
}


function deleteOrder(title, author, id) {
    var req = newXMLHttpRequest();

    req.onreadystatechange = function () {
        if (req.readyState = 4) {
            var row = document.getElementById(id);
            row.parentNode.removeChild(row);
        }
    };

    var body = 'title=' + title +
        '&author=' + author;

    // Открываем HTTP-соединение с помощью POST-метода к
    //сервлету корзины покупателя.
    req.open("POST", "http://localhost:8080/controller?command=payDeleteOrder", true);
    req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    req.send(body);
}


function deleteLibrarian(login, id) {

    // Возвращает содержимое  XMLHttpRequest
    var req = newXMLHttpRequest();

    // Оператор для получения сообщения обратного вызова
    // из объекта запроса

    req.onreadystatechange = function () {
        if (req.readyState = 4) {
            var row = document.getElementById(id);
            row.parentNode.removeChild(row);
        }
    };

    var body = 'login=' + login;

    // Открываем HTTP-соединение с помощью POST-метода к
    //сервлету корзины покупателя.
    // Третий параметр определяет, что запрос  асинхронный.
    req.open("POST", "http://localhost:8080/controller?command=deleteLibrarian", true);

    // Определяет, что в содержимом запроса есть данные
    req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    req.send(body);
}


function unblockUser(login, id) {

    // Возвращает содержимое  XMLHttpRequest
    var req = newXMLHttpRequest();

    // Оператор для получения сообщения обратного вызова
    // из объекта запроса
    req.onreadystatechange = function () {
        if (req.readyState = 4) {
            var row = document.getElementById(id);
            row.parentNode.removeChild(row);
        }
    };

    var body = 'login=' + login;

    // Открываем HTTP-соединение с помощью POST-метода к
    //сервлету корзины покупателя.
    // Третий параметр определяет, что запрос  асинхронный.
    req.open("POST", "http://localhost:8080/controller?command=unblockUser", true);

    // Определяет, что в содержимом запроса есть данные
    req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    req.send(body);
}


function blockUser(login, id) {

    // Возвращает содержимое  XMLHttpRequest
    var req = newXMLHttpRequest();

    // Оператор для получения сообщения обратного вызова
    // из объекта запроса
    req.onreadystatechange = function () {
        if (req.readyState = 4) {
            var row = document.getElementById(id);
            row.parentNode.removeChild(row);
        }
    };

    var body = 'login=' + login;

    // Открываем HTTP-соединение с помощью POST-метода к
    //сервлету корзины покупателя.
    // Третий параметр определяет, что запрос  асинхронный.
    req.open("POST", "http://localhost:8080/controller?command=blockUser", true);

    // Определяет, что в содержимом запроса есть данные
    req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    req.send(body);
}


function updateBook(title, author, id) {
    // Возвращает содержимое  XMLHttpRequest
    var req = newXMLHttpRequest();

    // Оператор для получения сообщения обратного вызова
    // из объекта запроса
    req.onreadystatechange = function () {
        if (req.readyState = 4) {
            window.location.href="/jsp/UpdateBook.jsp";
        }
    };

    var body = 'title=' + title
        + "&author=" + author;

    // Открываем HTTP-соединение с помощью POST-метода к
    //сервлету корзины покупателя.
    // Третий параметр определяет, что запрос  асинхронный.
    req.open("POST", "http://localhost:8080/controller?command=beforeUpdateBook", true);

    // Определяет, что в содержимом запроса есть данные
    req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");

    req.send(body);
}