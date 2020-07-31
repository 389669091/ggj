let vm = new Vue({
    el:'.page-content',
    data:{
        qualification:{}
    },
    methods:{
        doUpdate:function(check){
            this.qualification.check=check;
            this.qualification.address=null;//不更新地址
            axios({
                url:`manager/qualification/doUpdate`,
                method:'put',
                data:this.qualification
            }).then(response=>{
                console.log(response.data.qualification.address);
                let index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                parent.layer.success = response.data.success;
                if(response.data.success){//成功更新，关闭当前子窗口，并且通过父窗口提示
                    parent.layer.close(index);
                    parent.layer.msg(response.data.msg);
                }else{
                    layer.msg(response.data.msg);//子窗口中提示
                }

            }).catch(error=>{
                layer.msg(error.message)
            })
        },
        // doUpdate:function (check) {
        //     //改变审核状态
        //     this.qualification.check=check;
        //     this.qualification.address=null;
        //     axios({
        //         url:`manager/qualification/doUpdate`,
        //         method: 'put',
        //         //传值
        //         data:this.qualification
        //     }).then((response)=>{
        //
        //     })
        // }
    },

    created:function () {
        //获取父窗口layer绑定数据传递给qualification
        this.qualification=parent.layer.obj;
        //address:  图片名

    }
});