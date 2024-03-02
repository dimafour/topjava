<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
const i18n = {};
i18n["addTitle"] = "<spring:message code='${param.model}.add'/>";
i18n["editTitle"] = "<spring:message code='${param.model}.edit'/>";
<c:forEach var="key"
           items='${["common.deleted","common.saved","common.enabled","common.disabled","common.errorStatus","common.confirm"]}'>
    i18n["${key}"] = "<spring:message code="${key}"/>";
</c:forEach>