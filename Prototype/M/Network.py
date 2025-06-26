import socket
from ConnectOllama import chatAPI

class ClientConnection:
    __client_socket = None
    __hostIP = "127.0.0.1"# "192.168.51.23"
    __port = 8989

    def __init__(self, hostIP, port):
        self.__hostIP = hostIP
        self.__port = port
        
        self.client_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.client_socket.connect((hostIP, port))

    def get_client_socket(self):
        return self.__client_socket
    
    def process(self):
        response = self.client_socket.recv(1024).decode("utf-8")
        print("전송")
        LLManswer = chatAPI(response)
        print(LLManswer)

        self.client_socket.send((LLManswer + "\n").encode("utf-8"))

