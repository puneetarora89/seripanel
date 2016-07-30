<%@ include file="/WEB-INF/tags/customtaglibs.jspf" %>
<h4 class="widgettitle">Data Table</h4>
    <table id="dyntable" class="table table-bordered responsive">
        <colgroup>
            <col class="con0" style="align: center; width: 4%" />
            <col class="con1" />
            <col class="con0" />
            <col class="con1" />
            <col class="con0" />
            <col class="con1" />
        </colgroup>
        <thead>
        <tr>
            <th class="head0 nosort"><input type="checkbox" class="checkall" /></th>
            <th class="head0">Student Name</th>
            <th class="head1">Task Name</th>
            <th class="head0">View Task Details</th>
            <th  hidden="true">rate id</th>
            <th class="head1">Comment</th>
            <th class="head0">Assign Rating</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="ratetask" items="${ratingTaskList}">
        	<tr class="gradeX">
	            <td class="aligncenter"><span class="center"><input type="checkbox" /></span></td>
	            <td>${ratetask.studentName}</td>
	            <td>${ratetask.taskType}</td>
	            <%-- <c:when test="${ratetask.status != 'PENDING'}"> --%>
	            <td><a class="syllabusContent" data-toggle="modal" data-id="${ratetask.syllabusId}" href="#syllabusContent">View Task Details</a></td>
	            <%-- </c:when> --%>
	            <%-- <c:otherwise>
	            	<td><span class="center">No Work Submitted</span></td>
	            </c:otherwise> --%>
	            <td id="rateId" hidden="true">${ratetask.rateId}</td>
	            <td class="center" id="comment">${ratetask.comment}</td>
	            <td class="center"><span class="field" id="rating">
	            			<c:if test="${ratetask.rate == 0 }">
		                        <a href="#" class="student-rate-a promptbutton"> <span class="badge badge-important blank-rating">1</span></a>
		                        <a href="#" class="student-rate-a promptbutton"> <span class="badge badge-warning blank-rating">2</span></a>
		                        <a href="#" class="student-rate-a promptbutton"> <span class="badge blank-rating">3</span></a>
		                        <a href="#" class="student-rate-a promptbutton"> <span class="badge badge-info blank-rating">4</span></a>
		                        <a href="#" class="student-rate-a promptbutton"> <span class="badge badge-success blank-rating">5</span></a>
	                        </c:if>
	                        <c:if test="${ratetask.rate == 1 }">
		                        <a href="#" class="student-rate-a promptbutton"> <span class="badge badge-important">1</span></a>
		                        <a href="#" class="student-rate-a promptbutton"> <span class="badge badge-warning blank-rating">2</span></a>
		                        <a href="#" class="student-rate-a promptbutton"> <span class="badge blank-rating">3</span></a>
		                        <a href="#" class="student-rate-a promptbutton"> <span class="badge badge-info blank-rating">4</span></a>
		                        <a href="#" class="student-rate-a promptbutton"> <span class="badge badge-success blank-rating">5</span></a>
	                        </c:if>
	                        <c:if test="${ratetask.rate == 2 }">
		                        <a href="#" class="student-rate-a promptbutton"> <span class="badge badge-important">1</span></a>
		                        <a href="#" class="student-rate-a promptbutton"> <span class="badge badge-warning">2</span></a>
		                        <a href="#" class="student-rate-a promptbutton"> <span class="badge blank-rating">3</span></a>
		                        <a href="#" class="student-rate-a promptbutton"> <span class="badge badge-info blank-rating">4</span></a>
		                        <a href="#" class="student-rate-a promptbutton"> <span class="badge badge-success blank-rating">5</span></a>
	                        </c:if>
	                        <c:if test="${ratetask.rate == 3 }">
		                        <a href="#" class="student-rate-a promptbutton"> <span class="badge badge-important">1</span></a>
		                        <a href="#" class="student-rate-a promptbutton"> <span class="badge badge-warning">2</span></a>
		                        <a href="#" class="student-rate-a promptbutton"> <span class="badge">3</span></a>
		                        <a href="#" class="student-rate-a promptbutton"> <span class="badge badge-info blank-rating">4</span></a>
		                        <a href="#" class="student-rate-a promptbutton"> <span class="badge badge-success blank-rating">5</span></a>
	                        </c:if>
	                        <c:if test="${ratetask.rate == 4 }">
		                        <a href="#" class="student-rate-a promptbutton"> <span class="badge badge-important">1</span></a>
		                        <a href="#" class="student-rate-a promptbutton"> <span class="badge badge-warning">2</span></a>
		                        <a href="#" class="student-rate-a promptbutton"> <span class="badge">3</span></a>
		                        <a href="#" class="student-rate-a promptbutton"> <span class="badge badge-info">4</span></a>
		                        <a href="#" class="student-rate-a promptbutton"> <span class="badge badge-success blank-rating">5</span></a>
	                        </c:if>
	                        <c:if test="${ratetask.rate == 5 }">
		                        <a href="#" class="student-rate-a promptbutton"> <span class="badge badge-important">1</span></a>
		                        <a href="#" class="student-rate-a promptbutton"> <span class="badge badge-warning">2</span></a>
		                        <a href="#" class="student-rate-a promptbutton"> <span class="badge">3</span></a>
		                        <a href="#" class="student-rate-a promptbutton"> <span class="badge badge-info">4</span></a>
		                        <a href="#" class="student-rate-a promptbutton"> <span class="badge badge-success">5</span></a>
	                        </c:if>
	                    </span>
	            </td>
        	</tr>
        </c:forEach>
        
        </tbody>
    </table>