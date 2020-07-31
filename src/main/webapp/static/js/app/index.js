// let vm = new Vue({
//     el:'.page-content',
//     data:{
//         result:{} //响应结果
//     },
//     methods:{
//         selectAll:function(){
//             console.log(this)
//             //发送ajax请求返回统一结果响应数据  {success:xxx,msg:xxx,obj:xxx}
//             axios({
//                 url:'manager/app/selectAll'   //基于base的绝对路径请求
//             }).then((response)=>{//剪头函数  自动传递上下文的this
//                 console.log(this);
//                 this.result = response.data;
//             }).catch(error=>{
//                 console.log(error);
//             })
//         }
//     },
//     created:function () {  //data初始化后调用
//         this.selectAll();
//     }
// });
// let vm=new Vue({
//     el: '.page-content',
//     data: {
//         result:{}
//     },
//     methods: {
//         selectAll: function () {
//             console.log(this)
//             axios({
//                 url:'manager/app/selectAll'
//             }).then((response)=>{
//                 console.log(this);
//                 this.result=response.data
//             }).catch(error=>{
//                 console.log(error)
//             })
//         }
//     },
//     created:function () {
//         this.selectAll();
//     }
// });
// var vm=new Vue({
//     el:'.page-content',
//     data:{
//         result:{}
//     },
//     methods:{
//         selectAll:function () {
//             console.log(this);
//             axios({
//                 url: 'manager/app/selectAll'
//             }).then(function (response) {
//                 console.log(this);
//                 vm.result=response.data
//             }).catch(function (error) {
//                 console.log(error)
//             })
//         }
//     },
//     created:function () {
//         this.selectAll()
//     }
// });
let vm = new Vue({
    el:'.page-content',
    data:{
        pageInfo:{
            pageNum:1,
            pageSize:5,
        } ,//响应结果
        active:true,     //true 则 版本列表激活   false则版本添加激活
        app:{}
    },
    methods: {
        selectPage: function () {
            //发送ajax请求返回统一结果响应数据  {success:xxx,msg:xxx,obj:xxx}
            axios({
                url: `manager/app/selectPage/${this.pageInfo.pageNum}/${this.pageInfo.pageSize}`   //基于base的绝对路径请求
            }).then((response) => {//传递上下文对象
                console.log(this);
                this.pageInfo = response.data.obj;
                console.log(this.pageInfo)
            }).catch(error => {
                console.log(error);
            })
        },
        _selectPage: function (pageNum, pageSize) {
            console.log(pageNum);
            this.pageInfo.pageNum = pageNum;
this.pageInfo.pageSize = pageSize;
this.selectPage();
},
doDelete: function (id) {

    layer.msg("确认删除吗?", {
        time: 0 //不自动关闭
        , btn: ['是', '否']
        , yes: (index) => {
            layer.close(index);
            let app = {id: id, delFlag: 1};
            axios({
                url: `manager/app/doUpdate`,
                method: 'put',
                data: app
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
toUpdate: function (app) {

    layer.obj = app;

    layer.open({
        type: 2,
        title: false,
        area: ['50%', '60%'],
        content: 'manager/app/toUpdate',
        end: () => {
            if (layer.success == undefined || !layer.success) {
                this.selectPage();
            }
        }
    });
},
changeActive: function () {
    this.active = !this.active;
},
save: function () {
    // this.changeActive();//修改显示状态
    console.log("保存......");
    axios({
        url:'manager/app/Insert',   //基于base的绝对路径请求
        method:'post',
        data:this.app,
    }).then( (response)=> {

        let index = parent.layer.getFrameIndex(window.name); //获取窗口索引
        parent.layer.success = response.data.success;
        if(response.data.success){
            parent.layer.close(index);
            parent.layer.msg(response.data.msg);
            this.app={};
            this.changeActive();
            this.selectPage();

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
created:function () {  //data初始化后调用
    // this.selectAll();
    this.selectPage();
}
});