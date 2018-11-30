var stompClient = null;

function connect() {
    var socket = new SockJS('/mv-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log(frame);
        stompClient.subscribe('/topic/online', function (online_user_number) {
            $("#online_num").text(JSON.parse(online_user_number.body));
        });
        stompClient.subscribe('/topic/common_message', function (greeting) {
            //showGreeting(JSON.parse(greeting.body).content);
            console.log(greeting)
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
}

function sendContent() {
    stompClient.send("/mv/danmu", {}, JSON.stringify({'emitContent': $("#emitContent").val()}));
}

$(function () {
    //$( "#connect" ).click(function() { connect(); });
    connect();
    $( "#sendBtn" ).click(function() { sendContent(); });
});