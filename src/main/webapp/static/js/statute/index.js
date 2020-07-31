// let vm = new Vue({
//     el:'.page-content',
//     data:{
//         pageInfo:{
//             pageNum:1,
//             pageSize:5
//         },
//         type:''
//     },
//     methods:{
//         selectPage:function () {
//             axios({
//                 url:`manager/statute/selectPage/${this.pageInfo.pageNum}/${this.pageInfo.pageSize}`,
//                 params:{type:this.type}
//             }).then(response=>{
//                 this.pageInfo=response.data.obj;
//             }).catch(error=>{
//                 layer.msg(error.message);
//             })
//         },
//         _selectPage:function (pageNum,pageSize) {
//             this.pageInfo.pageNum=pageNum;
//             this.pageInfo.pageSize=pageSize;
//             this.selectPage();
//         },
//         toUpdate:function (statute) {
//             layer.obj = statute;
//
//             layer.open({
//                 type:2,
//                 title:false,
//                 area:['60%','80%'],
//                 content:'manager/statute/toUpdate',
//                 end:()=>{
//                     if(layer.success==undefined||!layer.success){
//                         this.selectPage();
//                     }
//                 }
//             })
//         }
//     },
//     created:function () {
//         this.selectPage();
//     },
//     mounted:function () {
//         //生成日期插件
//         jeDate({
//             dateCell: '#indate',
//             isinitVal: true,
//             format: 'YYYY-MM-DD'
//         });
//     }
// })
let vm=new Vue({
    el:'.page-content',
    data:{
        pageInfo:{
            pageNum:1,
            pageSize:5
        },
            type:'',
        statute:{},
        active:true,
        ueditorConfig:{
            //前端默认ueditor资源文件加载路径
            //VueUeditorWrap会从默认加载路径中加载ueditor.all.min.js和config.js等资源
            UEDITOR_HOME_URL:'static/ueditor/',
            serverUrl:'doUeditor',
            maximumWords:100000
        }
    },

    methods:{
        selectPage:function () {
            axios({
                url:`manager/statute/selectPage/${this.pageInfo.pageNum}/${this.pageInfo.pageSize}`,
                params:{type:this.type}
            }).then(response=>{
                this.pageInfo=response.data.obj
            }).catch(error=>{
                layer.msg(error.message)
            })
        },
        _selectPage:function (pageNum,pageSize) {
            this.pageInfo.pageNum=pageNum;
            this.pageInfo.pageSize=pageSize;
            this.selectPage()
        },
        selectAll:function(){
            this.pageInfo={
                //初始化  第一次页面加载的时候使用
                pageNum:1,
                pageSize:5
            };
            this.type='';
            this.selectPage();
        },
        toUpdate:function (statute) {
            layer.obj = statute;

            layer.open({
                type:2,
                title:false,
                area:['60%','80%'],
                content:'manager/statute/toUpdate',
                end:()=>{
                    if(layer.success==undefined||!layer.success){
                        this.selectPage();
                    }
                }
            })
        },
        doDelete: function (id) {

            layer.msg("确认删除吗?", {
                time: 0 //不自动关闭
                , btn: ['是', '否']
                , yes: (index) => {
                    layer.close(index);
                    let statute = {id: id, delFlag: 1};
                    axios({
                        url: `manager/statute/doUpdate`,
                        method: 'put',
                        data: statute
                    }).then((response) => {
                        console.log(response.id);
                        console.log(response);
                        if (response.data.success) {
                            this.selectPage();
                        }
                        layer.msg(response.data.msg);
                    }).catch(error => {
                        // console.log(error);
                        layer.msg(error.message);
                    })
                }
            })
        },
        changeActive: function () {
            this.active = !this.active;
        },
        save: function () {
            // this.changeActive();//修改显示状态
            console.log("保存......");
            axios({
                url:'manager/statute/Insert',   //基于base的绝对路径请求
                method:'post',
                data:this.statute,
            }).then( (response)=> {

                let index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                parent.layer.success = response.data.success;
                if(response.data.success){
                    parent.layer.close(index);
                    parent.layer.msg(response.data.msg);
                    this.statute={};
                    this.selectPage();
                    this.changeActive();
                }else{
                    layer.msg(response.data.msg);//子窗口中提示
                }
                //失败后提示
            }).catch(error=>{
                // console.log(error);
                layer.msg(error.message);
            })
        },
    },
    created:function () {
        this.selectPage()
    },
    mounted:function () {
        //生成日期插件
        jeDate({
            dateCell: '#indate',
            isinitVal: true,
            format: 'YYYY-MM-DD',
            choosefun:(val)=> {//选中日期后回调
                //动态获取jeDate赋值后的日期，给vue的statute对象的pubDate赋值
                // console.log(val)
                this.statute.pubDate=val;
            }
        });
    },
components:{//局部注册
    VueUeditorWrap
}
});