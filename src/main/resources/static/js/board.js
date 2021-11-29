let index = {
    init: function (){
        $("#btn-save").on("click", () => {
            this.save();
        });
    },

    save: function () {
        // alert('user의 save 함수 호출됨');
        let data = {
            title: $("#title").val(),
            content: $("#content").val(),
        }

        $.ajax({
            type: "POST",
            url: "/api/board",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json" // 응답형태
        }).done(resp => {
                alert("글쓰기가 완료되었습니다..");
                location.href = "/";
            }
        ).fail(err => {
            alert(JSON.stringify(err))
        }); // 통신을 이용해서 3개의 파라미터를
    }
}

index.init();