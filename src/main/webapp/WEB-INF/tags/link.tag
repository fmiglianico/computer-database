<%@ tag %>

<%@ taglib uri="WEB-INF/cdblib" prefix="cdblib" %>

<%@ attribute name="wrap" required="true" type="com.formation.computerdb.domain.Page" %>
<%@ attribute name="nbrows" required="false" type="java.lang.Integer" %>
<%@ attribute name="currpage" required="false" type="java.lang.Integer" %>
<%@ attribute name="orderby" required="false" type="java.lang.String" %>
<%@ attribute name="tagclass" required="false"%>

<a class="${tagclass}" href="${cdblib:generateLink(currpage == null ? wrap.currPage : currpage, 
																	nbrows == null ? wrap.nbRows : nbrows, 
																	wrap.search, 
																	orderby == null ? wrap.orderBy.colName : orderby, 
																	orderby == null ? null : wrap.orderBy.getDirForCol(orderby) 
																  )}">
	<jsp:doBody/>
</a>