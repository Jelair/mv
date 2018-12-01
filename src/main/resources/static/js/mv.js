var stompClient = null;

function connect() {
    var socket = new SockJS('/mv-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log(frame);
        var subscribePath = '/topic/online/'+$("#fetchVideoId").text();
        stompClient.subscribe(subscribePath, function (online_user_number) {
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

function getPlayNum() {
    var getUrl = '/api/playNum/' + $("#fetchVideoId").text();
    $.ajax({
        url: getUrl,
        dataType: 'json',
        type: 'GET',
        async: true,
        success: function (res) {
            $('#playNum').text(res.playNum);
            console.log(res);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log('error')
        }
    });
}

$(function () {
    //$( "#connect" ).click(function() { connect(); });
    connect();
    getPlayNum();
    $( "#sendBtn" ).click(function() { sendContent(); });
});