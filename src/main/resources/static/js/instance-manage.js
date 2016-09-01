var instanceManage = {};
var modalProcessor = instanceManage;

instanceManage.testConnect = function() {
    var params = $(".form-horizontal").serialize();

    var request = $.ajax({
        url : "/instance-manage/test-connection.mj",
        data : params
    });

    request.done(function(data) {
        if(data.result == true) {
            alert('OK');
        } else {
            alert(data.message);
        }
    });

    request.fail(function(){
        alert("서버에 접속하는 동안 에러가 발생했습니다. 관리자에게 문의하세요.");
    });
};

instanceManage.saveInstance = function() {
    var form = $(".form-horizontal");
    var params = form.serialize();
    var instanceId = form.find("input[name=id]").val();

    var formOverlay = $("#formOverlay");
    formOverlay.css("display", "block");

    var url = "/instance/" + instanceId + ".mj";
    var request = $.ajax({
        url : url,
        method : "POST",
        data : params
    });

    request.done(function(data) {
        if(data.result == true) {
            location.reload();
        } else {
            alert(data.message);

            //var formOverlay = $("#formOverlay");
            formOverlay.css("display", "none");
        }
    });

    request.fail(function(){
        alert("서버에 접속하는 동안 에러가 발생했습니다. 관리자에게 문의하세요.");

        //var formOverlay = $("#formOverlay");
        formOverlay.css("display", "none");
    });
};

instanceManage.deleteInstance = function(instId) {
    if(confirm("삭제하시겠습니까?") == false) {
        return;
    }

    var url = "/instance/" + instId + ".mj";
    var request = $.ajax({
        url : url,
        method : "DELETE"
    });

    request.done(function(data) {
        if(data.result == true) {
            location.reload();
        } else {
            alert(data.message);
        }
    });

    request.fail(function(){
        alert("서버에 접속하는 동안 에러가 발생했습니다. 관리자에게 문의하세요.");
    });
};

instanceManage.doModal = function(modal, relatedTarget) {
    modal.find(".form-horizontal")[0].reset();

    var instId = relatedTarget.attr("inst-id");
    if(instId) {
        var url = "/instance/" + instId + ".mj";

        $.get(url, function(data) {
            var form = $(".modal-content .form-horizontal");
            form.find("input[name=seq]").val(data.seq);
            form.find("input[name=branch]").val(data.branch);
            form.find("input[name=id]").val(data.id);
            form.find("input[name=name]").val(data.name);
            form.find("input[name=description]").val(data.description);
            form.find("input[name=version]").val(data.version);
            form.find("input[name=host]").val(data.host);
            form.find("input[name='connection.ip']").val(data.connection.ip);
            form.find("input[name='connection.port']").val(data.connection.port);
            form.find("input[name='connection.sid']").val(data.connection.sid);
            form.find("input[name='connection.userName']").val(data.connection.userName);
            form.find("input[name='connection.userPassword']").val(data.connection.userPassword);
        }).fail(function(){
            alert("서버에 접속하는 동안 에러가 발생했습니다. 관리자에게 문의하세요.");
        });
    }
};