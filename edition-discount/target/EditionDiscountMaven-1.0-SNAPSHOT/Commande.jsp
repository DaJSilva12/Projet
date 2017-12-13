<%-- 
    Document   : Commande
    Created on : 17 nov. 2017, 15:11:41
    Author     : nbrugie
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Commande Client</title>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="description" content="Popup en CSS3 et JS pour le blog webdesignweb.fr" />
        <meta name="keywords" content="modal, window, overlay, modern, box, css transition, css animation " />
         <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
        <meta name="viewport" content="width=device-width, initial-scale=1.0"> 
        <script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
        <title>JSP Page</title>
        <link rel="stylesheet" href="style.css" />


        
    </head>
    <body>
       
        
        
        <div id= "element">
        <h1>Vos Commande</h1>
                <div>
			<table border="3" class="greenTable">
                            <tr><th>Nom du Produit</th><th>Numero de la commande</th><th>Quantité</th><th>Prix Total</th><th>Date Achat</th><th>Supprimer</th><th>Modifier</th></tr>
				<c:forEach var="record" items="${commandeClient}" >
                                    <form method="GET">
                                    <tr>
                                        <td>${record.orderNum}
                                             <input type="hidden" value="${record.orderNum}" name="orderNum"></td>
					<td>${record.description}</td>       
                                        <td><input id ="quantité" class="form-field" name="quantiteModif" size="7" value="${record.quantité}"></td>
                                        <td>${record.prix}</td>
                                        <td>${record.dateAchat}</td>

                                               <td><a href="?action=DELETE&orderNum=${record.orderNum}">Supprimer</a></td>
                                        <td>

                                                <input type="hidden" name="action" value="MODIFIER"> 
                                                <input type="submit" value="MODIFIER">
                                                
                                            
                                        </td>

                                    </tr>	
                                    </form>
				</c:forEach>

			</table>
                   
		</div>
       
        
        <h1>Produits</h1>
        <div>
			<table border="3" class="blueTable">
                            <tr><th>Nom du Produit</th><th>Prix</th><th>Id</th><th>Quantité</th></tr> 
				<c:forEach var="record" items="${ListeProduit}">
                                    <form>
					<tr>

						<td>${record.description}</td>

						<td >${record.prix}</td>

                                                <td>${record.id}</td>

                                                <td>
                                                    <input id ="quantité" class="form-field" name="quantite" size="7" >
                                                </td>
                                                <td>
                                                 <input type="hidden" value="${record.description}" name="description">
                                                 <input type="hidden" value="${record.id}" name="id" >

                                                 <input name="action" value="AJOUTER" type="hidden"> 
                                                 <button type="submit" action="AJOUTER">AJOUTER</button>
                                                </td>
					</tr>
                                     </form>

				</c:forEach>  
			</table>
		</div>
        
        </div>
        <div class="overlay"></div>
        <form action="${pageContext.request.contextPath}/Logout" method="post">
                    <input type="submit" value="Logout" />
        </form>
        
    </body>
</html>
