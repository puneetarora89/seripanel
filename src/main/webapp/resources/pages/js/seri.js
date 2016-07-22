/**
 * Created by puneet on 18/04/16.
 */


var $ = jQuery.noConflict();

$(function(){
	$(".date-field").attr("autocomplete","off").datepicker({dateFormat:"yy-mm-dd"});
});

var email = {
    deleteMail: function(){
        $(".mailAction").on("click", function(e){
            var mailId = $(this).attr("data-mailId");
            if($(this).attr("data-type")=="delRecMail")
                deleteMail(mailId, "recDel");
            e.preventDefault();
        });
        function deleteMail(mailId, sentDel){
            $.ajax({
                url: "/email/delete/",
                method: "POST",
                data: {mailId: mailId, mode: "recDel"},
                async: false,
                success: function(result) {
                    $(".messagemenu").append('<h4 style="text-align: center" id="mailDelAlert" class="widgettitle title-success">Email deleted successfully!!!</h4>');
                    $("div.emailBody[data-id="+mailId+"]").remove();
                    $("ul.msglist li[data-id="+mailId+"]").remove();
                    setTimeout(function(){
                        $("#mailDelAlert").remove();
                    }, 3000);
                }
            });
        }
    },

    changeReadStatus: function(){
        $(".unread").on("click", function(){
            var emailId = $(this).attr("data-id");
            var thisVar = $(this);
            $.ajax({
                url: "/email/read/",
                method: "POST",
                data: {mailId: emailId},
                async: false,
                success: function(result) {
                    thisVar.removeClass("unread");
                }
            });
        });
    },

    replyMail: function(){
        $(".mailAction").on("click", function(e){
            var mailId = $(this).attr("data-mailId");
            if($(this).attr("data-type")=="replyMail")
            {
                var thisVar = $(this);
                var sub = "RE: "+$("div.emailBody[data-id="+mailId+"]").find("h1.subject").text();
                var msg = $("div.emailBody[data-id="+mailId+"]").find("textarea").val();
                var repliedMailId = mailId;
                var fromId = $("div.emailBody[data-id="+mailId+"]").find("span.to input").val();
                var toId = $("div.emailBody[data-id="+mailId+"]").find("span.from").text();
                $.ajax({
                    url: "/email/sendemail/",
                    method: "POST",
                    data: {subject: sub, msg: msg, repliedMailId: repliedMailId, toId: toId, fromId: fromId, isAjax: true},
                    async: false,
                    success: function(result) {
                        $(".messagemenu").append('<h4 style="text-align: center" id="mailDelAlert" class="widgettitle title-success">Email sent successfully!!!</h4>');
                        setTimeout(function(){
                            $("#mailDelAlert").remove();
                        }, 5000);
                    }
                });
            }
            e.preventDefault();
        });
    }
}

var user = {
    roleChangerEvent: function(){
        $(".roleDepender").hide();
        $("#roleSelect").change(function(){
            var flag = false;
            var thisVar = $(this);
            $(".roleDepender").hide();
            var selected = $("option:selected", this).val();
            thisVar.parents("div.control-group").removeClass("error").find("span").text("");
            $(".btn-primary").removeAttr("disabled");
            if($("p[for='"+selected+"']").length>0){
                $("p[for='"+selected+"']").each(function(){
                    if($(this).find("select option").length == 1)
                    {
                        $(".btn-primary").attr("disabled", true);
                        thisVar.parents("div.control-group").addClass("error").find("span").text($(this).find("label.control-label").attr("data-supErrMsg"));
                        flag=true;
                        return;
                    }
                });
            }

            if(!flag)
                $("p[for='"+selected+"']").slideDown();
        });
    }
}

var teacher = {

    teacherListing: function(){
        var rpp = 10;
        if(getUrlVars()['rpp'] != undefined && getUrlVars()['rpp']>0){

            rpp=getUrlVars()['rpp'];
            if(rpp.indexOf('#')>-1)
                rpp = rpp.substr(0,rpp.indexOf('#'));
            $(".recordSelector").find("option[value="+rpp+"]").attr("selected", "selected");
        }


        var p=1;
        if(getUrlVars()['p'] != undefined && getUrlVars()['p']>0)
            p=getUrlVars()['p'];

        var deptId = $("#hidDepartmentId").val();

        $(".recordSelector").change(function(e){
            rpp = $("option:selected", this).val();
            teacher.listingAjax($("#hidSchoolId").val(), deptId, 1, rpp);
            e.preventDefault();
        });
        loader.init($(".teacher-table-container"));
        teacher.listingAjax($("#hidSchoolId").val(), deptId, p, rpp);

        $(document).on("click", ".pager li a", function(e){
            if($(this).parent("li").hasClass("disabled"))
                return;
            loader.init($(".teacher-table-container"));
            var p = $(this).attr("data-p");
            if($(this).text() == 'Next')
                $(this).attr("data-p", (parseInt(p)+1))
            teacher.listingAjax($("#hidSchoolId").val(), deptId, p, rpp);
            e.preventDefault();
        });
    },

    listingAjax: function(schoolid, deptId, pageno, rpp){
        $.ajax({
            url: "/teacher/teacherlisting/",
            method: "GET",
            data: {schoolid: schoolid, deptId: deptId, p: pageno, rpp:rpp},
            async: false,
            success: function(result) {
                $(".teacher-table-container").html(result);
            }
        });
    }
}

var student = {
    studentListing: function(){
        var rpp = 10;
        if(getUrlVars()['rpp'] != undefined && getUrlVars()['rpp']>0){

            rpp=getUrlVars()['rpp'];
            if(rpp.indexOf('#')>-1)
                rpp = rpp.substr(0,rpp.indexOf('#'));
            $(".recordSelector").find("option[value="+rpp+"]").attr("selected", "selected");
        }


        var p=1;
        if(getUrlVars()['p'] != undefined && getUrlVars()['p']>0)
            p=getUrlVars()['p'];

        $(".recordSelector").change(function(e){
            rpp = $("option:selected", this).val();

            if(typeof $("#hidSchoolId").val() !== "undefined" && parseInt($("#hidSchoolId").val())>0)
                schoolTempId=$("#hidSchoolId").val();

            var params = '{ "schoolId": "'+schoolTempId+'" }';

            student.listingAjax(params, 1, rpp);
            e.preventDefault();
        });

        loader.init($(".student-table-container"));
        var schoolTempId = 0;

        if(typeof $("#hidSchoolId").val() !== "undefined" && parseInt($("#hidSchoolId").val())>0)
            schoolTempId=$("#hidSchoolId").val();

        var params = '{ "schoolId": "'+schoolTempId+'" }';
        student.listingAjax(params, p, rpp);

        $(document).on("click", ".pager li a", function(e){
            if($(this).parent("li").hasClass("disabled"))
                return;
            loader.init($(".student-table-container"));
            var p = $(this).attr("data-p");
            if($(this).text() == 'Next')
                $(this).attr("data-p", (parseInt(p)+1))

            if(typeof $("#hidSchoolId").val() !== "undefined" && parseInt($("#hidSchoolId").val())>0)
                schoolTempId=$("#hidSchoolId").val();

            var params = '{ "schoolId": "'+schoolTempId+'" }';
            student.listingAjax(params, p, rpp);
            e.preventDefault();
        });
    },
    studentSelectCtrlBuilder: function(ctrl, placeCtrl, params){
        var preCustVal = $(ctrl).children("option:selected").val();
        if(parseInt(preCustVal)>0) {
            var params = '{ "standardid": "'+preCustVal+'" }';
            student.studentListSelectCtl(params, placeCtrl);
            if($("#moduleId").children("option:selected").val()>0)
                $(".student-evaludation-container").slideDown();
        }

        $(document).on("change", ctrl, function(e){
            var standardid = 0;
            if(typeof $(this).val() !== "undefined" && parseInt($(this).val())>0)
                standardid=$(this).val();

            var params = '{ "standardid": "'+standardid+'" }';

            student.studentListSelectCtl(params, placeCtrl);
            //e.preventDefault();
        });
    },
    listingAjax: function(params, pageno, rpp){
    var stuParam = $.parseJSON(params)
        $.ajax({
            url: "/student/studentlisting/",
            method: "GET",
            data: {schoolid: stuParam.schoolid, p: pageno, rpp:rpp},
            async: false,
            success: function(result) {
                $(".student-table-container").html(result);
            }
        });
    },
    studentListSelectCtl: function(params, placeCtrl){
        var stuParam = $.parseJSON(params)
        var dataUrl = "";
        if(typeof stuParam.schoolid!=='undefined')
            dataUrl+="&schoolid="+stuParam.schoolid;

        if(typeof stuParam.standardid!=='undefined')
            dataUrl+="&standardid="+stuParam.standardid;

        $.ajax({
            url: "/student/studentselectctrl/?"+dataUrl,
            method: "GET",
            //data: {schoolid: stuParam.schoolid, p: pageno, rpp:rpp},
            async: false,
            success: function(result) {
                $(placeCtrl).html(result);
            }
        });
    }
};

var hod = {
    hodListing: function(){
        var rpp = 10;
        if(getUrlVars()['rpp'] != undefined && getUrlVars()['rpp']>0){

            rpp=getUrlVars()['rpp'];
            if(rpp.indexOf('#')>-1)
                rpp = rpp.substr(0,rpp.indexOf('#'));
            $(".recordSelector").find("option[value="+rpp+"]").attr("selected", "selected");
        }


        var p=1;
        if(getUrlVars()['p'] != undefined && getUrlVars()['p']>0)
            p=getUrlVars()['p'];

        $(".recordSelector").change(function(e){
            rpp = $("option:selected", this).val();
            hod.listingAjax($("#hidSchoolId").val(), 1, rpp);
            e.preventDefault();
        });
        loader.init($(".hod-table-container"));
        hod.listingAjax($("#hidSchoolId").val(), p, rpp);

        $(document).on("click", ".pager li a", function(e){
            if($(this).parent("li").hasClass("disabled"))
                return;
            loader.init($(".hod-table-container"));
            var p = $(this).attr("data-p");
            if($(this).text() == 'Next')
                $(this).attr("data-p", (parseInt(p)+1))
            hod.listingAjax($("#hidSchoolId").val(), p, rpp);
            e.preventDefault();
        });
    },
    listingAjax: function(schoolid, pageno, rpp){
        $.ajax({
            url: "/hod/hodlisting/",
            method: "GET",
            data: {schoolid: schoolid, p: pageno, rpp:rpp},
            async: false,
            success: function(result) {
                $(".hod-table-container").html(result);
            }
        });
    }
};

var standard = {
    standardListing: function(){
        var rpp = 10;
        if(getUrlVars()['rpp'] != undefined && getUrlVars()['rpp']>0){

            rpp=getUrlVars()['rpp'];
            if(rpp.indexOf('#')>-1)
                rpp = rpp.substr(0,rpp.indexOf('#'));
            $(".recordSelector").find("option[value="+rpp+"]").attr("selected", "selected");
        }


        var p=1;
        if(getUrlVars()['p'] != undefined && getUrlVars()['p']>0)
            p=getUrlVars()['p'];


        $(".recordSelector").change(function(e){
            rpp = $("option:selected", this).val();
            standard.listingAjax(0, 1, rpp);
            e.preventDefault();
        });
        loader.init($(".standard-table-container"));
        standard.listingAjax(0, p, rpp);

        $(document).on("click", ".pager li a", function(e){
            if($(this).parent("li").hasClass("disabled"))
                return;
            loader.init($(".standard-table-container"));
            var p = $(this).attr("data-p");
            if($(this).text() == 'Next')
                $(this).attr("data-p", (parseInt(p)+1))
            standard.listingAjax(0, p, rpp);
            e.preventDefault();
        });
    },

    listingAjax: function(schoolid, pageno, rpp){
        $.ajax({
            url: "/standard/standardlisting/",
            method: "GET",
            data: {schoolid: schoolid, p: pageno, rpp:rpp},
            async: false,
            success: function(result) {
                $(".standard-table-container").html(result);
            }
        });
    }
}

var standardId=0;
var subjectId = 0;
var subject = {
    subAddModal: function () {
        $(document).on("click", ".standardSubs > li a", function(e){
            if($(this).attr('data-action')=='add') {
                $("#subName").val("");
                $(".updateSubBtn").addClass("addSubBtn").removeClass("updateSubBtn");
                $(".addUpdateSubTitle").text("Add Subject");
                standardId = $(this).parent("li").parent("ul").attr("data-id");
                $(".modal-body h4").text("Selected Standard " + $(this).parents("td").prev("td").text());
                e.preventDefault();
            }
        });
    },

    addEditSubAjax: function(){
        $(document).on("click", ".addSubBtn", function(){
            $.ajax({
                url: "/subject/addsubject/",
                method: "GET",
                data: {name: $("#subName").val(), status: 1, standardId:standardId, isCompulsary:1},
                async: false,
                success: function(result) {
                    $(".msg-container").html('<h4 class="widgettitle title-success">Subject Added Successfully</h4>');
                    $("#subName").val("");
                    setTimeout(function(){
                        $(".msg-container").html("");
                    }, 4000);
                }
            });
        });

        $(document).on("click", ".updateSubBtn", function(){
            $.ajax({
                url: "/subject/addsubject/",
                method: "GET",
                data: {name: $("#subName").val(), status: 1, standardId:standardId, subjectId:subjectId, isCompulsary:1},
                async: false,
                success: function(result) {
                    $(".msg-container").html('<h4 class="widgettitle title-success">Subject Updated Successfully</h4>');
                    setTimeout(function(){
                        $(".msg-container").html("");
                    }, 4000);
                }
            });
        });
    },
    manageSubModal: function(){
        $(document).on("click", ".standardSubs > li a", function(e){
            if($(this).attr('data-action')=='manage') {
                $(".modal-body h4").text("Selected Standard " + $(this).parents("td").prev("td").text());
                standardId = $(this).parent("li").parent("ul").attr("data-id");
                subject.listingAjax(standardId);
                e.preventDefault();
            }
        });
    },
    editModal: function(){
        $(document).on("click", ".sub-edit-a", function(e){
            standardId = $(this).attr("data-standard-id");
            subjectId = $(this).attr("data-sub-id");
            $("#subNameSpan").text("Selected Subject "+$(this).parents("td").prev().text());
            $("#subName").val($(this).parents("td").prev("td").text());
            $(".addUpdateSubTitle").text("Update Subject");
            $(".addSubBtn").removeClass("addSubBtn").addClass("updateSubBtn");
            $(".close-btn").trigger("click");
            e.preventDefault();
        });
    },
    moduleAddModal: function(){
        $(document).on("click", ".module-modal-btn", function(e){
            subject.moduleListingAjax($(this).attr("data-sub-id"));
            $(".module-listing-table tfoot tr").attr("data-sub-id", $(this).attr("data-sub-id")).attr("data-standard-id", $(this).attr("data-standard-id")).html('<td colspan="3" align="center"><button class="btn btn-primary add-module-btn">Add New Module</button></td>');
            e.preventDefault();
        });
        $(document).on("click", ".add-module-btn", function(e){
            $(this).parent().parent().html("<td></td><td><input type='text' id='newModuleText' placeholder='Add New Module' /> </td><td><a href='#' class='addModuleA'><i class='iconfa-plus'></i></a> </td>");
            e.preventDefault();
        });
        $(document).on("click", ".addModuleA", function(e){
            $(".module-listing-table tbody").append("<tr><td></td><td>"+$(this).parent().prev().children("input").val()+"</td><td><a href='#' class='editModuleA'><i class='icon-edit'></i></a> </td></tr>");
            subject.addModuleAjax($("#newModuleText").val(), $(".module-listing-table tfoot tr").attr("data-sub-id"), $(".module-listing-table tfoot tr").attr("data-standard-id"));
            $(".module-listing-table tfoot tr").html('<td colspan="3" align="center"><button class="btn btn-primary add-module-btn">Add New Module</button></td>');
            e.preventDefault();
        });
        $(document).on("click", ".editModuleA", function(e){
            var id = $(this).attr("data-id");
            var txt = $(this).parent().prev().text();
            $(this).parent().parent().html("<td></td><td><input type='text' id='editModuleText' data-id='"+id+"' value='"+txt+"' placeholder='Edit Module' /> </td><td><a href='#' class='editModuleA-btn'><i class='iconfa-ok'></i></a> </td>");
            e.preventDefault();
        });
        $(document).on("click", ".editModuleA-btn", function(e){
            var id = $(this).parent().prev().children("input").attr("data-id");
            var txt = $(this).parent().prev().children("input").val();
            subject.editModuleAjax(txt, id);
            $(this).parent().prev().html($(this).parent().prev().children("input").val());
            $(this).parent().html("<a href='#' data-id='"+id+"' class='editModuleA'><i class='icon-edit'></i></a>");
            e.preventDefault();
        });
        $(document).on("click", ".bank-sub-listing-a", function(e){
            $(".module-close-btn").trigger("click");
            e.preventDefault();
        });
    },
    listingAjax: function(standardId){
        $.ajax({
            url: "/subject/subjectlisting/",
            method: "GET",
            data: {standardId: standardId},
            async: false,
            success: function(result) {
                $(".subject-listing-table tbody").html(result);
            }
        });
    },
    addModuleAjax: function(val, subjectId, standardId){
        $.ajax({
            url: "/subject/addmodule/",
            method: "POST",
            data: {subjectName: val, entityName:'module', pid: subjectId, standardId: standardId},
            async: false,
            success: function(result) {
                $(".editModuleA:last").attr("data-id", result);
            }
        });
    },
    moduleListingAjax: function(subjectId){
        $.ajax({
            url: "/subject/modulelisting/",
            method: "GET",
            data: {pid: subjectId},
            async: false,
            success: function(result) {
                $(".module-listing-table tbody").html(result);
            }
        });
    },
    editModuleAjax: function(val, subjectId){
        $.ajax({
            url: "/subject/editmodule/",
            method: "POST",
            data: {subjectName: val, entityName:'module', subjectId: subjectId},
            async: false,
            success: function(result) {

            }
        });
    },
};

var syllabus = {
    content: function(){
        preSchoolId = $("#hidSchoolId").val();
        preStandardId = 0;
        preSubjectId = 0;
        $(document).on("change", "#schoolId", function(e){
            preSchoolId = $("#schoolId").val();
            tinymce.activeEditor.setContent("");
            $("#standardId").val(0).change();
            $("#subjectIdContainer").html("Select Subject First");
            $("#syllabusDueDate").val("");
            $(".stdform").attr("action", "/syllabus/addsyllabus");
            $("#syllabusId").val("0");
            //e.preventDefault();
        });

        $(document).on("change", "#standardId", function(e){
            preStandardId = $(this).val();
            tinymce.activeEditor.setContent("");
            $("#subjectIdContainer").html("Select Subject First");
            $("#syllabusDueDate").val("");
            $(".stdform").attr("action", "/syllabus/addsyllabus");
            $("#syllabusId").val("0");
            //e.preventDefault();
        });

        $(document).on("change", "#subjectId", function(e){
            preSubjectId = $(this).val();
            tinymce.activeEditor.setContent("");
            $("#syllabusDueDate").val("");
            $(".stdform").attr("action", "/syllabus/addsyllabus");
            $("#syllabusId").val("0");
            //e.preventDefault();
        });

        $(document).on("change", "#moduleId", function(e){
            tinymce.activeEditor.setContent("");
            $("#syllabusDueDate").val("");
            $(".stdform").attr("action", "/syllabus/addsyllabus");
            $("#syllabusId").val("0");
            //e.preventDefault();
        });

        $(document).on("change", "#customSubjectId", function(e){
            preStandardId = $("option:selected", this).attr("data-standard-id");
            $("#standardId").val(preStandardId);
            preSubjectId = $(this).val();
            utils.moduleControlBuilder(preSubjectId, "moduleIdContainer", "moduleId", "moduleId", "moduleId", "0");
            if($(".student-evaludation-container").length>0){
                $(".student-evaludation-container").slideUp();
            }
            //e.preventDefault();
        });
        
        
    },
    standardCustomPreSelect: function(moduleId){
        var tempStandardId = parseInt($("#customSubjectId").children("option:selected").val());
        if(tempStandardId>0){
            utils.moduleControlBuilder(tempStandardId, "moduleIdContainer", "moduleId", "moduleId", "moduleId", moduleId);
        }
        preStandardId = parseInt($("#customSubjectId").children("option:selected").attr("data-standard-id"));
        preSubjectId = tempStandardId;
        //syllabus.fetchStandardCurrentSyllabus(moduleId,preSchoolId,preStandardId,preSubjectId,"");
    },
    standardSyllabusFetcher: function(ctrlSubjectId, placeCtrlId){
        $(document).on("change", "#"+ctrlSubjectId, function(){
            var schoolId = parseInt(preSchoolId);
            var standardId = parseInt(preStandardId);
            var subjectId = parseInt(preSubjectId);
            var moduleId = parseInt($(this).val());
            //alert(schoolId+" :: "+standardId+" :: "+subjectId);
            if(schoolId>0 && standardId>0 && subjectId>0)
                syllabus.fetchStandardCurrentSyllabus(moduleId,schoolId,standardId,subjectId,placeCtrlId);
            else {
                tinymce.activeEditor.setContent("");
                $("#syllabusDueDate").val("");
                $(".stdform").attr("action", "/syllabus/addsyllabus");

            }

        });
    },
    fetchStandardCurrentSyllabus: function(moduleId,schoolId,standardId,subjectId,placeCtrlId){
        $.getJSON( "/syllabus/findlatestsyllabus/", {moduleId:moduleId, schoolId:schoolId, standardId: standardId, subjectId: subjectId} )
            .done(function( result ) {
                //alert( "JSON Data: " + result.dueDate );
                if(result.result=="true"){
                    tinymce.activeEditor.setContent(result.content);
                    $("#syllabusDueDate").val(result.dueDate);
                    $(".stdform").attr("action", "/syllabus/editsyllabus");
                    $("#syllabusId").val(result.id);
                    if($(".student-evaludation-container").length>0){
                        $(".student-evaludation-container").slideDown();
                    }

                } else {
                    tinymce.activeEditor.setContent("");
                    $("#syllabusDueDate").val("");
                    $(".stdform").attr("action", "/syllabus/addsyllabus");
                    if($(".student-evaludation-container").length>0){
                        $(".student-evaludation-container").slideUp();
                    }
                }
            })
            .fail(function( jqxhr, textStatus, error ) {
                var err = textStatus + ", " + error;
                alert( "Request Failed: " + err );
            });


    }
};

var utils = {
    schoolSubsSelector: function(standardId, placeCtrlId, ctrlName, ctrlId, ctrlClass, selectedSubjectVal){
        var standardSelId = parseInt($("option:selected", "#"+standardId).val());
        if(standardSelId>0) {
            utils.subjectControlBuilder(standardSelId, placeCtrlId, ctrlName, ctrlId, ctrlClass, selectedSubjectVal)
        }

        $(document).on("change", "#"+standardId, function(){
            var standardSelId = parseInt($("option:selected", "#"+standardId).val());
            if(standardSelId>0) {
                utils.subjectControlBuilder(standardSelId, placeCtrlId, ctrlName, ctrlId, ctrlClass, selectedSubjectVal)
            }
        });
    },
    subModuleSelector: function(subjectId, placeCtrlId, ctrlName, ctrlId, ctrlClass, selectedModuleVal){
        var moduleSelId = parseInt($("option:selected", "#"+subjectId).val());
        if(moduleSelId>0) {
            utils.moduleControlBuilder(moduleSelId, placeCtrlId, ctrlName, ctrlId, ctrlClass, selectedModuleVal)
        }

        $(document).on("change", "#"+subjectId, function(){
            var moduleSelId = parseInt($("option:selected", "#"+subjectId).val());
            if(moduleSelId>0) {
                utils.moduleControlBuilder(moduleSelId, placeCtrlId, ctrlName, ctrlId, ctrlClass, selectedModuleVal)
            }
        });
    },
    subjectControlBuilder: function(standardId, placeCtrlId, ctrlName, ctrlId, ctrlClass, selectedSubjectVal){
        $.ajax({
            url: "/subject/getschoolsubjects/",
            method: "GET",
            data: {standardId: standardId, ctrlName: ctrlName, ctrlId: ctrlId, ctrlClass: ctrlClass, selectedSubjectVal: selectedSubjectVal},
            async: false,
            success: function(result) {
                $("#"+placeCtrlId).html(result);
            }
        });
    },
    moduleControlBuilder: function(subjectId, placeCtrlId, ctrlName, ctrlId, ctrlClass, selectedModuleVal){
        $.ajax({
            url: "/subject/getsubjectmodules/",
            method: "GET",
            data: {subjectId: subjectId, ctrlName: ctrlName, ctrlId: ctrlId, ctrlClass: ctrlClass, selectedModuleVal: selectedModuleVal},
            async: false,
            success: function(result) {
                $("#"+placeCtrlId).html(result);
            }
        });
    },

    multiValuePreSelect: function(ctrlId, val){
        $("#teacherStandardId option[value=2]").prop("selected", true);
        var multiPreValTemp = val.split(",");
        multiPreValTemp=multiPreValTemp.filter(function(v){return v!==''});
        $.each(multiPreValTemp, function(i,e){
            $("#"+ctrlId+" option[value='2']").prop("selected", true);
        });
    }
};

var rating = {
    createRating: function(){
        if($('.promptbutton').length > 0) {
            $('.promptbutton').click
            (function(){
                var ratingGot = $(this).text();
                jPrompt('NOTE: Any pre given rating of same module will be overridden!!! <br>Selected Rating: '+ratingGot+'<br>Enter Comments:<textarea style="resize: none; padding-right: 0px !important; overflow: hidden" rows="5" id="popup_prompt"></textarea>', '', 'Submit Comments', function(r) {
                    var entityName = "syllabus";
                    var entityId = parseInt($("#syllabusId").val());
                    var studentId = $("#studentListContainer").children("option:selected").val();
                    var maxRating = 5;
                    var schoolId = $("#hidSchoolId").val();
                    var params = '{ "entityName": "'+entityName+'", "schoolId": "'+schoolId+'", "comments": "'+r+'", "entityId": "'+entityId+'", "rating": "'+ratingGot+'", "studentId": "'+studentId+'", "maxRating": "'+maxRating+'" }';
                    rating.createRatingAjax(params);
                });
            });
        }
        

        
    },
    createRatingAjax: function(params){
        var ratingParams = $.parseJSON(params)
        //alert(ratingParams);
        $.ajax({
            url: "/rating/addrating/",
            method: "GET",
            data: {entityName: ratingParams.entityName, schoolId: ratingParams.schoolId, comments:ratingParams.comments, entityId: ratingParams.entityId, studentId: parseInt(ratingParams.studentId), maxRating: parseInt(ratingParams.maxRating), rating: parseInt(ratingParams.rating)},
            async: false,
            success: function(result) {
                $(".rating-msg-container").prepend('<h4 class="widgettitle title-success">Rating submitted successfully</h4>');
                setTimeout(function(){
                    $("h4.title-success").slideUp().remove();
                }, 5000);
            }
        });
    },
    
    studentListing: function(){
    	$(document).on("change", "#standardId", function(){
    		$("#dataTable").html("");
    		$("#customSubjectId").val("0");
    	});
    	
    	$(document).on("click", ".syllabusContent", function(e){
        	e.preventDefault();
            var syllabusId = $(this).attr("data-id");
            $("#syllabusContent").find(".modal-body").load("/syllabus/syllabuscontent/"+syllabusId);
        });
    	
        $(document).on("change", "#customSubjectId", function(){
            var standardId = $("#standardId").val();
            var subjectId = $(this).val();
//            var dataUrl = "/syllabus/getStudentWork/?standardId="+standardId+"&subjectId="+subjectId;
//            $('#dyntable').dataTable( {
//            	  "ajaxSource": dataUrl,
//            	  "columns": [
//            	    { "data": "studentName" },
//            	    { "data": "taskType" },
//            	    { "data": "taskType" },
//            	    { "data": "rate" },
//            	    { "data": "outof" }
//            	  ]
//            	} );
            $.ajax({
                url: "/syllabus/getStudentWork/",
                method: "GET",
                data: {standardId: standardId, subjectId: subjectId},
                async: false,
                success: function(result) {
                	$('#dataTable').html(result);
                }
            });
            
            $(document).on("click", ".promptbutton", function(e){
                e.preventDefault();
                console.log("Rating updated select");
                $(this).nextAll().children("span").addClass("blank-rating");
        		$(this).children("span").removeClass("blank-rating");
        		$(this).prevAll().children("span").removeClass("blank-rating");
        		var rateId = $(this).parent().parent().siblings("#rateId").html();
        		var rate = $(this).children("span").html();
        		console.log("Rate ID - "+rateId);
        		console.log("Rate - "+rate);
        		$.ajax({
                    url: "/rating/updateRating/"+rateId+"/"+rate,
                    method: "GET",
                    async: true,
                });
            });
//            $.getJSON( "/syllabus/getStudentWork/", {standardId: standardId, subjectId: subjectId} )
//                .done(function( result ) {
//                	$('#dataTable').html(result);
//                })
//                .fail(function( jqxhr, textStatus, error ) {
//                    var err = textStatus + ", " + error;
//                    alert( "Request Failed: " + err );
//                });
        });
    }
    
    
};

var tasks={
    schoolChange: function(){
        $(document).on("change", "#schoolId", function(){
            $("#hidSchoolId").val($(this).val());
            $("#taskName").val("").change();
            tinymce.activeEditor.setContent("");
            $("#syllabusDueDate").val("");
            $(".stdform").attr("action", "/tasks/addtask");

        });
    },
    standardChange: function(){
        $(document).on("change", "#standardId", function(){
            $("#hidStandardId").val($(this).val());
            tinymce.activeEditor.setContent("");
            $("#syllabusDueDate").val("");
            $(".stdform").attr("action", "/tasks/addtask");
        });
    },
    subjectChange:function(){
        $(document).on("change", "#subjectId", function(){
            $("#taskName").val("").change();
            tinymce.activeEditor.setContent("");
            $("#syllabusDueDate").val("");
            $(".stdform").attr("action", "/tasks/addtask");
            $("#hidSubjectId").val($(this).val());
        });
    },
    taskNameChange: function(){
        $("#taskName").on("change", function(){
            var taskName = $(this).val();
                tinymce.activeEditor.setContent("");
                $("#syllabusDueDate").val("");
                $(".stdform").attr("action", "/tasks/addtask/");
                var schoolId = $("#hidSchoolId").val();
                var standardId = $("#hidStandardId").val();
                var subjectId = $("#hidSubjectId").val();
                var params = '{ "schoolId": "'+schoolId+'", "standardId": "'+standardId+'", "subjectId": "'+subjectId+'", "taskName": "'+taskName+'" }';
                if(schoolId.length>0 && standardId.length>0 && subjectId.length>0 && taskName.length>0)
                    tasks.taskFetcherAjax(params);
        });
    },
    teacherCustomSubjectChange: function(){
        $(document).on("change", "#customSubjectId", function(){
            tinymce.activeEditor.setContent("");
            $("#syllabusDueDate").val("");
            $(".stdform").attr("action", "/tasks/addtask/");
            $("#hidStandardId").val($("option:selected", this).attr("data-standard-id"));
            $("#hidSubjectId").val($("option:selected", this).val());
        });
    },
    taskFetcherAjax: function(params){
        var taskParams = $.parseJSON(params);
        $.ajax({
            url: "/tasks/gettask/",
            method: "GET",
            dataType: "json",
            data: {schoolId: taskParams.schoolId, standardId: taskParams.standardId, subjectId: taskParams.subjectId, taskName: taskParams.taskName},
            async: false,
            success: function(result) {
                if(result.result=="true"){
                    tinymce.activeEditor.setContent(result.content);
                    $("#syllabusDueDate").val(result.dueDate);
                    $(".stdform").attr("action", "/tasks/edittask/");
                    $("#taskId").val(result.taskId);
                }
            }
        });
    }
};



var loader={
    init: function(ctrl){
        ctrl.html('<div align="center"><img src="/resources/pages/images/loaders/loader19.gif" alt=""></div>');
    }
}

function getUrlVars()
{
    var vars = [], hash;
    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for(var i = 0; i < hashes.length; i++)
    {
        hash = hashes[i].split('=');
        vars.push(hash[0]);
        vars[hash[0]] = hash[1];
    }

    return vars;
}