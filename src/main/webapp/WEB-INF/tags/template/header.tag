<%@ include file="/WEB-INF/tags/customtaglibs.jspf" %>
<div class="header">
    <div class="logo">
        <a href=""><img src="${pageContext.request.contextPath}/resources/pages/images/serilogo.png" alt="" /></a>
    </div>

    <div class="headerinner">
        <ul class="headmenu">
            <li class="odd">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                    <span class="count">4</span>
                    <span class="head-icon head-message"></span>
                    <span class="headmenu-label">Messages</span>
                </a>
                <ul class="dropdown-menu">
                    <li class="nav-header">Messages</li>
                    <li><a href=""><span class="icon-envelope"></span> New message from <strong>Jack</strong> <small class="muted"> - 19 hours ago</small></a></li>
                    <li><a href=""><span class="icon-envelope"></span> New message from <strong>Daniel</strong> <small class="muted"> - 2 days ago</small></a></li>
                    <li><a href=""><span class="icon-envelope"></span> New message from <strong>Jane</strong> <small class="muted"> - 3 days ago</small></a></li>
                    <li><a href=""><span class="icon-envelope"></span> New message from <strong>Tanya</strong> <small class="muted"> - 1 week ago</small></a></li>
                    <li><a href=""><span class="icon-envelope"></span> New message from <strong>Lee</strong> <small class="muted"> - 1 week ago</small></a></li>
                    <li class="viewmore"><a href="messages.html">View More Messages</a></li>
                </ul>
            </li>

            <li>
                <a class="dropdown-toggle" data-toggle="dropdown" data-target="#">
                    <span class="count">${fn:length(notificationList)}</span>
                    <span class="head-icon head-notification"></span>
                    <span class="headmenu-label">New Notification</span>
                </a>
                <ul class="dropdown-menu newusers">
                    <li class="nav-header">New Notifications</li>
                    <c:forEach items="${notificationList}" var="notification">
                        <li>
                            <a href="">
                                <%--<img src="${pageContext.request.contextPath}/resources/pages/images/photos/thumb1.png" alt="" class="userthumb" />--%>
                                <strong>
                                	<utilLibs:propertyFetcher key="${notification.notificationType}" />  
                                	<small>Due Date:
                                    	<fmt:formatDate value="${notification.dueDate}" var="startFormat" pattern="yyyy-MM-dd"/>
                                    ${startFormat}
                               		 </small>
                                </strong>
                                <%-- <small>Due Date:
                                    <fmt:parseDate value="${notification.dueDate}" pattern="yyyy-MM-dd HH:mm:ss" var="myDate"/>
                                    <fmt:formatDate value="${notification.dueDate}" var="startFormat" pattern="yyyy-MM-dd"/>
                                    ${startFormat}
                                </small> --%>
                                <span class="notiDesc">${notification.description}</span>
                            </a>
                        </li>
                    </c:forEach>


                </ul>
            </li>
            <li class="odd">
                <a class="dropdown-toggle" data-toggle="dropdown" data-target="#">
                    <span class="count">1</span>
                    <span class="head-icon head-bar"></span>
                    <span class="headmenu-label">Statistics</span>
                </a>
                <ul class="dropdown-menu">
                    <li class="nav-header">Statistics</li>
                    <li><a href=""><span class="icon-align-left"></span> New Reports from <strong>Products</strong> <small class="muted"> - 19 hours ago</small></a></li>
                    <li><a href=""><span class="icon-align-left"></span> New Statistics from <strong>Users</strong> <small class="muted"> - 2 days ago</small></a></li>
                    <li><a href=""><span class="icon-align-left"></span> New Statistics from <strong>Comments</strong> <small class="muted"> - 3 days ago</small></a></li>
                    <li><a href=""><span class="icon-align-left"></span> Most Popular in <strong>Products</strong> <small class="muted"> - 1 week ago</small></a></li>
                    <li><a href=""><span class="icon-align-left"></span> Most Viewed in <strong>Blog</strong> <small class="muted"> - 1 week ago</small></a></li>
                    <li class="viewmore"><a href="charts.html">View More Statistics</a></li>
                </ul>
            </li>
            <li class="right">
                <div class="userloggedinfo">
                    <img src="${pageContext.request.contextPath}/resources/pages/images/photos/thumb1.png" alt="" />
                    <div class="userinfo">
                        <h5><utilLibs:getLoginDetails propName="name" /> <small>- <utilLibs:getLoginDetails propName="email" /> </small></h5>
                        <ul>
                            <li><a href="/updateprofile">Edit Profile</a></li>
                            <li><a href="">Account Settings</a></li>
                            <li><a href="/logout">Sign Out</a></li>
                        </ul>
                    </div>
                </div>
            </li>
        </ul><!--headmenu-->
    </div>
</div>