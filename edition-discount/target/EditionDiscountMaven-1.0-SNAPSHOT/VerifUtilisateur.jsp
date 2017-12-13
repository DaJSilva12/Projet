<%-- 
    Document   : VerifUtilisateur
    Created on : 15 nov. 2017, 16:16:24
    Author     : nbrugie
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Login </title>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="styleLogin.css" />

    </head>
    <body>
		<div class="login" style="color:red">${errorMessage} </div>

                        
                     <div id="login">
                         
                        <form class="form-container">
                            <div class="form-title"><h2>Sign up</h2></div>
                            <div class="form-title">Email</div>
                            <input class="form-field" type="text" name="loginParam" /><br />
                                <div class="form-title">Password</div>
                            <input class="form-field" type="text" name="passwordParam" /><br />
                            <div class="submit-container">
                                <input class="submit-button" name='action'type="submit" value="login" />
                            </div>
                        </form>
                     </div>
		<!-- On montre le nombre d'utilisateurs connectés -->
		<!-- Cette information est stockée dans le scope "application" par le listener -->
        <div><h4>${message}</h4></div>
    </body>
</html>
