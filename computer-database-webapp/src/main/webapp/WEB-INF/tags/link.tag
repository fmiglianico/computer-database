<%@ tag %>

<%@ taglib uri="WEB-INF/cdblib" prefix="cdblib" %>

<%@ attribute name="wrap" required="true" type="org.springframework.data.domain.Page" %>
<%@ attribute name="search" required="false" type="java.lang.String" %>
<%@ attribute name="orderBy" required="false" type="com.formation.computerdb.core.common.OrderBy" %>

<!-- overloaded parameters -->
<%@ attribute name="nbrows" required="false" type="java.lang.Integer" %>
<%@ attribute name="currpage" required="false" type="java.lang.Integer" %>
<%@ attribute name="newOrderBy" required="false" type="java.lang.String" %>
<%@ attribute name="tagclass" required="false"%>

<a class="${tagclass}" href="${cdblib:generateLink(currpage == null ? wrap.number + 1 : currpage, 
																	nbrows == null ? wrap.size : nbrows, 
																	search, 
																	newOrderBy == null ? (orderBy == null ? null : orderBy.colName) : newOrderBy, 
																	newOrderBy == null ? null : (orderBy == null ? null : orderBy.getDirForCol(newOrderBy))
																  )}">
	<jsp:doBody/>
</a>