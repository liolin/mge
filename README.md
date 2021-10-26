# mge
MGE Miniproject

Ein LAN Messanger App, welche über Websockets mit einem Server spricht. Um den Fokus auf die Entwicklung der App zu legen, habe ich mich entschieden, als Server ein Echo-Server auf Websockets-Basis zu verwenden. Dadurch kann die App mit einem Server kommunizieren und erhält aber auch eine Nachricht zurück.

- WebSocketServer: [ws-echo-server](https://github.com/sunny-zuo/ws-echo-server)
- WebSocketLibrary: [Java-Websocket](https://github.com/TooTallNate/Java-WebSocket)
- JSON Serialisierung: [Gson](https://github.com/google/gson)

## Punkteverteilung
Features 
- [X] Verwendung von Kotlin statt Java (3Pt)
- [X] Verwendung von Notifications (1-2Pt)
- [X] Verwendung von Webservices / Drittkomponenten (Libraries), Websockets und Gson (1-3Pt)
- [X] Persistenz (Speichern von Settings) (1-3Pt)


Implementierung:
- [X] Observer Pattern für Notification / Rendering, wenn neue Nachricht erhalten


## Images
Start Actvity 
![Start Screen](img/start_screen.png)


Settings Activity
![Settings Screen](img/settings_screen.png)


Chat Activity
![Chat Screen](img/chat_screen.png)


Notification
![Notification](img/notification.png)
