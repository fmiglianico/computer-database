<%@ tag %>

<%@ taglib uri="WEB-INF/cdblib" prefix="cdblib" %>

<%@ attribute name="page2" required="false" type="java.lang.Integer" %>
<%@ attribute name="max" required="false" type="java.lang.Integer" %>
<%@ attribute name="search" required="false"%>
<%@ attribute name="orderby" required="false"%>
<%@ attribute name="dir" required="false"%>
<%@ attribute name="tagclass" required="false"%>

<a class="${tagclass}" href="${cdblib:generateLink(page2, max, search, orderby, dir)}">
	<jsp:doBody/>
</a>