<!DOCTYPE html>
<html>
<%@ include file="/WEB-INF/tags/customtaglibs.jspf" %>
<body>
<design:head />
<div class="mainwrapper">
    <%--<design:header />--%>

    <design:navigation />

    <div class="rightpanel">

        <design:breadcrumbs />

        <design:pageheader />

        <!--pageheader-->

        <div class="maincontent">

            <div class="maincontentinner">

                <commonLib:createPassword />

                <design:footer /><!--footer-->

            </div><!--maincontentinner-->

        </div><!--maincontent-->

    </div><!--rightpanel-->

</div><!--mainwrapper-->
</body>
</html>
