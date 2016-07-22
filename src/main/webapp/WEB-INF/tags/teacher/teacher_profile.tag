<%@ include file="/WEB-INF/tags/customtaglibs.jspf" %>
<c:choose>
    <c:when test="${emailErr==true}">
        <c:set var="emailErrClass" scope="request" value="error" />
    </c:when>
</c:choose>
<div class="widget">
    <h4 class="widgettitle">Teacher Profile</h4>
    <div class="widgetcontent">
        <form class="stdform" action="/${formAction}" name="teacherForm" method="post">
            <input type="hidden" name="tLoginId" value="${userForm.id}" />
            <input type="hidden" name="p" value="${param.p}">
            <input type="hidden" name="rpp" value="${param.rpp}">
            <div class="par control-group ${emailErrClass}">
                <label for="email" class="control-label">Email Id</label>
                <div class="controls">
                    <input type="text" class="span4 " id="email" name="email" readonly="readonly" value="${userForm.email}" placeholder="Enter Email Id">
                    <span class="help-inline">${emailErrrrMsg}</span>
                </div>
            </div><!--par-->

            <div class="par control-group">
                <label for="fName" class="control-label">First Name</label>
                <div class="controls">
                    <input type="text" class="span4" id="fName" name="fName" readonly="readonly" value="${userForm.fName}" readonly="readonly" placeholder="Enter First Name">
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <div class="par control-group">
                <label for="mName" class="control-label">Middle Name</label>
                <div class="controls">
                    <input type="text" class="span4" id="mName" name="mName" readonly="readonly" value="${userForm.mName}" readonly="readonly" placeholder="Enter Middle Name">
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <div class="par control-group">
                <label for="lName" class="control-label">Last Name</label>
                <div class="controls">
                    <input type="text" class="span4" id="lName" name="lName" readonly="readonly" value="${userForm.lName}" readonly="readonly" placeholder="Enter Last Name">
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <div class="par control-group">
                <label for="dob" class="control-label">Date of Birth</label>
                <div class="controls">
                    <input type="text" class="span4" id="dob" name="dob" value="<fmt:formatDate pattern="yyyy-MM-dd" 
            value="${userForm.dob}" />" readonly="readonly" placeholder="Enter Date of Birth">
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <p>
                <label>Gender</label>
                <span class="formwrapper">
                <c:choose>
                	<c:when test="${userForm.gender == 'MALE'}">
                    	<input type="radio" name="gender" value="MALE" checked="checked" disabled="disabled"/> Male &nbsp; &nbsp;
                    	<input type="radio" name="gender" value="FEMALE" disabled="disabled"/> Female
                	</c:when>
                	<c:otherwise>
                    	<input type="radio" name="gender" value="MALE" disabled="disabled"/> Male &nbsp; &nbsp;
                    	<input type="radio" name="gender" value="FEMALE" checked="checked" disabled="disabled"/> Female
                	</c:otherwise>
                </c:choose>
                </span>
            </p>

            <p>
                <label>Standard</label>
                <span class="formwrapper">
                    <script>utils.multiValuePreSelect("teacherStandardId", "${teacher.teacherStandardId}")</script>
                    <schoolLibs:schoolStandardCtrl ctrlName="teacherStandardId" ctrlClass="teacherStandardId span4" ctrlId="teacherStandardId" multi="true" selectedStandard="0" />
                    <%--<standardLibs:standardList ctrlName="teacherStandardId" multi="true" selectedStandard="0" />--%>
                </span>
            </p>

            <p>
                <label>Describe yourself</label>
                <span class="field"><textarea style="resize: vertical; height: 100px;" name="tDescription" class="span5" rows="5" cols="80" id="autoResizeTA">${teacher.tDescription}</textarea></span>
            </p>

            <div class="par control-group">
                <label for="tNationality" class="control-label">Nationality</label>
                <div class="controls">
                    <input type="text" class="span4" id="tNationality" name="tNationality" value="${teacher.tNationality}" placeholder="Enter Nationality">
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <div class="par control-group">
                <label for="tPersonalEmail" class="control-label">Personal Email</label>
                <div class="controls">
                    <input type="text" class="span4" id="tPersonalEmail" name="tPersonalEmail" value="${teacher.tPersonalEmail}" placeholder="Enter Personal Email">
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <p>
                <label>Complete Home Address</label>
                <span class="field"><textarea style="resize: vertical; height: 100px;" name="tHomeAdress" class="span5" rows="5" cols="80" id="tHomeAdress">${teacher.tHomeAdress}</textarea></span>
            </p>

            <div class="par control-group">
                <label for="tPhNo" class="control-label">Phone No</label>
                <div class="controls">
                    <input type="text" class="span4" id="tPhNo" name="tPhNo" value="${teacher.tPhNo}" placeholder="Enter Phone No">
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <div class="par control-group">
                <label for="tMobNo" class="control-label">Mobile No</label>
                <div class="controls">
                    <input type="text" class="span4" id="tMobNo" name="tMobNo" value="${teacher.tMobNo}" placeholder="Enter Mobile No">
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <div class="par control-group">
                <label for="tExperiance" class="control-label">Total Teaching Experience</label>
                <div class="controls">
                    <input type="text" class="span4" id="tExperiance" name="tExperiance" value="${teacher.tExperiance}" placeholder="Enter Total Teaching Experience">
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <div class="par control-group">
                <label for="tSecMarks" class="control-label">10th Percentage</label>
                <div class="controls">
                    <input type="text" class="span4" id="tSecMarks" name="tSecMarks" value="${teacher.tSecMarks}" placeholder="Enter 10th Percentage">
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <div class="par control-group">
                <label for="tSenSecMarks" class="control-label">12th Percentage</label>
                <div class="controls">
                    <input type="text" class="span4" id="tSenSecMarks" name="tSenSecMarks" value="${teacher.tSenSecMarks}" placeholder="Enter 12th Percentage">
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <p>
                <label>Graduation Subjects</label>
                <span class="field"><textarea style="resize: vertical; height: 100px;" name="tGradMetadata" class="span5" rows="5" cols="80" id="tGradMetadata">${teacher.tGradMetadata}</textarea></span>
            </p>

            <p>
                <label>Post Graduation Subjects</label>
                <span class="field"><textarea style="resize: vertical; height: 100px;" name="tPgMetadata" class="span5" rows="5" cols="80" id="tPgMetadata">${teacher.tPgMetadata}</textarea></span>
            </p>

            <p>
                <label>Any Professional Course</label>
                <span class="field"><textarea style="resize: vertical; height: 100px;" name="tOthQualificationMetadata" class="span5" rows="5" cols="80" id="tOthQualificationMetadata">${teacher.tOthQualificationMetadata}</textarea></span>
            </p>

            <div class="par control-group">
                <label for="tSpecialization" class="control-label">Specialization</label>
                <div class="controls">
                    <input type="text" class="span4" id="tSpecialization" name="tSpecialization" value="${teacher.tSpecialization}" placeholder="Enter Specialization">
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <div class="par control-group">
                <label for="tOthSpecialization" class="control-label">Other subject specialization</label>
                <div class="controls">
                    <input type="text" class="span4" id="tOthSpecialization" name="tOthSpecialization" value="${teacher.tOthSpecialization}" placeholder="Enter Other subject specialization">
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <p>
                <label>Teaching Catagory</label>
                <span class="field">
                <select name="tCategory" class="uniformselect">
                    <option value="">Choose One</option>
                    <option value="primary" ${(teacher.tOthSpecialization eq 'primary')?'selected':''}>Primary</option>
                    <option value="secondary" ${(teacher.tOthSpecialization eq 'secondary')?'selected':''}>Secondary</option>
                    <option value="senior-secondary" ${(teacher.tOthSpecialization eq 'senior-secondary')?'selected':''}>Senior Secondary</option>
                </select>
                </span>
            </p>

            <div class="par control-group">
                <label for="tDeptName" class="control-label">Department Name</label>
                <div class="controls">
                    <input type="text" class="span4" id="tDeptName" name="tDeptName" value="${teacher.tDeptName}" placeholder="Enter Department Name">
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <div class="par control-group">
                <label for="tDesignation" class="control-label">Designation</label>
                <div class="controls">
                    <input type="text" class="span4" id="tDesignation" name="tDesignation" value="${teacher.tDesignation}" placeholder="Enter Designation">
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <div class="par control-group">
                <label for="tOthSkills" class="control-label">Other Skills<br><small>Like singing/Dancing/Any sport</small></label>
                <div class="controls">
                    <input type="text" class="span4" id="tOthSkills" name="tOthSkills" value="${teacher.tOthSkills}" placeholder="Enter Other Skills">
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <div class="par control-group">
                <label for="tJoiningDate" class="control-label">Joining Date (DD/MM/YYYY)</label>
                <div class="controls">
                    <input type="text" class="span4 date-field" id="tJoiningDate" name="tJoiningDate" value="<utilLibs:dateFormatter dateVar='${teacher.tJoiningDate}'/>" placeholder="Enter Joining Date">
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <div class="par control-group">
                <label for="tLangSpeak" class="control-label">Language Know (Speak)</label>
                <div class="controls">
                    <input type="text" class="span4" id="tLangSpeak" name="tLangSpeak" value="${teacher.tLangSpeak}" placeholder="Enter Language Know (Speak)">
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <div class="par control-group">
                <label for="tLangWrite" class="control-label">Language Know (Written)</label>
                <div class="controls">
                    <input type="text" class="span4" id="tLangWrite" name="tLangWrite" value="${teacher.tLangWrite}" placeholder="Enter Language Know (Written)">
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <div class="par control-group">
                <label for="tOthLang" class="control-label">Any other language</label>
                <div class="controls">
                    <input type="text" class="span4" id="tOthLang" name="tOthLang" value="${teacher.tOthLang}" placeholder="Enter Any other language">
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <div class="par control-group">
                <label for="tRef1Metadata" class="control-label">References 1</label>
                <div class="controls">
                    <input type="text" class="span4" id="tRef1Metadata" name="tRef1Metadata" value="${teacher.tRef1Metadata}" placeholder="Enter References 1">
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <div class="par control-group">
                <label for="tRef2Metadata" class="control-label">References 2</label>
                <div class="controls">
                    <input type="text" class="span4" id="tRef2Metadata" name="tRef2Metadata" value="${teacher.tRef2Metadata}" placeholder="Enter References 2">
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <p>
                <label>Detail of last organisation</label>
                <span class="field"><textarea style="resize: vertical; height: 100px;" name="tLastOrgMetadata" class="span5" rows="5" cols="80" id="tLastOrgMetadata">${teacher.tLastOrgMetadata}</textarea></span>
            </p>

            <p class="stdformbutton">
                <button class="btn btn-primary">Submit Button</button>
                <button type="reset" class="btn">Reset Button</button>
            </p>

        </form>
    </div><!--widgetcontent-->
</div><!--widget-->