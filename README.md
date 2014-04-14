В решении данной задачи было принято:
1) Структура состоит из коллекции HashMap ключами которой являются имя приложения, а значениями коллекция HashMap ключами которой являются uuid инстанса, а значение остальные данные.
2) При получении нового статуса с уже зарегестрированным UUID. Данные перезаписываются.
3) Сервис принимает статус в формате JSON.
 

Варианты запросов для регистрации параметров:
{"name":"WAVE","uuid":"7b2f75b2-d17a-4e38-8b8f-1","state":"UP","cpu":73,"timeWork":44,"quantity":47} 
{"name":"TEXT","uuid":"7b2f75b2-d17a-4e38-8b8f-11","state":"UP","cpu":22,"timeWork":15,"quantity":11} 
{"name":"WAVE","uuid":"7b2f75b2-d17a-4e38-8b8f-5","state":"UP","cpu":45,"timeWork":45,"quantity":49} 
{"name":"WAVE","uuid":"7b2f75b2-d17a-4e38-8b8f-6","state":"STARTING...","cpu":1,"timeWork":68,"quantity":11} 
{"name":"APPLET","uuid":"7b2f75b2-d17a-4e38-8b8f-4","state":"STARTING...","cpu":13,"timeWork":90,"quantity":28} 
{"name":"TEXT","uuid":"7b2f75b2-d17a-4e38-8b8f-12","state":"UP","cpu":59,"timeWork":25,"quantity":2} 
{"name":"APPLET","uuid":"7b2f75b2-d17a-4e38-8b8f-6","state":"STARTING...","cpu":20,"timeWork":38,"quantity":49} 
{"name":"TEXT","uuid":"7b2f75b2-d17a-4e38-8b8f-11","state":"UP","cpu":14,"timeWork":80,"quantity":29} 
{"name":"WAVE","uuid":"7b2f75b2-d17a-4e38-8b8f-10","state":"UP","cpu":20,"timeWork":80,"quantity":12} 
{"name":"APPLET","uuid":"7b2f75b2-d17a-4e38-8b8f-5","state":"STARTING...","cpu":48,"timeWork":85,"quantity":44} 
{"name":"TEXT","uuid":"7b2f75b2-d17a-4e38-8b8f-1","state":"UP","cpu":5,"timeWork":7,"quantity":13} 
{"name":"TEXT","uuid":"7b2f75b2-d17a-4e38-8b8f-13","state":"UP","cpu":57,"timeWork":74,"quantity":36} 
{"name":"APPLET","uuid":"7b2f75b2-d17a-4e38-8b8f-9","state":"STARTING...","cpu":89,"timeWork":30,"quantity":14} 
{"name":"APPLET","uuid":"7b2f75b2-d17a-4e38-8b8f-7","state":"STARTING...","cpu":48,"timeWork":85,"quantity":39} 
{"name":"WAVE","uuid":"7b2f75b2-d17a-4e38-8b8f-3","state":"UP","cpu":59,"timeWork":91,"quantity":49} 
{"name":"WAVE","uuid":"7b2f75b2-d17a-4e38-8b8f-0","state":"UP","cpu":29,"timeWork":89,"quantity":43} 
{"name":"WAVE","uuid":"7b2f75b2-d17a-4e38-8b8f-12","state":"UP","cpu":22,"timeWork":53,"quantity":19} 
{"name":"APPLET","uuid":"7b2f75b2-d17a-4e38-8b8f-9","state":"STARTING...","cpu":54,"timeWork":7,"quantity":21} 
{"name":"TEXT","uuid":"7b2f75b2-d17a-4e38-8b8f-0","state":"UP","cpu":11,"timeWork":42,"quantity":7} 
{"name":"TEXT","uuid":"7b2f75b2-d17a-4e38-8b8f-8","state":"UP","cpu":13,"timeWork":33,"quantity":37} 

Варианты запросов GET:
Список приложений от менее к более загруженным
MyTest/Service3?sort=date

Список приложений с CPU в заданном интервале
MyTest/Service3?sort=cpu&minCpu=13&maxCpu=57

