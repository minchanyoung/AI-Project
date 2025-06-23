import socket
from ConnectOllama import chatAPI

hostIP = "192.168.51.23"
port = 8989

client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
client_socket.connect((hostIP, port))

while(True):
    response = client_socket.recv(1024).decode("utf-8")
    print("전송")
    LLManswer = chatAPI(response)
    print(LLManswer)

    client_socket.send((LLManswer + "\n").encode("utf-8"))