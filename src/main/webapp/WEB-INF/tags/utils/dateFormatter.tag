<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="utilsLibs" prefix="utilLibs" %>
<%@ attribute name="dateVar" description="this field store client id" %>
<fmt:parseDate value="${dateVar}" pattern="yyyy-MM-dd HH:mm:ss" var="parsedDate" />
<fmt:formatDate value="${parsedDate}" pattern="yyyy-MM-dd" />
