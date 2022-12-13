<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Chat</title>
        <link rel="stylesheet" href="css/chat.css">
        <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    </head>
    <body>
<div class="container bootstrap snippets bootdey main-chat">
    <nav class="navbar navbar-inverse">
  <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>                        
      </button>
      <a class="navbar-brand" href="#">Logo</a>
    </div>
    <div class="collapse navbar-collapse" id="myNavbar">
      <ul class="nav navbar-nav">
        <li class="active"><a href="#">Home</a></li>
        <li><a href="chat.jsp">Chat</a></li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
        <li><a href="#"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
      </ul>
    </div>
  </div>
</nav>
    My Language : <input type="text" value="en" id="tl">
            <div class="chat-message">
                <ul class="chat" id="chat-list">
                    <li class="left clearfix">
                    	<div class="chat-body clearfix">
                    		<div class="header">
                    			<strong class="primary-font">John Doe</strong>
                    		</div>
                    		<p>
                    			Hello
                    		</p>
                    	</div>
                    </li>
                    <li class="right clearfix">
                    	<div class="chat-body clearfix">
                    		<div class="header">
                    			<strong class="primary-font">Sarah</strong>
                    		</div>
                    		<p>
                    			Good morning
                    		</p>
                    	</div>
                    </li>
                </ul>
            </div>
            <div class="chat-box">
            	<div class="input-group">
            		<input class="form-control border no-shadow no-rounded" id="msg" placeholder="Type your message here">
            		<span class="input-group-btn">
                            <button class="btn btn-success no-rounded" onclick="sendMsg()" type="button">Send</button>
            		</span>
            	</div>	    
</div>
    <script type="text/javascript">
    var msg = document.getElementById("msg").value;
    var wsUrl;
    if (window.location.protocol === 'http:') {
        wsUrl = 'ws://';
    } else {
        wsUrl = 'wss://';
    }
    var ws = new WebSocket("ws://localhost:9999/BiLingo/Chat");//wsUrl + window.location.host + "/Chat");
        
    ws.onmessage = function(event) {
        if ( msg == event.data ) return;
      var mySpan = document.getElementById("chat-list");
      $.post("http://127.0.0.1:8888/", {
                            text: event.data,
                            source: "auto",
                            target: document.getElementById("tl").value,
                        }, function(data) {
                            console.log(data);
                            mySpan.innerHTML += `<li class="left clearfix">
                    	<div class="chat-body clearfix" title="`+event.data+`">
                    		<div class="header">
                    			<strong class="primary-font">John Doe</strong>
                    		</div>
                    		<p>`+
                    			data
                    		+`</p>
                    	</div>
                    </li>`;
                        });
      //let x = document.createElement("p");
      //x.setAttribute("class", "received-msg");
      
      //x.textContent = event.data;
      //mySpan.appendChild(x);
      //mySpan.innerHTML+=event.data+"<br/>";
    };
     
    ws.onerror = function(event){
        console.log("Error ", event);
    } 
    function sendMsg() {
        msg = document.getElementById("msg").value;
        if(msg)
        {
            ws.send(msg);
            var mySpan = document.getElementById("chat-list");
            mySpan.innerHTML += `<li class="right clearfix">
                    	<div class="chat-body clearfix" title="${tr}">
                    		<div class="header">
                    			<strong class="primary-font">John Doe</strong>
                    		</div>
                    		<p>`+
                    			msg
                    		+`</p>
                    	</div>
                    </li>`;
            //let x = document.createElement("p");
            //x.setAttribute("class", "sent-msg");
            //x.textContent = msg;
            //mySpan.appendChild(x);
        }
        document.getElementById("msg").value="";
    }
    $(document).ready(function(){
        $("#msg").keyup(function(event){ 
            if ( event.originalEvent.code === "Enter" )
                sendMsg();
        });
    });
</script>
    </body>
</html>