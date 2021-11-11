let index = {
    init: function (){
        $("#btn-save").on("click", () => {
            this.save();
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

        $.ajax().done().fail(); // 통신을 이용해서 3개의 파라미터를
    }
}

index.init();