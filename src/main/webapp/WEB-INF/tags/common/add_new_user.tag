<%@ include file="/WEB-INF/tags/customtaglibs.jspf" %>
<script>$(document).ready(function(){
    user.roleChangerEvent();
});</script>
<c:choose>
    <c:when test="${emailErr==true}">
        <c:set var="emailErrClass" scope="request" value="error" />
    </c:when>
</c:choose>
<div class="widget">
    <h4 class="widgettitle">Form Elements</h4>
    <div class="widgetcontent">
        <form class="stdform" action="/addadmin" name="userForm" method="post">
            <div class="par control-group ${emailErrClass}">
                <label for="login" class="control-label">Email Id</label>
                <div class="controls">
                    <input type="text" class="span4 " id="email" name="email" value="${userForm.email}" placeholder="Enter Email Id">
                    <span class="help-inline">${emailErrrrMsg}</span>
                </div>
            </div><!--par-->

            <div class="par control-group">
                <label for="fName" class="control-label">First Name</label>
                <div class="controls">
                    <input type="text" class="span4" id="fName" name="fName" value="${userForm.fName}" placeholder="Enter First Name">
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <div class="par control-group">
                <label for="mName" class="control-label">Middle Name</label>
                <div class="controls">
                    <input type="text" class="span4" id="mName" name="mName" value="${userForm.mName}" placeholder="Enter Middle Name">
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <div class="par control-group">
                <label for="lName" class="control-label">Last Name</label>
                <div class="controls">
                    <input type="text" class="span4" id="lName" name="lName" value="${userForm.lName}" placeholder="Enter Last Name">
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <div class="par control-group">
                <label for="dob" class="control-label">Date of Birth</label>
                <div class="controls">
                    <input type="text" class="span4 date-field" id="dob" name="dob" value="${userForm.dob}" placeholder="Enter Date of Birth">
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <p>
                <label>Gender</label>
                <span class="formwrapper">
                    <input type="radio" name="gender" value="MALE" checked /> Male &nbsp; &nbsp;
                    <input type="radio" name="gender" value="FEMALE" /> Female
                </span>
            </p>

            <div class="par control-group">
                <label class="control-label">Select Role</label>
                <div class="controls">
                    <select name="role" id="roleSelect" class="uniformselect">
                        <option value="">Make a selection</option>
                        <commonLib:roleOptionDecider />
                    </select>
                    <span class="help-inline"></span>
                </div>
            </div>

            <p for="ROLE_SCHOOL_ADMIN" class="roleDepender control-group">
                <label class="control-label" data-supErrMsg="Please proceed after adding new school">Select School <em>(When role SCHOOL_ADMIN)</em></label>
                <span class="formwrapper">
                    <schoolLibs:schoolListWithoutPrincipal />
                </span>
            </p>

            <p for="ROLE_STUDENT" class="roleDepender control-group">
                <label class="control-label" data-supErrMsg="Please proceed after adding new school">Select School <em>(When role STUDENT)</em></label>
                <span class="formwrapper">
                    <schoolLibs:activeSchoolList ctrlName="stuSchoolId" />
                </span>
            </p>

            <p for="ROLE_STUDENT" class="roleDepender control-group">
                <label class="control-label" data-supErrMsg="Please proceed after adding new standard">Select Standard <em>(When role Student)</em></label>
                <span class="formwrapper">
                    <standardLibs:standardList ctrlName="stuStandardId" selectedStandard="0" />
                </span>
            </p>

            <p for="ROLE_TEACHER" class="roleDepender control-group">
                <label class="control-label" data-supErrMsg="Please proceed after adding new school">Select School <em>(When role TEACHER)</em></label>
                <span class="formwrapper">
                    <schoolLibs:activeSchoolList ctrlName="teacherSchoolId" />
                </span>
            </p>

            <p for="ROLE_TEACHER" class="roleDepender control-group">
                <label class="control-label" data-supErrMsg="Please proceed after adding new standard">Select Standard <em>(When role TEACHER)</em></label>
                <span class="formwrapper">
                    <schoolLibs:schoolStandardCtrl ctrlName="teacherStandardId" ctrlClass="teacherStandardId span4" ctrlId="teacherStandardId" multi="true" selectedStandard="0" />
                    <%--<standardLibs:standardList ctrlName="teacherStandardId" multi="true" selectedStandard="0" />--%>
                </span>
            </p>

            <p for="ROLE_PARENT" class="roleDepender control-group">
                <label class="control-label" data-supErrMsg="Please proceed after adding new Student">Select Student <em>(When role PARENT)</em></label>
                <span class="formwrapper">
                    <studentLibs:studentListWothoutParentProfile ctrlName="studentId" />
                </span>
            </p>

            <p for="ROLE_HOD" class="roleDepender control-group">
                <label class="control-label" data-supErrMsg="Please proceed after adding new school">Select School <em>(When role HOD)</em></label>
                <span class="formwrapper">
                    <schoolLibs:activeSchoolList ctrlName="hodSchoolId" selectedSchool="${hodForm.hodSchoolId}" />
                </span>
            </p>

            <p for="ROLE_HOD" class="roleDepender control-group">
                <label class="control-label" data-supErrMsg="Please proceed after adding new Department">Select Department <em>(When role HOD)</em></label>
                <span class="formwrapper">
                    <utilLibs:getDepartmentList ctrlName="hodDepartmentId" selectedDepartment="${hodForm.hodDepartmentId}" ctrlClass="hodDepartmentId" ctrlId="hodDepartmentId" />
                </span>
            </p>

            <p class="control-group">
                <label class="control-label">Status</label>
                    <span class="formwrapper">
                        <input type="radio" name="status" value="1" checked="checked" /> Active &nbsp; &nbsp;
                        <input type="radio" name="status" value="0" /> In-Active
                    </span>
            </p>

            <p class="stdformbutton">
                <button class="btn btn-primary">Submit Button</button>
                <button type="reset" class="btn">Reset Button</button>
            </p>

        </form>
    </div><!--widgetcontent-->
</div><!--widget-->