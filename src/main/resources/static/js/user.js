let index = {
    init: function (){
        $("#btn-save").on("click", () => {
            this.save();
        });
    },

    save: function () {
        alert('user의 save 함수 호출됨');
    }

}

index.init();