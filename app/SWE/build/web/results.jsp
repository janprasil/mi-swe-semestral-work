<%-- 
    Document   : referendumResults
    Created on : 29.12.2016, 17:13:20
    Author     : janprasil
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
    <jsp:include page="header.jsp"></jsp:include>
    <h2>Závislost ekonomické situace města na hlasování v referendu</h2>
    <p>Na následujícím grafu si můžete prohlédnout, zda-li měla ekonomické situace měst vliv na rozhování občanů v referendu o odchodu Velké Británie z Evropské Unie.</p>
            <h3>Osa x</h3>
            <p>Na ose x jsou zaznamenány ekonomická hodnocení - tj od minima až po maximum ve Velké Británii</p>
            <h3>Osa y</h3>
            <p>Osa y nese hodnoty poměru hlasů PRO:PROTI pro město s daným ekonomickým hodnocením vyznačeným na ose x.</p>
        <canvas id="myChart" width="400" height="400"></canvas>
<jsp:include page="footer.jsp"></jsp:include>
        <script>
            var data = [];
            $.ajax({
                url: "/SWE/referendum-results",
                success: function( result ) {
                    var myData = result.reduce(function(prev, cur) {
                            prev.push({ x: cur.rank, y: cur.voteRatio });
                            return prev;
                        }, []);
                    var nextData = result.sort(function(a,b) { return a.rank - b.rank }).map(function(x, key) {
                        return x.voteRatio;
                    });
                    console.log(myData);
                    var ctx = document.getElementById("myChart");
                    var myChart = new Chart(ctx, {
                        type: 'line',
                        data: {
                            datasets: [{
                                    label: 'main dataset',
                                    data: myData.sort(function(a,b) { return a.x - b.x }),
                            }]
                        },
                        options: {
                                scales: {
                                    xAxes: [{
                                        type: 'linear',
                                        position: 'bottom'
                                    }]
                                }
                            }
                    });
                }
              });


</script>
