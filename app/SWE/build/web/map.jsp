<%-- 
    Document   : map
    Created on : 30.12.2016, 11:25:03
    Author     : janprasil
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
    <jsp:include page="header.jsp"></jsp:include>
    <h2>Mapa výsledků</h2>
        <p>V následující mapě můžeme nalézt, jakým způsobem probíhalo referendum napříč různými zeměpisnými oblastmi Velké Británie.</p>
        <div id="map" style="width:100%; height: 500px;"></div>
    <jsp:include page="footer.jsp"></jsp:include>
    <script>
        var gmaps = {};

        gmaps.map = function (mapDiv, latitude, longitude, accuracy, result) {
          "use strict";

          var createMap = function (mapDiv, coordinates) {
            var mapOptions = {
              center: coordinates,
              mapTypeId: google.maps.MapTypeId.ROADMAP,
              zoom: 5
            };

            return new google.maps.Map(mapDiv, mapOptions);
          };

          var addMarker = function (map, coordinates, x) {
            var settedIcon = (x.voteFor > x.voteAgainst) ? "marker-green.png" : "marker-red.png";
            var markerOptions = {
              clickable: false,
              map: map,
              position: coordinates,
              icon: "/SWE/" + settedIcon,
              label: ""+ Math.round(x.voteFor/x.voteAgainst * 100) / 100 +""
            };

            return new google.maps.Marker(markerOptions);
          };

          var initialize = function (mapDiv, latitude, longitude, accuracy, result) {
            var coordinates = new google.maps.LatLng(latitude, longitude);
            var map = createMap(mapDiv, coordinates);
            var markers = result.map(function(x, key) {
               var coordinates = new google.maps.LatLng(x.latitude, x.longitude);
               return addMarker(map, coordinates, x) 
            });
            var markerCluster = new MarkerClusterer(map, markers, 
            {imagePath: 'https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/m'});
            
          };

          initialize(mapDiv, latitude, longitude, accuracy, result);
        };

        $(document).ready(function () {
          "use strict";           
            $.ajax({
                url: "/SWE/referendum-map",
                success: function( result ) {
                    var map = gmaps.map($("#map")[0], 52.6, 0.0, 70, result);
                }
            });
        });
    </script>
    <script src="https://developers.google.com/maps/documentation/javascript/examples/markerclusterer/markerclusterer.js">
    </script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAjaIhUTRkxxDC5UEjrtQs1S9jQ1z_XdIg"
    async defer></script>
