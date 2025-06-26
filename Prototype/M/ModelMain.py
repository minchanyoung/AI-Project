from Network import ClientConnection


hostIP = "127.0.0.1"# "192.168.51.23"
port = 8989

clientConnection = ClientConnection(hostIP, port)

while(True):
    clientConnection.process()