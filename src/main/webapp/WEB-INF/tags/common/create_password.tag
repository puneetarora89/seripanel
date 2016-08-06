<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="widget">
    <h4 class="widgettitle">Form Elements</h4>
    <div class="widgetcontent">
        <form class="stdform" action="/addpassword" name="userForm" method="post">
            <input type="hidden" name="passwordToken" value="${param.token}" />

            <div class="par control-group">
                <label for="email" class="control-label">Email Id</label>
                <div class="controls">
                    <input type="text" class="span4 " id="email" name="email" readonly="readonly" value="${userDetails.email}" placeholder="Enter Email Id">
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <div class="par control-group">
                <label for="password" class="control-label">Enter New Password</label>
                <div class="controls">
                    <input type="password" class="span4" id="password" name="password" placeholder="Enter Password">
                    <span class="help-inline"></span>
                </div>
            </div><!--par-->

            <div class="par control-group">
                <label for="cpassword" class="control-label">Confirm Your Password</label>
                <div class="controls">
                    <input type="password" class="span4" id="cpassword" name="cpassword" placeholder="Enter Password again">
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