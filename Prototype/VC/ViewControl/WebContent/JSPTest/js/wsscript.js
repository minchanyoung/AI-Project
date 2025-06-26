
var webSocket = null;

function onopen(){
	console.log("onopen");
}
function onclose(){
	console.log("onclose");
}
function onerror(){
	console.log("onerror");
}
function onMessage(evt) {
	console.log("onMessage - " + evt);
	
}

function connect(){
	webSocket = new WebSocket("ws://localhost:8181/ViewControl/websocket");

	var messageTextArea = document.getElementById("messageTestArea");
	
	webSocket.onopen = onopen;
	webSocket.onclose = onclose;
	webSocket.onerror = onerror;
}
function disconnect(){
	console.log("disconnect");
}

function send(){
	var msg = document.getElementById("msg");
	websocket.send(msg);
	document.getElementById("msg").value = "";
}

