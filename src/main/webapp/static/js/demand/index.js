let vm = new Vue({
    el: '.page-content',
    data: {
        pageInfo: {
            pageNum: 1,
            pageSize: 5
        }
    },
    methods: {
        selectPage: function () {
            axios({
                url: `manager/demand/selectPage/${this.pageInfo.pageNum}/${this.pageInfo.pageSize}`
            }).then(response => {
                this.pageInfo = response.data.obj;
                console.log(this.pageInfo)
            }).catch(error=>{
                layer.msg(error.message)
            })
        },
        _selectPage: function (pageNum, pageSize) {
            this.pageInfo.pageNum = pageNum;
            this.pageInfo.pageSize = pageSize;
            this.selectPage();
        }

    },
    created: function () {
        this.selectPage()
    }
});