let index = {
    init: function (){
        $("#btn-save").on("click", () => {
            this.save();
        });
        $("#btn-login").on("click", () => {
            this.login();
        });
    },

    save: function () {
        // alert('user의 save 함수 호출됨');
        let data = {
            username: $("#username").val(),
            password: $("#password").val(),
            email: $("#email").val()
        }
        console.log(data);

        $.ajax({
            type: "POST",
            url: "/auth/joinProc",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json" // 응답형태
        }).done(resp => {
                alert("회원가입이 완료되었습니다.");
                location.href = "/";
            }
        ).fail(err => {
            alert(JSON.stringify(err))
        }); // 통신을 이용해서 3개의 파라미터를
    }
}

index.init();