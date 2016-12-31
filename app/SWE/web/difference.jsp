<%-- 
    Document   : difference
    Created on : 30.12.2016, 22:28:29
    Author     : janprasil
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="header.jsp"></jsp:include>
            <h2>Rozdíly v hlasování</h2>
            <p>Pomocí slideru můžete nastavit poměr hlasování PRO ku PROTI odchodu. Výsledkem je tabulka s městy, kde byl dodržen stejný nebo vyšší poměr. Zároveň je možné tento poměr seřadit od největšího či nejmenšího</p>
            <h3>Slider</h3>
            <p>Pomocí slideru můžete nastavit poměr hlasů PRO:PROTI</p>
            <div id="slider">
                <div id="custom-handle" class="ui-slider-handle"></div>
            </div>
            <h3>Řazení</h3>
            <p>Zde můžete seřadit zobrazení výsledků buď od nejmenšího či naopak od největšího poměru.</p>
            <select>
                <option value="desc">Descending</option>
                <option value="asc">Ascending</option>
            </select>
            <h3>Výsledky</h3>
            <p>Tabulka s výsledky</p>
            <table id="table" border='1'>
                <thead>
                    <tr>
                        <th>Town</th>
                        <th>Vote For</th>
                        <th>Vote Against</th>
                    </tr>
                </thead>
                <tbody id="table-body">
                    
                </tbody>
            </table>
            
    <jsp:include page="footer.jsp"></jsp:include>
    <script>
        
        $( function() {
            
            var getData = function(ratio, sort) {
                $.ajax({
                    url: "/SWE/ReferendumVotesDifferenceServlet?ratio="+ ratio + "&sort=" + sort,
                    success: function( result ) {
                        var tableData = result.reduce(function(prev, current) {
                            var row = "<tr><td>"+ current.town +"</td><td>"+ current.voteFor +"</td><td>"+ current.voteAgainst +"</td></tr>"
                            return prev + row;
                        }, "");
                        $('#table-body').html(tableData);
                    }
                });
            }
            
            $('select').on('change', function() {
                getData( $("#slider").slider( "value" ), this.value );
            });
            
            var handle = $( "#custom-handle" );
            $("#slider" ).slider({
                min: 0.0,
                max: 4.0,
                step: 0.2,
                create: function() {
                    handle.text( $( this ).slider( "value" ) );
                    getData("0.0", $('select').value);
                },
                slide: function( event, ui ) {
                    handle.text( ui.value );
                },
                change: function(event, ui) {
                    getData(ui.value, $('select').value);
                }
            });
        });
    </script>
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
