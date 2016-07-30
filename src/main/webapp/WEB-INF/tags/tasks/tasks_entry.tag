<script type="text/javascript" src="${pageContext.request.contextPath}/resources/pages/js/tinymce/jquery.tinymce.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/pages/js/wysiwyg.js"></script>
<%@ include file="/WEB-INF/tags/customtaglibs.jspf" %>
<form class="stdform" action="${formAction}" method="post">
    <c:set var="hidSubId" value="0" />
    <c:choose>
        <c:when test="${not empty syllabusForm.subjectId && syllabusForm.subjectId>0}">
            <c:set var="hidSubId" value="${syllabusForm.subjectId}" />
        </c:when>
    </c:choose>

<script>
    $(document).ready(function(){
        syllabus.content();
        utils.schoolSubsSelector("standardId", "subjectIdContainer", "subjectId", "subjectId", "subjectId", "${subjectId}");
        tasks.schoolChange();tasks.standardChange();tasks.subjectChange();tasks.taskNameChange();tasks.teacherCustomSubjectChange();
    });
</script>

    <input type="hidden" value="${syllabusForm.taskId}" id="taskId" name="taskId1">
    <input type="hidden" value="${schoolId}" id="hidSchoolId">
    <input type="hidden" value="${standardId}" id="hidStandardId">
    <input type="hidden" value="${subjectId}" id="hidSubjectId">
    <div class="row-fluid">
        <div class="span6">
            <sec:authorize access="hasRole('ROLE_SUP_ADMIN')">
                <p>
                <label>Select School</label>
                <span class="field">
                        <schoolLibs:activeSchoolList ctrlName="schoolId" selectedSchool="${schoolId}" />
                </span>
                </p>
            </sec:authorize>

            <sec:authorize access="hasRole('ROLE_TEACHER')">
                <p>
                <label>Select Subject</label>
                <span class="field">
                    <input type="hidden" name="standardId" id="standardId">
                    <standardLibs:subjectListInQuery ids="${tempStandardId}" ctrlName="subjectId" ctrlId="customSubjectId" selectedSubject="${subjectId}" />
                </span>
                </p>
            </sec:authorize>

            <sec:authorize access="hasRole('ROLE_SUP_ADMIN') or hasRole('ROLE_SCHOOL_ADMIN')">
                <p>
                    <label>Select Standard</label>
                <span class="field">
                    <standardLibs:standardList ctrlName="standardId" multi="false" selectedStandard="${standardId}" />
                </span>
                </p>

                <p>
                    <label>Select Subject</label>
                <span class="field" id="subjectIdContainer">
                    Select Standard First
                </span>
                </p>
            </sec:authorize>

            <p>
                <label>Select Task Type</label>
                <span class="field" id="">
                    <select name="taskName" id="taskName">
                        <option value="">Make a selection</option>
                        <option value="CLASS_WORK">Class Work</option>
                        <option value="HOME_WORK">Home Work</option>
                    </select>
                </span>
            </p>

            <p>
                <label>Due Date</label>
                <span class="field"><input type="text" name="taskDueDate" id="syllabusDueDate" value="${syllabusForm.taskDueDate}" class="input-large date-field" placeholder="Select Date" /></span>
            </p>
        </div>


        <sec:authorize access="hasRole('ROLE_TEACHER')">
            <div class="span6 student-evaludation-container hide">
                <p>
                    <script>
                        $(document).ready(function(){
                            student.studentSelectCtrlBuilder("#customSubjectId", "#standardStudentCtrl", "");rating.createRating();
                        });
                    </script>
                    <label>Select Student</label>
                    <span class="field" id="standardStudentCtrl">
                        Select Subject First
                    </span>
                </p>
                <p>
                    <label>Assign Rating</label>
                    <span class="field" id="rating">
                        <a href="#" class="student-rate-a promptbutton"> <span class="badge badge-important">1</span></a>
                        <a href="#" class="student-rate-a promptbutton"> <span class="badge badge-warning">2</span></a>
                        <a href="#" class="student-rate-a promptbutton"> <span class="badge">3</span></a>
                        <a href="#" class="student-rate-a promptbutton"> <span class="badge badge-info">4</span></a>
                        <a href="#" class="student-rate-a promptbutton"> <span class="badge badge-success">5</span></a>
                    </span>
                </p>
                <p><label>&nbsp;</label><span class="rating-msg-container"></span> </p>


            </div>
        </sec:authorize>

    </div>

    <div>
        <textarea id="elm1" name="content" rows="15" cols="80" style="width: 80%" class="tinymce">
            &lt;p&gt;
                ${syllabusForm.content}
            &lt;/p&gt;
        </textarea>
    </div>
    <br />
    <button type="submit" name="submit" class="btn btn-primary">Submit</button>
    <button type="reset" name="reset" class="btn resetBtn">Reset</button>
</form>