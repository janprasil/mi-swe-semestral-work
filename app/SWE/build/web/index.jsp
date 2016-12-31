<%-- 
    Document   : index
    Created on : 29.12.2016, 17:01:31
    Author     : janprasil
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
    <jsp:include page="header.jsp"></jsp:include>
    <h2>Semestrální práce MI-SWE</h2>
    <p>Vítejte na stránce semestrální práce předmětu MI-SWE.</p>
    <p>Cílem této semestrální práce je seznámit se s výsledky referenda o odchodu Velké Británie z Evropské Unie. Tato webová prezentace obsahuje vizualizované odpovědi na 3 SPARQL dotazy.</p>
    <h3>Mapa hlasování</h3>
    <p>Pomocí mapy hlasování můžeme nalézt, jakým způsobem probíhalo referendum napříč různými zeměpisnými oblastmi Velké Británie.
        <br/>
        <a href="/SWE/map.jsp">Mapu můžete zobrazit pod tímto odkazem</a>
    </p>
    <h3>Rozdíly v hlasování</h3>
    <p>Pomocí slideru můžete nastavit poměr hlasování PRO ku PROTI odchodu. Výsledkem je tabulka s městy, kde byl dodržen stejný nebo vyšší poměr. Zároveň je možné tento poměr seřadit od největšího či nejmenšího
        <br/>
        <a href="/SWE/difference.jsp">Tabulku můžete zobrazit pod tímto odkazem</a>
    </p>
    <h3>Vliv ekonomického hodnocení na volbu v referendu</h3>
    <p>Cílem bylo zjistit, zda-li má ekonomická situace města vliv na volbu v hlasování PRO či PROTI. Výsledkem je graf, na jehož ose x jsou zaznamenány ekonomické hodnocení a na ose y pak poměr volby PRO:PROTI pro město s daným ekonomickým hodnocením.
       <br />
       <a href="/SWE/results.jsp">Graf můžete zobrazit pod tímto odkazem</a>
    </p>
<jsp:include page="footer.jsp"></jsp:include>
