let vm = new Vue({
    el:'.page-content',
    data:{
        statute:'',
        ueditorConfig:{
            //前端默认ueditor资源文件加载路径
            //VueUeditorWrap会从默认加载路径中加载ueditor.all.min.js和config.js等资源
            UEDITOR_HOME_URL:'static/ueditor/',
            serverUrl:'doUeditor',
            maximumWords:100000
        }
    },
    methods:{
        doUpdate:function () {

            axios({
                url:'manager/statute/doUpdate',
                method:'put',
                data:this.statute
            }).then(response=>{
                let index = parent.layer.getFrameIndex(window.name);
                if(response.data.success){//成功处理
                    parent.layer.close(index);
                    parent.layer.msg(response.data.msg);
                    parent.layer.success=true;
                }else{//更新失败提示
                    layer.msg(response.data.msg)
                }
            }).catch(error=>{
                layer.msg(error.message)
            })
        },

        cancel:function () {
            let index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);

        },
        // doUpdate:function () {
        //     axios({
        //         url:''
        //     })
        // }
    },
    created:function () {
        this.statute=parent.layer.obj;
    },
    mounted:function () {
        jeDate({
            dateCell: '#modifydate',   //设置日期插件存放的dom节点
            format: 'YYYY-MM-DD',       //设置日期显示格式
            zIndex: 999999999,           //设置图层
            choosefun:(val)=> {//选中日期后回调
                //动态获取jeDate赋值后的日期，给vue的statute对象的pubDate赋值
                // console.log(val)
                this.statute.pubDate=val;
            }
        });
    }, components:{//局部注册
    VueUeditorWrap
}
})