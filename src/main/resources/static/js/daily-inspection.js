var dailyInspection = {};

dailyInspection.loadInstance = function() {
    if(this.currentIndex >= this.instanceIdList.length) {
        $(".content-header .fa-refresh").removeClass("fa-spin");
        return;
    }

    var currentInstanceId = this.instanceIdList[this.currentIndex];

    var selectorQuery = "#box-" + currentInstanceId;
    $(selectorQuery + " .table-condensed").hide();
    $(selectorQuery + " .fa-refresh").addClass("fa-spin");

    var request = $.ajax({
        url : "/daily-inspection/reload-instance.mj",
        data : {id : currentInstanceId}
    });

    request.done(function(data) {
        $(selectorQuery).html("");
        $(selectorQuery).html(data);

        dailyInspection.currentIndex++;
        dailyInspection.loadInstance();
    });

    request.fail(function(){
        alert("서버에 접속하는 동안 에러가 발생했습니다. 관리자에게 문의하세요.");

        dailyInspection.currentIndex++;
        dailyInspection.loadInstance();
    });
}

dailyInspection.reloadAllInstance = function() {
    $(".content-header .fa-refresh").addClass("fa-spin");

    dailyInspection.instanceIdList = [];

    $(".content .col-md-6 .box").each(function(idx) { dailyInspection.instanceIdList.push($(this).attr("id").substring(4)); });
    dailyInspection.currentIndex = 0;

    dailyInspection.loadInstance();
}

dailyInspection.reloadInstance = function(id) {
    dailyInspection.instanceIdList = [id];
    dailyInspection.currentIndex = 0;

    var selectorQuery = "#box-" + id;
    $(selectorQuery + " .table-condensed").hide();
    $(selectorQuery + " .fa-refresh").addClass("fa-spin");

    var request = $.ajax({
        url : "/daily-inspection/reload-instance.mj",
        data : {id : id}
    });

    request.done(function(data) {
        $(selectorQuery).html("");
        $(selectorQuery).html(data);
    });

    request.fail(function(){
        alert("서버에 접속하는 동안 에러가 발생했습니다. 관리자에게 문의하세요.");
    });
}

dailyInspection.changeBranch = function(branch) {
    location.href = "/daily-inspection/main.mj?branch=" + branch;
}

$('#datepicker').datepicker();