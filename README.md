# Frontend/App
<!-- TOC -->
* [Frontend/App](#frontendapp)
  * [Netzwerk](#netzwerk)
    * [`WebSocketClient`](#websocketclient)
    * [`PostOffice`](#postoffice)
    * [`ResponseReceiver`](#responsereceiver)
<!-- TOC -->

---
## Netzwerk
### `WebSocketClient`
Wird für die Herstellung der Client <-> Server Verbindung benötigt und für die Kommunikation
zwischen beiden Endpunkten.

Methoden:
* `public void connectToServer(PostOffice messageHandler)`
    * `public void onOpen(@NonNull WebSocket webSocket, @NonNull Response response)`
    * `public void onMessage(@NonNull WebSocket webSocket, @NonNull String text)`
    * `public void onClosing(@NonNull WebSocket webSocket, int code, @NonNull String reason)`
    * `public void onClosed(@NonNull WebSocket webSocket, int code, @NonNull String reason)`
    * `public void onFailure(@NonNull WebSocket webSocket, @NonNull Throwable t, @Nullable Response response)`
* `public void sendMessageToServer(JSONObject msg)`
* `public void disconnectFromServer() throws Throwable`
* `private boolean checkResponse(Response response)`



### `PostOffice`
Zuständig für das Verteilen der erhaltenen Nachrichten vom Server über den key action. Dient als 
message handler. Ruft die implementierte Methode des Interface ResponseReceiver in der View auf und
übergibt an diese die Nachricht vom Server.

Methoden:
* `public void routeResponse(JSONObject response)`



### `ResponseReceiver`
Interface das eine Methode enthält welche in der View in der eine Nachricht zum Server geschickt
wird und ein Response erwartet wird implementiert werden muss. Diese Methode beinhaltet die Logik
die die Nachricht vom Server verarbeitet.

Methoden:
* `void receiveResponse(JSONObject response) throws JSONException`

---
