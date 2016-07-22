<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/tags/customtaglibs.jspf" %>

<c:choose>
    <c:when test="${emailErr==true}">
        <c:set var="emailErrClass" scope="request" value="error" />
    </c:when>
</c:choose>
<div class="widget">
    <h4 class="widgettitle">Student Profile</h4>
    <div class="widgetcontent">
        <form class="stdform" action="/${formAction}" name="studentForm" method="post">
            <input type="hidden" name="userId" value="${userForm.id}" />
            <input type="hidden" name="studentId" value="${studentForm.studentId}" />
            <input type="hidden" name="stuSchoolId" value="${studentForm.stuSchoolId}" />
            <input type="hidden" name="p" value="${param.p}">
            <input type="hidden" name="rpp" value="${param.rpp}">
            <div class="par control-group ${emailErrClass}">
                <label for="login" class="control-label">Email Id</label>
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
                    <input type="text" class="span4" id="dob" name="dob" value="${userForm.dob}" readonly="readonly" placeholder="Enter Date of Birth">
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <p>
                <label>Gender</label>
                <span class="formwrapper">
                    <c:choose>
                        <c:when test="${userForm.gender.equals('male')}">
                            <input type="radio" name="gender" value="MALE" checked disabled="disabled" /> Male &nbsp; &nbsp;
                            <input type="radio" name="gender" value="FEMALE" disabled /> Female
                        </c:when>
                        <c:otherwise>
                            <input type="radio" name="gender" value="MALE" disabled /> Male &nbsp; &nbsp;
                            <input type="radio" name="gender" value="FEMALE" checked disabled="disabled" /> Female
                        </c:otherwise>
                    </c:choose>

                </span>
            </p>

            <div class="par control-group">
                <label for="birthPlace" class="control-label">Birth Place</label>
                <div class="controls">
                    <input type="text" class="span4" id="birthPlace" name="birthPlace" value="${studentForm.birthPlace}" placeholder="Enter Birth Place">
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <div class="par control-group">
                <label for="hobbies" class="control-label">Hobbies</label>
                <div class="controls">
                    <input type="text" class="span4" id="hobbies" name="hobbies" value="${studentForm.hobbies}" placeholder="Enter Hobbies">
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <div class="par control-group">
                <label for="bloodGroup" class="control-label">Blood Group</label>
                <div class="controls">
                    <input type="text" class="span4" id="bloodGroup" name="bloodGroup" value="${studentForm.bloodGroup}" placeholder="Enter Blood Group">
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <div class="par control-group">
                <label for="admissionDate" class="control-label">Addmission Date</label>
                <div class="controls">
                    <input type="text" class="span4 date-field" id="admissionDate" name="admissionDate" value="${studentForm.admissionDate}" placeholder="Enter Addmission Date">
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <div class="par control-group">
                <label for="applicationNo" class="control-label">Application No</label>
                <div class="controls">
                    <input type="text" class="span4" id="applicationNo" name="applicationNo" value="${studentForm.applicationNo}" placeholder="Enter Application No">
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <div class="par control-group">
                <label for="stream" class="control-label">Stream</label>
                <div class="controls">
                    <input type="text" class="span4" id="stream" name="stream" value="${studentForm.stream}" placeholder="Enter Stream">
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <div class="par control-group">
                <label for="session" class="control-label">Session</label>
                <div class="controls">
                    <input type="text" class="span4" id="session" name="session" value="${studentForm.session}" placeholder="Enter Session">
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <div class="par control-group">
                <label for="section" class="control-label">Section</label>
                <div class="controls">
                    <input type="text" class="span4" id="section" name="section" value="${studentForm.section}" placeholder="Enter Section">
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <div class="par control-group">
                <label for="nationality" class="control-label">Nationality</label>
                <div class="controls">
                    <utilLibs:getCountryList ctrlClass="nationality" ctrlId="nationality" selectedCountry="${studentForm.nationality}" ctrlName="nationality" />
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <div class="par control-group">
                <label for="session" class="control-label">Category</label>
                <div class="controls">
                    <input type="text" class="span4" id="category" name="category" value="${studentForm.category}" placeholder="Enter Category">
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <div class="par control-group">
                <label for="religion" class="control-label">Religion</label>
                <div class="controls">
                    <input type="text" class="span4" id="religion" name="religion" value="${studentForm.religion}" placeholder="Enter Religion">
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <p>
                <label>Address</label>
                <span class="field"><textarea style="resize: vertical; height: 100px;" name="address" class="span5" rows="5" cols="80" id="address">${studentForm.address}</textarea></span>
            </p>

            <div class="par control-group">
                <label for="country" class="control-label">Country</label>
                <div class="controls">
                    <utilLibs:getCountryList ctrlClass="country" ctrlId="country" ctrlName="country" selectedCountry="${studentForm.country}" />
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <div class="par control-group">
                <label for="state" class="control-label">State</label>
                <div class="controls">
                    <input type="text" class="span4" id="state" name="state" value="${studentForm.state}" placeholder="Enter State">
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <div class="par control-group">
                <label for="city" class="control-label">City</label>
                <div class="controls">
                    <input type="text" class="span4" id="city" name="city" value="${studentForm.city}" placeholder="Enter City">
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <div class="par control-group">
                <label for="pincode" class="control-label">Pincode</label>
                <div class="controls">
                    <input type="text" class="span4" id="pincode" name="pincode" value="${studentForm.pincode}" placeholder="Enter Pincode">
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <div class="par control-group">
                <label for="mobNo" class="control-label">Mobile No</label>
                <div class="controls">
                    <input type="text" class="span4" id="mobNo" name="mobNo" value="${studentForm.mobNo}" placeholder="Enter Mobile No">
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <div class="par control-group">
                <label for="stuStandardId" class="control-label">Standard</label>
                <div class="controls">
                    <standardLibs:standardList ctrlName="stuStandardId" selectedStandard="${studentForm.stuStandardId}" />
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <div class="par control-group">
                <label for="stuSchoolId" class="control-label">School</label>
                <div class="controls">
                    <schoolLibs:schoolById ctrlName="stuSchoolId" schoolId="${studentForm.stuSchoolId}" disabled="disabled"/>
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->




            <p class="stdformbutton">
                <button class="btn btn-primary">Submit Button</button>
                <button type="reset" class="btn">Reset Button</button>
            </p>

        </form>
    </div><!--widgetcontent-->
</div><!--widget-->