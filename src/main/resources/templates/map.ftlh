<#import '*/page.ftlh' as u>
<#import '*/pagination.ftlh' as a>

<@u.page>
    <html>
    <head>
        <title>Расчет стоимости доставки</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <script src="https://api-maps.yandex.ru/2.1/?lang=ru_RU&amp;apikey=2012c5e7-7832-4ee9-9ae8-ebabe046f0be" type="text/javascript"></script>
        <style>
            html, body, #map {
                width: 100%;
                height: 100%;
                padding: 0;
                margin: 0;
            }
        </style>
    </head>
    <body>
    <div id="map"></div>
    </body>
    </html>
</@u.page>

<script lang="text/javascript">
    ymaps.ready(init);

    function init() {
        // Стоимость за километр.
        var DELIVERY_TARIFF = 20,
            // Минимальная стоимость.
            MINIMUM_COST = 500,
            myMap = new ymaps.Map('map', {
                center: [60.906882, 30.067233],
                zoom: 9,
                controls: []
            }),
            // Создадим панель маршрутизации.
            routePanelControl = new ymaps.control.RoutePanel({
                options: {
                    // Добавим заголовок панели.
                    showHeader: true,
                    title: 'Расчёт доставки'
                }
            }),
            zoomControl = new ymaps.control.ZoomControl({
                options: {
                    size: 'small',
                    float: 'none',
                    position: {
                        bottom: 145,
                        right: 10
                    }
                }
            });
        // Пользователь сможет построить только автомобильный маршрут.
        routePanelControl.routePanel.options.set({
            types: {auto: true}
        });

        // Если вы хотите задать неизменяемую точку "откуда", раскомментируйте код ниже.
        routePanelControl.routePanel.state.set({
            fromEnabled: false,
            from: 'Москва, Льва Толстого 16'
         });

        myMap.controls.add(routePanelControl).add(zoomControl);

        // Получим ссылку на маршрут.
        routePanelControl.routePanel.getRouteAsync().then(function (route) {

            // Зададим максимально допустимое число маршрутов, возвращаемых мультимаршрутизатором.
            route.model.setParams({results: 1}, true);

            // Повесим обработчик на событие построения маршрута.
            route.model.events.add('requestsuccess', function () {

                var activeRoute = route.getActiveRoute();
                if (activeRoute) {
                    // Получим протяженность маршрута.
                    var length = route.getActiveRoute().properties.get("distance"),
                        // Вычислим стоимость доставки.
                        price = calculate(Math.round(length.value / 1000)),
                        // Создадим макет содержимого балуна маршрута.
                        balloonContentLayout = ymaps.templateLayoutFactory.createClass(
                            '<span>Расстояние: ' + length.text + '.</span><br/>' +
                            '<span style="font-weight: bold; font-style: italic">Стоимость доставки: ' + price + ' р.</span>');
                    // Зададим этот макет для содержимого балуна.
                    route.options.set('routeBalloonContentLayout', balloonContentLayout);
                    // Откроем балун.
                    activeRoute.balloon.open();
                }
            });

        });
        // Функция, вычисляющая стоимость доставки.
        function calculate(routeLength) {
            return Math.max(routeLength * DELIVERY_TARIFF, MINIMUM_COST);
        }
    }
</script>