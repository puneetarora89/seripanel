<%@ include file="/WEB-INF/tags/customtaglibs.jspf" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/pages/js/responsive-tables.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/pages/js/jquery.dataTables.min.js"></script>

<script type="text/javascript">
    $(document).ready(function(){
        // dynamic table
        $('#dyntable').dataTable({
            "sPaginationType": "full_numbers",
            "aaSortingFixed": [[0,'asc']],
            "fnDrawCallback": function(oSettings) {
                jQuery.uniform.update();
            }
        });

rating.studentListing();
    });
</script>
<div class="row-fluid">
<div class="">
<h4 class="">${subjectForm.subjectName}</h4>
<br>
    <form class="stdform" action="${formAction}" method="post">
<sec:authorize access="hasRole('ROLE_TEACHER')">
	<p>
        <label>Select Standard</label>
                <span class="field">
                    <standardLibs:standardList ctrlName="standardId" standardIds="${tempStandardId}" selectedStandard="${empty selectedStandard ? 0 : selectedStandard}" />
                </span>
    </p>
    <p>
        <label>Select Subject</label>
                <span class="field">
                    <standardLibs:subjectListInQuery ids="${tempStandardId}" ctrlName="subjectId" ctrlId="customSubjectId" selectedSubject="${empty selectedSubject ? 0 : selectedSubject}" />
                </span>
    </p>
                <p>
                <label>Task Date</label>
                <span class="field"><input type="text" name="taskDate" id="taskDate" class="input-large date-field" placeholder="Select Date" /></span>
            </p>
</sec:authorize>
</form>

<div id="dataTable"></div>

<div aria-hidden="false" aria-labelledby="syllabusContent" role="dialog" tabindex="-1" class="modal hide fade in" id="syllabusContent">
    <div class="modal-header">
        <button aria-hidden="true" data-dismiss="modal" class="close" type="button">&times;</button>
        <h3 id="myModalLabel" class="addUpdateSubTitle">Add Subject</h3>
    </div>
    <div class="msg-container"></div>
    <div class="modal-body">
    </div>
    <div class="modal-footer">
        <button data-dismiss="modal" class="btn">Close</button>
    </div>
</div>

</div></div>