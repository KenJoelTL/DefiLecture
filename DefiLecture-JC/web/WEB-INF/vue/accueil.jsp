<h1>ACCUEIL</h1>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


       <form method="POST" action=".do" enctype="multipart/form-data">
            File:
            <input type="file" name="nomFichier" id="file" /> <br/>
            <input type="hidden" name="tache" value="ajouterImage" />
            <input type="hidden" name="idCompte" value="${sessionScope.connecte}" />
            <input type="submit" value="Upload" name="upload" id="upload" />
        </form>
            <span>${requestScope.test}</span>
            <span><%=this.getServletContext().getRealPath("/image/avatars/AvatarCompte_1")%></span>
            <!--
            <div style="background-image: url('C:/Users/Joel/Desktop/data.jpg')">                
            </div>
            <div style="background-image: url('./build/web/image/avatars/ImageDe1')">                
            </div>-->
            <div style="background-image: url('<%=this.getServletContext().getRealPath("/image/avatars/AvatarCompte_1")%>')">                
            </div>
            <div style="background-image: url('<c:url value='/image/avatars/AvatarCompte_1'/>')">                
            </div>