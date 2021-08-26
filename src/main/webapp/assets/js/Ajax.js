/*
 * Возвращает новый XMLHttpRequest объект или false, если браузер его не поддерживает
 */
function newXMLHttpRequest() {

    var xmlreq = false;

    if (window.XMLHttpRequest) {

        // Создадим XMLHttpRequest объект для не-Microsoft браузеров
        xmlreq = new XMLHttpRequest();

    } else if (window.ActiveXObject) {

        // Создадим XMLHttpRequest с помощью MS ActiveX
        try {
            // Попробуем создать XMLHttpRequest для поздних версий
            // Internet Explorer

            xmlreq = new ActiveXObject("Msxml2.XMLHTTP");

        } catch (e1) {

            // Не удалось создать требуемый ActiveXObject

            try {
                // Пробуем вариант, который поддержат более старые версии
                //  Internet Explorer

                xmlreq = new ActiveXObject("Microsoft.XMLHTTP");

            } catch (e2) {

                // Не в состоянии создать XMLHttpRequest с помощью ActiveX
            }
        }
    }

    return xmlreq;
}

/*
 * Возвращает новый XMLHttpRequest объект или false, если браузер его не поддерживает
 */
function newXMLHttpRequest() {

    var xmlreq = false;

    if (window.XMLHttpRequest) {

        // Создадим XMLHttpRequest объект для не-Microsoft браузеров
        xmlreq = new XMLHttpRequest();

    } else if (window.ActiveXObject) {

        // Создадим XMLHttpRequest с помощью MS ActiveX
        try {
            // Попробуем создать XMLHttpRequest для поздних версий
            // Internet Explorer

            xmlreq = new ActiveXObject("Msxml2.XMLHTTP");

        } catch (e1) {
            try {
                // Пробуем вариант, который поддержат более старые версии
                //  Internet Explorer

                xmlreq = new ActiveXObject("Microsoft.XMLHTTP");

            } catch (e2) {
            }
        }
    }

    return xmlreq;
}

function getReadyStateHandler(req){

    // Возвращает неопределенную функцию, которая считывает
    // данные XMLHttpRequest return function () {

    // Если требуется статус "закончен"
    if (req.readyState == 4) {

        // Проверяем, пришел ли  успешный ответ сервера
        if (req.status == 200) {

            // Передает  XML оператору
            var row = document.getElementById(id);
            row.parentNode.removeChild(row);

        } else {
            // Возникла ошибка HTTP
            alert("HTTP error: " + req.status);
        }
    }
}




